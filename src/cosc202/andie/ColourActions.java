package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel directly 
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations will need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills and Ninja-turtles
 * @version 1.0
 */
public class ColourActions {
    
    /** A list of actions for the Colour menu. */
    protected ArrayList<Object> actions;
    protected JMenu cccycle;
    public String error = "Error!";
    public String loadimagefirst = "Please load an image first.";
    public String apply = "Something went wrong when trying to apply.";

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     * Keyboard shortcut for:
     * Convert to greyscale: G
     * imageInvert: 1
     * ColourChannelCycle: 4, 5, 6
     * Brightness: K
     * Contrast: C
     * 
     */
    public ColourActions() {
        actions = new ArrayList<Object>();
        actions.add(new ConvertToGreyAction("Greyscale", null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G), Integer.valueOf(KeyEvent.VK_W), Integer.valueOf(ActionEvent.CTRL_MASK)));

        cccycle = new JMenu("Colour Channel Cycle");
        cccycle.setOpaque(true);

        cccycle.add(new ColourChannelCycleAction("Cycle Red and Green", null, "Cycle the Red and Green colour channels", 0, Integer.valueOf(KeyEvent.VK_R), Integer.valueOf(KeyEvent.VK_4), Integer.valueOf(ActionEvent.CTRL_MASK)));
        cccycle.add(new ColourChannelCycleAction("Cycle Red and Blue", null, "Cycle the Red and Blue colour channels", 1, Integer.valueOf(KeyEvent.VK_B), Integer.valueOf(KeyEvent.VK_5), Integer.valueOf(ActionEvent.CTRL_MASK)));
        cccycle.add(new ColourChannelCycleAction("Cycle Green and Blue", null, "Cycle the Green and Blue colour channels", 2, Integer.valueOf(KeyEvent.VK_G), Integer.valueOf(KeyEvent.VK_6), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new ImageInvertAction("Invert Image", null, "Inverts the colour in the image", Integer.valueOf(KeyEvent.VK_I), Integer.valueOf(KeyEvent.VK_1), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new BrightnessAction("Brightness", null, "Brightens the image", Integer.valueOf(KeyEvent.VK_B), Integer.valueOf(KeyEvent.VK_K), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new ContrastAction("Contrast", null, "Change contrast in the image", Integer.valueOf(KeyEvent.VK_C), Integer.valueOf(KeyEvent.VK_N), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(cccycle);


        for (int j = 0; j < cccycle.getItemCount(); j++) {
            JMenuItem menuItem = cccycle.getItem(j);
            if (menuItem != null) {
                String key = menuItem.getActionCommand();
                key = key.toLowerCase();
                key = key.replaceAll("\\s+","");
                menuItem.setName(key);
                menuItem.setBackground(new Color(200, 162, 200));

            }
        }
    }

    /**
     * <p>
     * Create a menu containing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu colourMenu = new JMenu("Colour");
        colourMenu.setName("colour");

        // Add the submenus to the main menu and others
        for (Object action : actions) {
            if (action instanceof Action) {
                colourMenu.add((Action) action);
            } else if (action instanceof JMenu) {
                colourMenu.add((JMenu) action);
            }
        }
        
        for (int j = 0; j < colourMenu.getItemCount(); j++) {
            JMenuItem menuItem = colourMenu.getItem(j);
            if (menuItem != null) {
                String key = menuItem.getActionCommand();
                key = key.toLowerCase();
                key = key.replaceAll("\\s+","");
                menuItem.setName(key);
                menuItem.setBackground(new Color(200, 162, 200));

                if(key.equals("colourchannelcycle")){
                    menuItem.setBackground(new Color(200, 162, 200));
                }else if(key.equals("greyscale")){
                    menuItem.setBackground(Color.LIGHT_GRAY);                   
                }
            }
        }

        return colourMenu;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            //check that there is an image to add the filter to
            if (!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return; // undo opertation if there is no image
            }
            // create and applies the filter
            try{
                
                target.getImage().apply(new ConvertToGrey());
                target.repaint();
                target.getParent().revalidate();
                
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
            
        }

    }

    /**
     * <p>
     * Action to Cycle the colour channels of the image.
     * </p>
     * 
     * @see ColourChannelCycle
     */
    public class ColourChannelCycleAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        int channelsToCycle;
        ColourChannelCycleAction(String name, ImageIcon icon, String desc, int channelsToCycle, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            this.channelsToCycle = channelsToCycle;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the colour channel cycle action is triggered.
         * </p>
         * 
         */
        public void actionPerformed(ActionEvent e) {
            // checks that an image has been added
            if(!target.hasImage()){
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//cancel operation if no image has been added
            }
            try{
            // Create and apply the filter
            target.getImage().apply(new ColourChannelCycle(channelsToCycle));
            target.repaint();
            target.getParent().revalidate();
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to Invert colour in the image.
     * </p>
     * 
     * @see ImageInvert
     */
    public class ImageInvertAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        ImageInvertAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the invert image action is triggered.
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            //Check that an image has been loaded
            if(!target.hasImage()){
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//cancel operation if no image has been added
            }
            try{
            // Create and apply the filter
            target.getImage().apply(new ImageInvert());
            target.repaint();
            target.getParent().revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to brighten the image.
     * </p>
     * 
     * @see Brightness
     */
    public class BrightnessAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        BrightnessAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the brighten image action is triggered.
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            // checks that an image has been added
            if(!target.hasImage()){
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//cancel operation if no image has been added
            }
            
            // Determine the brightness - ask the user.
            int brightnessInt = 1;

            // Pop-up dialog box to ask for the radius value.
            JSlider brightnesSlider = new JSlider(-255, 255, 0);
            brightnesSlider.setMajorTickSpacing(1);
            brightnesSlider.setSnapToTicks(true);
            int option = JOptionPane.showOptionDialog(null, brightnesSlider, "Select brighness amount",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                brightnessInt = brightnesSlider.getValue();
            }

            // Cancels any operations if the set radius is 0
            if (brightnessInt == 0) {
                return;
            }
            
            try{
            // Create and apply the filter
            target.getImage().apply(new Brightness(brightnessInt));
            target.repaint();
            target.getParent().revalidate();

            }catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
        
        }
    }

    /**
     * <p>
     * Action to change contrast in the image.
     * </p>
     * 
     * @see Contrast
     */
    public class ContrastAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        ContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the change contrast action is triggered.
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            
            // checks that an image has been added
            if(!target.hasImage()){
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//cancel operation if no image has been added
            }

            // Determine the contrast - ask the user.
            int contrastInt = 1;

            // Pop-up dialog box to ask for the radius value.
            JSlider contrastSlider = new JSlider(0, 255, 128);
            contrastSlider.setMajorTickSpacing(1);
            contrastSlider.setSnapToTicks(true);
            int option = JOptionPane.showOptionDialog(null, contrastSlider, "Select contrast amount",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                contrastInt = contrastSlider.getValue();
            }

            // Cancels any operations if the set radius is 128
            if (contrastInt == 128) {
                return;
            }

            try{
            // Create and apply the filter
            target.getImage().apply(new Contrast(contrastInt));
            target.repaint();
            target.getParent().revalidate();
            }catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
