package cosc202.andie;

import java.util.ArrayList;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter; 

public class MacroActions{

    public String error = "Error!";
    public String emptymacro = "The macro is empty";
    public String savemacro = "Something went wrong when saving the macro.";
    public String saveimagemacro = "Something went wrong when saving the macro image.";
    public String repaintmacro = "Something went wrong when repainting the macro.";
    public String emptymacrosave = "You cannot save an empty macro.";
    public String macroerror = "Error in Macro";
    public String savemacro2 = "Do you want to save the current Macro?";
    public String savemacroconfirm = "Confirm Save for Macro";
    public String macrosaveerror = "There was an error when trying to save the image for the Macro.";
    public String repaintmacroerror = "Unable to repaint using the macro";

    
    protected ArrayList<Action> actions; 

    public MacroActions(){
        //instansiate the arraylist that holds the actions
        this.actions = new ArrayList<Action>();
        //add all of the actions to the Actions arraylist
        actions.add(new MacroStartAction("Start Macro", null, "Start the Macro recording", KeyEvent.VK_S, Integer.valueOf(KeyEvent.VK_C), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new MacroStopAction("Stop Macro", null, "Stop the Macro recording", KeyEvent.VK_Z, Integer.valueOf(KeyEvent.VK_U), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new MacroImportAction("Import Macro", null, "Import a saved Macro", KeyEvent.VK_I, KeyEvent.VK_SLASH, ActionEvent.CTRL_MASK));
    }

    
    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     * 
     * @return The tool menu UI element.
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu("Record");
        viewMenu.setName("record");



        for (Action action : actions) {
            viewMenu.add(new JMenuItem(action));
        }

        for (int j = 0; j < viewMenu.getItemCount(); j++) {
            JMenuItem menuItem = viewMenu.getItem(j);
            if (menuItem != null) {
                String key = menuItem.getActionCommand();
                key = key.toLowerCase();
                key = key.replaceAll("\\s+","");
                menuItem.setName(key);
                menuItem.setBackground(new Color(255, 182, 193));
                
            }
        }
        return viewMenu;
    }


    
    public class MacroStartAction extends ImageAction {
        /**
         * <p>
         * Triggered when the MacroStartAction is activated.
         * </p>
         *
         * <p>
         * This method sets the macroRunning flag of EditableImage to true.
         * </p>
         * 
         * @param name     The name of the action.
         * @param icon     An icon to use to represent the action.
         * @param desc     A brief description of the action.
         * @param mnemonic A mnemonic key to use as a shortcut.
         */
        public MacroStartAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EditableImage.isRecording = true;
        }
    }

    public class MacroStopAction extends ImageAction {
        /**
         * <p>
         * Triggered when the MacroStopAction is activated.
         * </p>
         *
         * <p>
         * This method sets the macroRunning flag of EditableImage to false and
         * provides an opens up dialog for saving the macro.
         * </p>
         * 
         * @param name     The name of the action.
         * @param icon     An icon to use to represent the action.
         * @param desc     A brief description of the action.
         * @param mnemonic A mnemonic key to use as a shortcut.
         */
        public MacroStopAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * callback for when the MacroStopAction is activated.
         * </p>
         *
         * <p>
         * This method sets the macroRunning from EditableImage to false and
         * shows an option to save the current Macro.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // set the macro isRunning boolean to false
            EditableImage.isRecording = false;
            // if there are no operations to save to a file
            if (EditableImage.countMacroOperations == 0) {
                JOptionPane.showMessageDialog(Andie.frame, emptymacrosave, macroerror, JOptionPane.ERROR_MESSAGE);
                return;
            }
            int chosenOption = JOptionPane.showOptionDialog(Andie.frame, savemacro2, savemacroconfirm, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            // if the option is yes then save it to a file
            if (chosenOption == JOptionPane.YES_OPTION) {
                // choose the file
                JFileChooser chosenFile = new JFileChooser("./macros");
                int result = chosenFile.showSaveDialog(null);
                // check to see if the result is the approve option
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        // get the file path and store it
                        String imgFilepath = chosenFile.getSelectedFile().getCanonicalPath();
                        System.out.println("Saving macro to: " + imgFilepath);
                        // save the macro to the file
                        target.getImage().saveMacro(imgFilepath);
                        // 
                    } catch (Exception ex) {     
                        // let the JOptionPane show an error message if there is failure with the image being saved
                        JOptionPane.showMessageDialog(Andie.frame, macrosaveerror, macroerror, JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    


    
    public class MacroImportAction extends ImageAction {
        /**
         * <p>
         * Triggered when the MacroImportAction is activated.
         * </p>
         *
         * <p>
         * callback for when the MacroImportAction is activated.
         * </p>
         *
         * <p>
         * This method opens up a file selection dialog which
         * allows for loading of a presaved .macro file .
         * </p>
         *
         * @param name     The name of the action.
         * @param icon     An icon to use to represent the action.
         * @param desc     A brief description of the action.
         * @param mnemonic A mnemonic key to use as a shortcut.
         */
        public MacroImportAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier){
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback triggered when the MacroImportAction is activated.
         * </p>
         *
         * <p>
         * This method opens a file selection dialog, allowing the user to choose a
         * .macro file.
         * If a file is selected, it applies all the actions from the stack to the
         * image.
         * </p>
         *
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileToSelect = new JFileChooser("./Macros");
            //promt the user to select the file type
            FileNameExtensionFilter fileTypeChosen = new FileNameExtensionFilter("File types to choose:", "macro", "ops");
            //set the file type of the file to select 
            fileToSelect.setFileFilter(fileTypeChosen);
            //promts the user to select a file of the certain type 
            int result = fileToSelect.showOpenDialog(target);
            //if the user selects a file then apply all the actions from the stack on to the image
            if(result == JFileChooser.APPROVE_OPTION){
                try {
                    //get the file path from the selected file
                    String filePath = fileToSelect.getSelectedFile().getCanonicalPath();
                    //add the macro to the image in the file
                    target.getImage().openMacro(filePath);
                    target.repaint();
                    target.getParent().revalidate();
                } catch (Exception j) {
                    JOptionPane.showMessageDialog(Andie.frame, repaintmacroerror, macroerror, JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        }
    }
}
    

