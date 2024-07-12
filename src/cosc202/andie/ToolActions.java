package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;



/**
 * <p>
 * Actions provided by the View menu.
 * </p>
 * 
 * <p>
 * The View menu contains actions that affect how the image is displayed in the application.
 * These actions do not affect the contents of the image itself, just the way it is displayed.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills and Ninja-turtles
 * @version 1.0
 */
public class ToolActions {
    
    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;
    /**
     * A list of icons for the tool bar menu.
     */
    //protected ArrayList<ImageIcon> imageIconList;

    /*Submenus list */
    //sub menus removed



    public String error = "Error!";
    public String loadimagefirst = "Please load an image first.";
    public String errorresize = "Something went wrong when resizing.";
    public String errorrotate = "Something went wrong when rotating.";
    public String errorflip = "Something went wrong when flipping.";
    public String errorSelect2 = "This feature does not work with select.";

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     * @throws IOException 
     */
    public ToolActions() throws IOException {
        actions = new ArrayList<Action>();
        // Add the option to resize the image smaller using the button or ctrl + up arrow | command + up arrow key
        actions.add(new ResizeImageAction("Resize Smaller", null, "Resize the Image Smaller", KeyEvent.VK_S, Integer.valueOf(KeyEvent.VK_DOWN), Integer.valueOf(ActionEvent.CTRL_MASK)));
        // Add the option to resize the image larger using the button or ctrl + down arrow | command + down arrow key
        actions.add(new ResizeImageAction("Resize Larger", null, "Resize the Image Larger", KeyEvent.VK_L, Integer.valueOf(KeyEvent.VK_UP), Integer.valueOf(ActionEvent.CTRL_MASK)));
        //add the option to rotate the image left using the button or Ctrl + left arrow key | command + left arrow key
        actions.add(new RotateImageAction("Rotate Left", null, "Rotate the Image Left", Integer.valueOf(KeyEvent.VK_A), Integer.valueOf(KeyEvent.VK_LEFT), Integer.valueOf(ActionEvent.CTRL_MASK)));
        //add the option to rotate the image left using the button or Ctrl + Left arrow key | command + right arrow key
        actions.add(new RotateImageAction("Rotate Right", null, "Rotate the Image right", Integer.valueOf(KeyEvent.VK_K), Integer.valueOf(KeyEvent.VK_RIGHT), Integer.valueOf(ActionEvent.CTRL_MASK)));
        //add the option to flip the image horizontal using the button or Ctrl + H | command + H
        actions.add(new FlipImageAction("Flip Horizontal", null, "Flip the Image Horizontal", Integer.valueOf(KeyEvent.VK_H), Integer.valueOf(KeyEvent.VK_H), Integer.valueOf(ActionEvent.CTRL_MASK)));
        //add the option to flip the image vertical using the button or Ctrl + V | command + V
        actions.add(new FlipImageAction("Flip Vertical", null, "Flip the Image Vertical", Integer.valueOf(KeyEvent.VK_V), Integer.valueOf(KeyEvent.VK_V), Integer.valueOf(ActionEvent.CTRL_MASK)));
    }


    /**
     * <p>
     * Create a button from an action.
     * </p>
     * 
     * @return The Button.
     */
    public JButton createButtonFromAction(Action action) {
        JButton button = new JButton(action);
        button.setText(null); // Remove text to show only the icon
        return button;
    }

    /**
     * <p>
     * Get all the image actions.
     * </p>
     * 
     * @return The action arraylist.
     */
    public ArrayList<Action> getActions(){
        return actions;
    }

    
   

    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     * 
     * @return The tool menu UI element.
     */
    public JMenu createMenu() {
        JMenu toolMenu = new JMenu("Tools");
        toolMenu.setName("tools");


        for (Object action: actions) {
            if(action instanceof JMenu){
                toolMenu.add((JMenu) action);
            } else {
                toolMenu.add(new JMenuItem((Action) action));
            }
        }

        for (int j = 0; j < toolMenu.getItemCount(); j++) {
            JMenuItem menuItem = toolMenu.getItem(j);
            if (menuItem != null) {
                String key = menuItem.getActionCommand();
                key = key.toLowerCase();
                key = key.replaceAll("\\s+","");
                menuItem.setName(key);
                menuItem.setBackground(new Color(255, 182, 193));
            }
        }

        return toolMenu;
    }

   
        /**
     * <p>
     * Action to resize an image.
     * </p>
     * 
     * @see ResizeImage
     */
    public class ResizeImageAction extends ImageAction {

        ResizeImageAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }


        /**
        * <p>
        * method to perform if the action happens
        * </p>
        * 
        * 
        */
        public void actionPerformed(ActionEvent e) {
            //check if the image has been loaded
            if(!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method when an image has not been loaded
            }

            //check if the image is selected
            if (target.getImage().isSelected()) {
                JOptionPane.showMessageDialog(null, errorSelect2, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method when selection is active
            }

            try{
            //check which button was clicked 
            if (e.getActionCommand().equals("Resize Larger") || e.getActionCommand().equals("Rahi Nui ake")) {
                target.getImage().apply(new ResizeImage(true));
            } else if (e.getActionCommand().equals("Resize Smaller") || e.getActionCommand().equals("Rahi iti ake")) {
                target.getImage().apply(new ResizeImage(false));
            }
            target.repaint();
            target.getParent().revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, errorresize, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

       /**
     * <p>
     * Action to resize an image.
     * </p>
     * 
     * @see RotateImage
     */
    public class RotateImageAction extends ImageAction {
    
        RotateImageAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }


        /**
        * <p>
        * method to perform if the type of rotate button is pressed or the shortuct key for it is pressed
        * </p>
        * 
        * 
        */
        public void actionPerformed(ActionEvent e) {
            //check if the image has been loaded
            if(!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method when an image has not been loaded
            }
            
            //check if the image is selected
            if (target.getImage().isSelected()) {
                JOptionPane.showMessageDialog(null, errorSelect2, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method when selection is active
            }

            try{
            // Create and apply the rotation
            if (e.getActionCommand().equals("Rotate Left") || e.getActionCommand().equals("Tītaka Mauī")) {
                target.getImage().apply(new RotateImage(false));
            } else if (e.getActionCommand().equals("Rotate Right") || e.getActionCommand().equals("Huri Matau")) {
                target.getImage().apply(new RotateImage(true));
            }
            target.repaint();
            target.getParent().revalidate();
            }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, errorrotate, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to flip an image.
     * </p>
     * 
     * @see FlipImage
     */
    public class FlipImageAction extends ImageAction{
   
        FlipImageAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }


        /**
        * <p>
        * method to perform if the action happens
        * </p>
        * 
        * 
        */
        public void actionPerformed(ActionEvent e) {
            //check if the image has been loaded
            if(!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method when an image has not been loaded
            }

            //check if the image is selected
            if (target.getImage().isSelected()) {
                JOptionPane.showMessageDialog(null, errorSelect2, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method when selection is active
            }

            try{
            // Create and apply the rotation
            if (e.getActionCommand().equals("Flip Horizontal") || e.getActionCommand().equals("Pore Whakapae")) {
                target.getImage().apply(new FlipImage(true));
            } else if (e.getActionCommand().equals("Flip Vertical") || e.getActionCommand().equals("Pore Poutū")) {
                target.getImage().apply(new FlipImage(false));
            }
            target.repaint();
            target.getParent().revalidate();
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(null, errorflip, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
