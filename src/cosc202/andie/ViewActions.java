package cosc202.andie;

import java.util.*;
import java.awt.Image;
import java.awt.event.*;
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
 * @author Steven Mills
 * @version 1.0
 */
public class ViewActions {
    
    /**
     * A list of actions for the View menu.
     */
    protected ArrayList<Action> actions;
    /**
     * A list of icons for the tool bar menu.
     */
    protected ArrayList<ImageIcon> imageIconList;

    /**
     * <p>
     * Create a set of View menu actions.
     * </p>
     */
    public ViewActions() {
        actions = new ArrayList<Action>();
        imageIconList = new ArrayList<ImageIcon>();
        actions.add(new ZoomInAction("Zoom In", null, "Zoom In", Integer.valueOf(KeyEvent.VK_PLUS), Integer.valueOf(KeyEvent.VK_I), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new ZoomOutAction("Zoom Out", null, "Zoom Out", Integer.valueOf(KeyEvent.VK_MINUS), Integer.valueOf(KeyEvent.VK_P), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new ZoomFullAction("Zoom Full", null, "Zoom Full", Integer.valueOf(KeyEvent.VK_1), Integer.valueOf(KeyEvent.VK_F), Integer.valueOf(ActionEvent.CTRL_MASK)));
        //create the icons 
        //image icons created
        ImageIcon zoomInIcon = new ImageIcon(ToolActions.class.getClassLoader().getResource("zoomIn.png"));
        zoomInIcon = resizeIcon(zoomInIcon, 50, 50);
        //
        ImageIcon zoomOutIcon = new ImageIcon(ToolActions.class.getClassLoader().getResource("zoomOut.png"));
        zoomOutIcon = resizeIcon(zoomOutIcon, 50, 50);
        //
        ImageIcon zoomFullIcon = new ImageIcon(ToolActions.class.getClassLoader().getResource("fullZoom.png"));
        zoomFullIcon = resizeIcon(zoomFullIcon, 50, 50);
        //add the icons to the iconslist
        //add the icons to the list
        imageIconList.add(zoomInIcon);
        imageIconList.add(zoomOutIcon);
        imageIconList.add(zoomFullIcon);
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
     * Get all the image icons.
     * </p>
     * 
     * @return The image icon arraylist.
     */
    public ArrayList<ImageIcon> getIcons(){
        return imageIconList;
    }
    /**
     * <p>
     * Resize the image entered into the method.
     * </p>
     * 
     * @return The resized image icon.
     */
    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
    /**
     * <p>
     * Create a menu containing the list of View actions.
     * </p>
     * 
     * @return The view menu UI element.
     */
    public JMenu createMenu() {
        JMenu viewMenu = new JMenu("View");
        viewMenu.setName("view");

        for (Action action: actions) {
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

    /**
     * <p>
     * Action to zoom in on an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomInAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        /**
         * <p>
         * Create a new zoom-in action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomInAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the zoom-in action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomInAction is triggered.
         * It increases the zoom level by 10%, to a maximum of 200%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(target.getZoom()+10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to zoom out of an image.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomOutAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        /**
         * <p>
         * Create a new zoom-out action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomOutAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the zoom-out action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomOutAction is triggered.
         * It decreases the zoom level by 10%, to a minimum of 50%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(target.getZoom()-10);
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to reset the zoom level to actual size.
     * </p>
     * 
     * <p>
     * Note that this action only affects the way the image is displayed, not its actual contents.
     * </p>
     */
    public class ZoomFullAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        /**
         * <p>
         * Create a new zoom-full action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ZoomFullAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the zoom-full action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ZoomFullAction is triggered.
         * It resets the Zoom level to 100%.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.setZoom(100);
            target.repaint();
            target.getParent().revalidate();
        }

    }



}
