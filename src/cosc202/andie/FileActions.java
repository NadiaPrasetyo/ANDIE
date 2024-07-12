package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills and Ninja-turtles
 * @version 1.0
 */
public class FileActions {

    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    public String error = "Error!";
    public String nosave = "There is nothing to save.";
    public String errorsave = "An error occurred while trying to save your file.";
    public String loadimagefirst = "Please load an image first.";
    public String filetype = "Please select the type of image file you want (eg. jpeg...).";
    public String errorexport = "An error occurred while exporting your file.";
    public String unsavedchanges = "The image currently displayed has unsaved changes. Do you want to continue without saving those changes?";
    public String unsavedchanges2 = "Unsaved Changes";
    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     * Keyboard shortcut for:
     * Open: O
     * Save: S
     * Save As: A
     * Export: Q
     * Exit: E
     */
    public FileActions() {
        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction("Open", null, "Open a file", Integer.valueOf(KeyEvent.VK_O), Integer.valueOf(KeyEvent.VK_O), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new FileSaveAction("Save", null, "Save the file", Integer.valueOf(KeyEvent.VK_S), Integer.valueOf(KeyEvent.VK_S), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new FileSaveAsAction("Save As", null, "Save a copy", Integer.valueOf(KeyEvent.VK_A), Integer.valueOf(KeyEvent.VK_A), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new FileExportAction("Export", null, "Export the file", Integer.valueOf(KeyEvent.VK_X), Integer.valueOf(KeyEvent.VK_Q), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new FileExitAction("Exit", null, "Exit the program", Integer.valueOf(KeyEvent.VK_E), Integer.valueOf(KeyEvent.VK_E), Integer.valueOf(ActionEvent.CTRL_MASK)));


    }

    public ArrayList<Action> getActions(){
        return actions;
    }

    /**
     * <p>
     * Create a menu containing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("file");



        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        for (int j = 0; j < fileMenu.getItemCount(); j++) {
            JMenuItem menuItem = fileMenu.getItem(j);
            if (menuItem != null) {
                String key = menuItem.getActionCommand();
                key = key.toLowerCase();
                key = key.replaceAll("\\s+","");
                menuItem.setName(key);
                menuItem.setBackground(new Color(255, 190, 134));
            }
        }

        return fileMenu;
    }

    /**
     * <p>
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {

            if(Andie.isChanged()){
                int option =JOptionPane.showConfirmDialog(Andie.frame, unsavedchanges, unsavedchanges2, JOptionPane.YES_NO_OPTION);
                if(option != JOptionPane.YES_OPTION){
                    return;
                }else{
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(target);
        
                    if (result == JFileChooser.APPROVE_OPTION) {
                        try {
                            String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                            target.getImage().open(imageFilepath);
                        } catch (Exception ex) {
                            System.exit(1);
                        }
                    }
            
                    target.repaint();
                    target.getParent().revalidate();
    
    
                }
            }else{
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(target);
    
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        target.getImage().open(imageFilepath);
                    } catch (Exception ex) {
                        System.out.println(ex);
                        System.exit(1);
                    }
                }
        
                target.repaint();
                target.getParent().revalidate();

            }            
        }
                  


        

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // saves the image and any updates to the image
            try {
                // checks that an image has been loaded before saving
                if (target.hasImage()) {
                    target.getImage().save();
                } else {
                    // if an image has not been loaded, show error popup message
                    JOptionPane.showMessageDialog(null, nosave, error, JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                //extra catch for un-loaded image
                JOptionPane.showMessageDialog(null, errorsave, error, JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            //checks that an image has been loaded before pulling up the saveAs dialog
            if (!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return; // undo opertation if there is no image
            }
            JFileChooser fileChooser = new JFileChooser();

            // Add the image file types that can be used
            FileNameExtensionFilter png = new FileNameExtensionFilter("PNG File", "png");
            FileNameExtensionFilter jpg = new FileNameExtensionFilter("JPEG File", "jpg");
            FileNameExtensionFilter gif = new FileNameExtensionFilter("GIF File", "gif");

            // add them to the fileChooser
            fileChooser.addChoosableFileFilter(png);
            fileChooser.addChoosableFileFilter(jpg);
            fileChooser.addChoosableFileFilter(gif);

            int result;
            ;

            // A do while loop is used so the user cannot save the image they
            // have worked on until they choose the type to save it under. When
            // they have chosen the type of image, a method will be called to
            // get the extension for that chosen type and attach it to the name
            // of the image the user has typed so that the image will be saved under
            // that chosen type.
            do {
                result = fileChooser.showSaveDialog(target);

                if (result == JFileChooser.CANCEL_OPTION) {
                    return;// closes selector if user click cancel
                }

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        
                            String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                            String extension = getImageExtension(fileChooser);

                            if (extension == null) {

                                // this is the dialog box that appears when the user doesnt choose a file-type
                                // to save their image under
                                JOptionPane.showMessageDialog(null,
                                        filetype, error, JOptionPane.ERROR_MESSAGE);
                            } else {

                                // when the user has chosen the filetype, the right extension will be attached
                                // to the end of the name
                                imageFilepath += "." + extension;

                                // the saveAs method is called on the imagePath
                                target.getImage().saveAs(imageFilepath);
                            }
                        
                        
                    } catch (Exception ex) {
                        //extra catch for un-loaded image and when there is any errors saving the image; closes the application
                        JOptionPane.showMessageDialog(null, errorsave, error, JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                }

            } while (result == JFileChooser.APPROVE_OPTION && getImageExtension(fileChooser) == null);

        }

    }

    /**
     * <p>
     * Action export an image to a file location.
     * </p>
     * 
     * @see EditableImage#export(String)
     */
    public class FileExportAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        /**
         * <p>
         * Create a new file-export action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the file-export action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExportAction is triggered.
         * It prompts the user to select a file and export the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();

            // Add the image file types that can be used
            FileNameExtensionFilter png = new FileNameExtensionFilter("PNG File", "png");
            FileNameExtensionFilter jpg = new FileNameExtensionFilter("JPEG File", "jpg");
            FileNameExtensionFilter gif = new FileNameExtensionFilter("GIF File", "gif");

            // add them to the fileChooser
            fileChooser.addChoosableFileFilter(png);
            fileChooser.addChoosableFileFilter(jpg);
            fileChooser.addChoosableFileFilter(gif);

            int result;

            // A do while loop is used so the user cannot export the image they
            // have worked on until they choose the type to export it under. When
            // they have chosen the type of image, a method will be called to
            // get the extension for that chosen type and attach it to the name
            // of the image the user has typed so that the image will be exported under
            // that chosen type.
            do {
                // checks that an image has been loaded before exporting
                if (!target.hasImage()) {
                    JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                    return;// undo opertation if there is no image
                }
                result = fileChooser.showSaveDialog(target);

                if (result == JFileChooser.CANCEL_OPTION) {
                    return;
                }

                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                        String extension = getImageExtension(fileChooser);

                        if (extension == null) {
                            JOptionPane.showMessageDialog(null,
                                    filetype, error, JOptionPane.ERROR_MESSAGE);
                        } else {
                            imageFilepath += "." + extension;
                            target.getImage().export(imageFilepath);
                        }
                    } catch (Exception ex) {
                        //extra catch for when there is any errors exporting the image; closes the application
                        JOptionPane.showMessageDialog(null, errorexport, error, JOptionPane.ERROR_MESSAGE);
                        System.exit(1);
                    }
                }

            } while (result == JFileChooser.APPROVE_OPTION && getImageExtension(fileChooser) == null);

        }

    }

    /**
     * <p>
     * A private method that returns a string which is the appropriate
     * file extension for the selection made by the user in fileChooser.
     * </p>
     * 
     */
    private String getImageExtension(JFileChooser fileChooser) {

        try {
            FileNameExtensionFilter extension = (FileNameExtensionFilter) fileChooser.getFileFilter();
            String[] extensions = extension.getExtensions();
            if (extensions.length > 0) {
                return extensions[0];
            }
        } catch (ClassCastException e) {
            // Handles the classexception by returning a null value
            return null;

        }
        return null;
    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {
        int acceleratorKey;
        int acceleratorModifier;

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            
            if(Andie.isChanged()){
                int option =JOptionPane.showConfirmDialog(Andie.frame, unsavedchanges, unsavedchanges2, JOptionPane.YES_NO_OPTION);
                if(option != JOptionPane.YES_OPTION){
                    return;
                }
            }
                    System.exit(0);
        }

    }

}
