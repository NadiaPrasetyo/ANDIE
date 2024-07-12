package cosc202.andie;

import java.util.*;
import java.io.*;
import java.awt.Rectangle;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.JOptionPane;

/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 * 
 * <p>
 * The EditableImage represents an image with a series of operations applied to it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can be undone.
 * THis is what is meant by "A Non-Destructive Image Editor" - you can always undo back to the original image.
 * </p>
 * 
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original image 
 * and the result of applying the current set of operations to it. 
 * The operations themselves are stored on a {@link Stack}, with a second {@link Stack} 
 * being used to allow undone operations to be redone.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills and Ninja-turtles
 * @version 1.0
 */
class EditableImage {

    /** The original image. This should never be altered by ANDIE. */
    private BufferedImage original;
    /** The current image, the result of applying {@link ops} to {@link original}. */
    private BufferedImage current;
    /** The sequence of operations currently applied to the image. */
    private Stack<ImageOperation> ops;
    /** A memory of 'undone' operations to support 'redo'. */
    private Stack<ImageOperation> redoOps;
    /** The file where the original image is stored/ */
    private String imageFilename;
    /** The file where the operation sequence is stored. */
    private String opsFilename;
    /** Counter for checking the number of operations since the macro started */
    static int countMacroOperations = 0;
    /** Stack for storing all of the image operations for the macros */
    private Stack<ImageOperation> macroStack;
    /** The boolean to store weather the macro recording is happening */
    protected static boolean isRecording = false;
    


    /**State of Selection */
    private boolean isSelected;
    private Rectangle selectedArea;

    /**
     * <p>
     * Updates the selection state of the image.
     * </p>
     * 
     * <p>
     * This method is used to update the selection state of the image. It is called by the {@link Select} class
     * to update the selection state of the image. It is used to determine whether a selection is active or not.
     * </p>
     * 
     * @param selected True if a selection is active, false otherwise.
     * @param selection The rectangle representing the selection.
     */
    public void updateSelection(boolean selected, Rectangle selection, BufferedImage image){
        current = image;
        selectedArea = selection;
        isSelected = selected;
       
    }

    /**
     * <p>
     * Acessor for the Macro Stack of operations
     *</p>
     * @return macroStack
     */
    public Stack<ImageOperation> getMacroStack(){
        return this.macroStack;
    }

    /**
     *<p>
     * Method to check for object equals
     * used in testing
     * </p>
     * @param obj comparing object
     * @return true if objects are the same
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        // Compare relevant fields here
        return true;
    }

    /**
     * Unused
     */
    @Override
    public int hashCode() {
        // Compute hash code based on relevant fields
        return Objects.hash(/* relevant fields */);
    }

    /**
     * <p>
     * Updates the selection state of the image.
     * </p>
     * 
     * <p>
     * This method is used to update the selection state of the image. It is called by the {@link Select} class
     * to update the selection state of the image. It is used to determine whether a selection is active or not.
     * </p>
     * 
     * @param selected True if a selection is active, false otherwise.
     * @param image The unselected image.
     */
    public void updateSelection(boolean selected, boolean cropping, BufferedImage image){
        isSelected = selected;
        current = image;
        if (selected == false){
            EditActions.SelectAction.getSelectFrame().setVisible(false);
            if (cropping == false){
                this.selectedArea = null;
            } 
        }
    }

    /**
     * <p>
     * Mutator for isSelected
     * </p>
     * @param selected The new value for isSelected
     */
    public void setSelected(boolean selected){
        isSelected = selected;
    }

    /**
     * <p>
     * Accessor for selected area
     * </p>
     * @return The selected area
     */
    public Rectangle getSelectedArea(){
        return selectedArea;
    }

    /**
     * <p>
     * Mutator for selected area
     * </p>
     * @param selectedArea The new value for selected area
     */
    public void setSelectedArea(Rectangle selectedArea){
        this.selectedArea = selectedArea;
    }

    /**
     * <p>
     * Accessor for selection state
     * </p>
     * @return True if a selection is active, false otherwise
     */
    public boolean isSelected(){
        return isSelected;
    }

    /**
     * Datafield for error messages - for internationalisation
     */
    public String error = "Error!";
    public String openingfile = "An error occurred while opening the image file.";
    public String notcorrecttype = "Andie cannot use this file type sorry.";
    public String deserializingopsfile = "An error occurred while deserializing the ops file.";
    public String errorsave = "An error occurred while trying to save your file.";
    public String errorexport = "An error occurred while exporting your file.";
    public String selectconfirmerror = "Error! Please confirm selection first.";

    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     * 
     * <p>
     * A new EditableImage has no image (it is a null reference), and an empty stack of operations.
     * </p>
     */
    public EditableImage() {
        original = null;
        current = null;
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        imageFilename = null;
        opsFilename = null;
        macroStack = new Stack<ImageOperation>();
    }

    /**
     * <p>
     * Check if there is an image loaded.
     * </p>
     * 
     * @return True if there is an image, false otherwise.
     */
    public boolean hasImage() {
        return current != null;
    }


    /**
     * <p>
     * Make a 'deep' copy of a BufferedImage.
     * </p>
     * 
     * <p>
     * Object instances in Java are accessed via references, which means that
     * assignment does
     * not copy an object, it merely makes another reference to the original.
     * In order to make an independent copy, the {@code clone()} method is generally
     * used.
     * {@link BufferedImage} does not implement {@link Cloneable} interface, and so
     * the
     * {@code clone()} method is not accessible.
     * </p>
     * 
     * <p>
     * This method makes a cloned copy of a BufferedImage.
     * This requires knowledge of some details about the internals of the
     * BufferedImage,
     * but essentially comes down to making a new BufferedImage made up of copies of
     * the internal parts of the input.
     * </p>
     * 
     * <p>
     * This code is taken from StackOverflow:
     * <a href=
     * "https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
     * in response to
     * <a href=
     * "https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
     * Code by Klark used under the CC BY-SA 2.5 license.
     * </p>
     * 
     * <p>
     * This method (only) is released under
     * <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
     * </p>
     * 
     * @param bi The BufferedImage to copy.
     * @return A deep copy of the input.
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    
    /**
     * <p>
     * Open an image from a file.
     * </p>
     * 
     * <p>
     * Opens an image from the specified file. It firstly checks if the specified file is an image-file,
     * if not, then a dialog box will appear letting the user know that the file he/she is trying to open
     * is not an image-file.
     * Also tries to open a set of operations from the file with <code>.ops</code> added.
     * So if you open <code>some/path/to/image.png</code>, this method will also try to
     * read the operations from <code>some/path/to/image.png.ops</code>.
     * 
     * Try-catches are incorporated into the method incase an exception occurs so 
     * instead of the program crashing, the user will be notified of a possible error 
     * with their tasks and then exits the method only and not the andie program
     * </p>
     * 
     * @param filePath The file to open the image from.
     */
    public void open(String filePath){ 
        try{

            imageFilename = filePath;

            //to check if the file given in the parameter is an image-file
            inImageFormat(filePath);

            opsFilename = imageFilename + ".ops";
            File imageFile = new File(imageFilename);
            original = ImageIO.read(imageFile);
            current = deepCopy(original);
        } catch (IOException e){
            JOptionPane.showMessageDialog(Andie.frame, openingfile, error, JOptionPane.ERROR_MESSAGE);  
            return;
        } catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(Andie.frame, notcorrecttype, error, JOptionPane.ERROR_MESSAGE);  
            return;    
        }

        try {

            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            redoOps.clear();
            objIn.close();
            fileIn.close();
        } catch (IOException e){

            //Could be that theres no file of a such or other input/output issues with this operation
            //clears the operations
            ops.clear();
            redoOps.clear();
        } catch (ClassNotFoundException e){
            JOptionPane.showMessageDialog(Andie.frame, deserializingopsfile, error, JOptionPane.ERROR_MESSAGE);
        } 
        this.refresh();
    }

    /**
     * <p>
     * A private method that determines whether a file in the parameter 
     * is an image filetype. If the file is not an image type(has an extension that
     * is not jpg, png or gif), then a new exception is thrown letting the user know
     * that the file in the parameter is not an image file.
     * </p>
     * 
     */    
    private void inImageFormat(String filePath){

        String extension = filePath.substring(1+imageFilename.lastIndexOf(".")).toLowerCase();
        if(!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg") && !extension.equals("gif")){
            throw new IllegalArgumentException("That file is not an image-file...duh");
        }
    }

    /**
     * <p>
     * Save an image to file.
     * </p>
     * 
     * <p>
     * Saves an image to the file it was opened from, or the most recent file saved as.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * Contains a try-catch to deal with situations where a possible input/output 
     * exception will occur, which then will be caught in a way that lets the user know 
     * theres an error with the image they are trying to save.
     * </p>
     * 
     */
    public void save(){
        if (this.opsFilename == null) {
            this.opsFilename = this.imageFilename + ".ops";
        }

        try{

        // Assign the file extension to a string
        String extension = imageFilename.substring(1+imageFilename.lastIndexOf(".")).toLowerCase();

        //create a new imageFile
        File image = new File(imageFilename);

        //write the image to the new imageFile(image)
        ImageIO.write(original, extension, image);

        // Write operations file
        FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(this.ops);
        objOut.close();
        fileOut.close();
        
        Andie.hasChanged = false;
        } catch( IOException e){
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(Andie.frame, errorsave, error, JOptionPane.ERROR_MESSAGE);
            return;
        }
    }


    /**
     * <p>
     * Save an image to a specified file.
     * </p>
     * 
     * <p>
     * Saves an image to the file provided as a parameter.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * 
     * There is also a try-catch that handles a potential exception when this method is used,
     * the user will be notified and then return(exits the method without exiting andie).
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     */
    public void saveAs(String imageFilename){

        try{

            //checks if the parameter is not an empty string
            if(imageFilename.isEmpty()){
                throw new IllegalArgumentException("Theres no filename for your image.");
            }

            this.imageFilename = imageFilename;
            this.opsFilename = imageFilename + ".ops";
            save();

        }catch(IllegalArgumentException e){

            //Dialog box that tells the user that they havent named their file
            JOptionPane.showMessageDialog(Andie.frame, e.getMessage(), error, JOptionPane.ERROR_MESSAGE);
            return;
        }

    }



    
    /**
     * <p>
     * Export an image to a specified filename(chosen by the user).
     * </p>
     * 
     * <p>
     * Saves an image to the file provided as a parameter. The sequence of operations
     * done to the image does not need to be saved.
     * 
     * Has a try-catch to handle any potential exceptions that may occur when the user calls
     * this method. Displays a dialog box letting the user know about the error and then exits 
     * the method.
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     */
    public void export(String imageFilename){

        try{
         
         if(imageFilename.isEmpty()){
            throw new IllegalArgumentException("Cannot export an image with no filename.");
         }   

        //create string to get the extension of the image
        String extension = imageFilename.substring(1+imageFilename.lastIndexOf(".")).toLowerCase();
        
        //write the image file using the current image and the extension
        Boolean success = ImageIO.write(current, extension, new File(imageFilename));

        if(success == false){
            JOptionPane.showMessageDialog(Andie.frame, notcorrecttype, error, JOptionPane.ERROR_MESSAGE);
        }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(Andie.frame, e.getMessage());
            return;
        } catch (IOException e){
            JOptionPane.showMessageDialog(Andie.frame, errorexport, error, JOptionPane.ERROR_MESSAGE);
            return;

        }

    }

    /**
     * <p>
     * Apply an {@link ImageOperation} to this image.
     * </p>
     * 
     * @param op The operation to apply.
     */
    public void apply(ImageOperation op) {
        Andie.hasChanged();

        if (isSelected){
            //checks that selected area has been confirmed
            if(EditActions.SelectAction.SelectListener.getIsConfirmed() == false){
            JOptionPane.showMessageDialog(null, selectconfirmerror, error, JOptionPane.ERROR_MESSAGE);
            return;
            }  
            //applies op only in the selected area
            current = Select.selectApply(current, op, (int)selectedArea.getX(), (int)selectedArea.getY(), (int)selectedArea.getWidth(), (int)selectedArea.getHeight());

            //checks that the ops was originally added when selection state was on           
        }else if (op.getSelected()){
            //applies the operation only to the selected area
            current = Select.selectApply(current, op, (int)selectedArea.getX(), (int)selectedArea.getY(), (int)selectedArea.getWidth(), (int)selectedArea.getHeight());

        } else{
            // applies the operation to the current image
        current = op.apply(current, false);
        }
        //ops.add(op);//adds the operation to the ops stack
        
        //current = op.apply(current, false);
        //if the macro is running then add the operation to the stack
        if(isRecording){
            //add to the macroStack
            macroStack.add(op);
            //add to the counted operations
            countMacroOperations++;
        }
        ops.add(op);
    }


    /**
     * <p>
     * Undo the last {@link ImageOperation} applied to the image.
     * </p>
     */
    public void undo() {
            // undo the last operation applied to the current image
            if(ops.peek().getClass() == CropOperation.class){
                isSelected = true;
                //checks if selection was made in this session or before (saved selection)
                if(EditActions.SelectAction.getSelectFrame()!=null){
                    EditActions.SelectAction.getSelectFrame().setVisible(true);
                    EditActions.SelectAction.SelectListener.setIsConfirmed(true);
                }
            
                //removes selected state if selection is undone
            } if (ops.peek().getClass() == SelectionArea.class){
                isSelected = false; 
            } 
           
            redoOps.push(ops.pop());
            //if the macro is running pop the operation from the stack
            if (isRecording) {
                //pop the operation from the macroStack
                macroStack.pop();
                //decrement the operation count
                countMacroOperations--;
            }
            refresh();

            //if selection is still on, redraw the selection area
            if (isSelected){
                current = Select.reSelect(current, selectedArea);
            }
            

    }

    /**
     * <p>
     * Reapply the most recently {@link undo}ne {@link ImageOperation} to the image.
     * </p>
     */
    public void redo()  {

        if(redoOps.peek().getClass() == CropOperation.class){
            isSelected = false;
            EditActions.SelectAction.getSelectFrame().setVisible(false);
        } else if(redoOps.peek().getClass() == SelectionArea.class){
            EditActions.SelectAction.SelectListener.setIsConfirmed(true);
            isSelected = true;
            current = redoOps.peek().apply(current, false);
            ops.add(redoOps.pop());
            return;
        }
        
                // reapply the last operation applied to the current image
            apply(redoOps.pop());
        
    }

    /**
     * <p>
     * Get the current image after the operations have been applied.
     * </p>
     * 
     * @return The result of applying all of the current operations to the {@link original} image.
     */
    public BufferedImage getCurrentImage() {
        return current;
    }

    /**
     * <p>
     * Reapply the current list of operations to the original.
     * </p>
     * 
     * <p>
     * While the latest version of the image is stored in {@link current}, this
     * method makes a fresh copy of the original and applies the operations to it in
     * sequence.
     * This is useful when undoing changes to the image, or in any other case where
     * {@link current}
     * cannot be easily incrementally updated.
     * </p>
     */
    private void refresh() {
        current = deepCopy(original);
        for (ImageOperation op : ops) {
            if (op.getClass() == SelectionArea.class) {
                // gets the selected area from the selection area operation
                selectedArea = ((SelectionArea) op).getSelectionArea();
                current = op.apply(current, false);
            } else
            // Checks if operation was originally applied when selection was on
            if (op.getSelected()) {
                current = Select.selectApply(current, op, (int) selectedArea.getX(), (int) selectedArea.getY(),
                        (int) selectedArea.getWidth(), (int) selectedArea.getHeight());
            } else {
                current = op.apply(current, false);
            }
        }
    }

    /**
     * Remove the specified imageOperation from the stack of operations.
     * 
     */
    public void removeOperation(ImageOperation op){
        ops.remove(op);
        refresh();
    }

     /**
     * Refreshes the current image state by reapplying all operations from the macro stack.
     *
     * @param macroStack The stack of image operations representing the macro.
     */
    private void refreshMacro(Stack<ImageOperation> macroStack) {
        current = deepCopy(original);
        for (ImageOperation op : macroStack) {
            current = op.apply(current, false);
        }
    }


    /**
     * Saves the macro stack to a file with the specified file name.
     *
     * @param fileName The name of the file to save the macro.
     * @throws Exception If the macro stack is empty or an error occurs during file writing.
     */
    public void saveMacro(String fileName) throws Exception {
        if (this.macroStack.isEmpty()) {
            throw new Exception("The macro stack is empty");
        }

        if (!fileName.contains(".macro")){
            fileName = fileName + ".macro";
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.macroStack);
            objectOutputStream.close();
            fileOutputStream.close();
        }
    }


     /**
     * Loads a macro from a file and applies the operations to the macro stack.
     * The file contains a serialized Stack of ImageOperation objects.
     *
     * @param path The path of the file from which to load the macro.
     * 
     */
    public void openMacro(String path) {
        try {
            FileInputStream fileInput = new FileInputStream(path);  // Correct usage of path
            ObjectInputStream objInput = new ObjectInputStream(fileInput);
    
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objInput.readObject();
            Stack<ImageOperation> opsReversed = new Stack<ImageOperation>();
            
            while (!opsFromFile.isEmpty()) {
                opsReversed.add(opsFromFile.pop());
            }
            
            while (!opsReversed.isEmpty()) {
                apply(opsReversed.pop());
            }
    
            objInput.close();
            fileInput.close();
        } catch (Exception j) {
            // Exception If the file is not found or the operations cannot be loaded.
            JOptionPane.showMessageDialog(null, "File Not Found.", "Error", JOptionPane.ERROR_MESSAGE);
            j.printStackTrace();
        }
        this.refresh();
    }
}
