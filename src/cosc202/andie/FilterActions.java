package cosc202.andie;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions {

    /** A list of actions for the Filter menu. */
    protected ArrayList<Object> actions;
    protected JMenu blurs;
    protected JMenu otherFilters;
    public String error = "Error!";
    public String loadimagefirst = "Please load an image first.";
    public String apply = "Something went wrong when trying to apply.";
    public String direction = "Please select the direction.";
    public String radiusstring = "Please select the radius.";
    public String blockstring = "Please select the block size.";
    public String scatterstring = "Please select the scatter size.";  
    public String directionOn = "You have a direction selected. Do you wish to keep it on while exiting?";
    public String embossDirection2 = "Emboss Direction already on";  
    public String sobelDirection2 = "Sobel Direction already on";  



    public String directionnorthwest = "NORTH-WEST";
    public String directionnorth = "NORTH";
    public String directionnortheast = "NORTH-EAST";
    public String directionwest = "WEST";
    public String directioneast = "EAST";
    public String directionsouthwest = "SOUTH-WEST";
    public String directionsouth = "SOUTH";
    public String directionsoutheast = "SOUTH-EAST";
    public String directionhorizontal = "HORIZONTAL";
    public String directionvertical = "VERTICAL";


    /**
     * <p>
     * Create a set of Filter menu actions.\
     * </p>
     * submenus for blurs and other filters
     * 
     * <p>
     * Keyboard shortcut for:
     * Mean filter: M
     * Median filter: J
     * Soft blur: B
     * Sharpen: X
     * Gaussian blur: G
     * Emboss filter: 2
     * Sobel filter: 3 //Add this later
     * Block Averaging: Scroll Lock
     * Random Scattering: L
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Object>();
        actions.add(new SharpenFilterAction("Sharpen", null, "Apply a sharpen filter", KeyEvent.VK_K,
                Integer.valueOf(KeyEvent.VK_X), Integer.valueOf(ActionEvent.CTRL_MASK)));

        blurs = new JMenu("Blur");
        blurs.setOpaque(true);

        blurs.add(new GaussianBlurAction("Gaussian blur", null, "Apply a Gaussian blur", KeyEvent.VK_G,
                Integer.valueOf(KeyEvent.VK_G), Integer.valueOf(ActionEvent.CTRL_MASK)));
        blurs.add(new SoftBlurAction("Soft blur", null, "Apply a soft blur", KeyEvent.VK_B,
                Integer.valueOf(KeyEvent.VK_B), Integer.valueOf(ActionEvent.CTRL_MASK)));
        blurs.add(new MeanFilterAction("Mean filter", null, "Apply a mean filter", Integer.valueOf(KeyEvent.VK_M), Integer.valueOf(KeyEvent.VK_M), Integer.valueOf(ActionEvent.CTRL_MASK)));
        blurs.add(new MedianFilterAction("Median filter", null, "Apply a median filter", KeyEvent.VK_H, Integer.valueOf(KeyEvent.VK_J), Integer.valueOf(ActionEvent.CTRL_MASK)));

        otherFilters = new JMenu("Other Filters");
        otherFilters.setOpaque(true);

        otherFilters.add(new SobelFilterAction("Sobel filter", null, "Apply Sobel filter", KeyEvent.VK_S, Integer.valueOf(KeyEvent.VK_3), Integer.valueOf(ActionEvent.CTRL_MASK))); 
        otherFilters.add(new EmbossFilterAction("Emboss filter", null, "Apply an emboss filter", KeyEvent.VK_E, Integer.valueOf(KeyEvent.VK_2), Integer.valueOf(ActionEvent.CTRL_MASK)));                        
        otherFilters.add(new BlockAveragingAction("Block Average", null, "Apply Block Averaging", Integer.valueOf(KeyEvent.VK_SCROLL_LOCK), Integer.valueOf(KeyEvent.VK_SCROLL_LOCK), Integer.valueOf(ActionEvent.CTRL_MASK)));
        otherFilters.add(new RandomScatteringAction("Random Scattering", null, "Apply Random Pixel Scattering", Integer.valueOf(KeyEvent.VK_ALPHANUMERIC), Integer.valueOf(KeyEvent.VK_L), Integer.valueOf(ActionEvent.CTRL_MASK))); 
        

        
        actions.add(otherFilters);
        for (int j = 0; j < otherFilters.getItemCount(); j++) {
            JMenuItem menuItem = otherFilters.getItem(j);
            if (menuItem != null) {
                String key = menuItem.getActionCommand();
                key = key.toLowerCase();
                key = key.replaceAll("\\s+","");
                menuItem.setName(key);

                if(key.equals("sobelfilter") || key.equals("embossfilter")){

                    menuItem.setBackground(new Color(255, 190, 134));

                }else if(key.equals("blockaverage") || key.equals("randomscattering")){

                    menuItem.setBackground(new Color(200, 162, 200));

                }

            }
        }

        actions.add(blurs);
        for (int j = 0; j < blurs.getItemCount(); j++) {
            JMenuItem menuItem = blurs.getItem(j);
            if (menuItem != null) {
                String key = menuItem.getActionCommand();
                key = key.toLowerCase();
                key = key.replaceAll("\\s+","");
                menuItem.setName(key);

                if(key.equals("gaussianblur") || key.equals("softblur")){

                    menuItem.setBackground(new Color(173, 216, 230));

                }else if(key.equals("medianfilter") || key.equals("meanfilter")){

                    menuItem.setBackground(new Color(255, 190, 134));

                }
            }
        }

    }

    /**
     * <p>
     * Create a menu containing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu filterMenu = new JMenu("Filter");
        filterMenu.setName("filter");

        // Add the submenus to the main menu and others
        for (Object action : actions) {
            if (action instanceof Action) {
                filterMenu.add((Action) action);
            } else if (action instanceof JMenu) {
                JMenu subMenu = (JMenu) action;
                filterMenu.add(subMenu);
            }
        }

        for (int j = 0; j < filterMenu.getItemCount(); j++) {
            JMenuItem menuItem = filterMenu.getItem(j);
            if (menuItem != null) {
                String key = menuItem.getActionCommand();
                key = key.toLowerCase();
                key = key.replaceAll("\\s+","");
                menuItem.setName(key);
                menuItem.setBackground(new Color(173, 216, 230));

                if(key.equals("otherfilters")){
                    menuItem.setBackground(new Color(144, 238, 144));
                }else if(key.equals("blur")){
                    menuItem.setBackground(new Color(144, 238, 144));  
                }

                

            }
        }

        return filterMenu;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
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
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
                //check if image is loaded
                if (!target.hasImage()) {
                    JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                    return;//exit the method if no image is loaded
                }

                // Determine the radius - ask the user.
                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
                JSpinner radiusSpinner = new JSpinner(radiusModel);
                int option = JOptionPane.showOptionDialog(null, radiusSpinner, radiusstring,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusModel.getNumber().intValue();
                }
                try{
                // Create and apply the filter
                target.getImage().apply(new MeanFilter(radius));
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }

        }

    }


    /**
     * <p>
     * Action to blur an image with a Median filter.
     * </p>
     * 
     * @see MedianFilter
     */
    public class MedianFilterAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        MedianFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic, Integer acceleratorKey, Integer acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * 
         * <p>
         * This method is called whenever the MedianFilterAction is triggered.
         * It prompts the user for a filter radius, then applies an appropriately sized
         * {@link MedianFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            
            //check if image is loaded
            if (!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method if no image is loaded
            }
            // Determine the radius - ask the user.
            int radius = 1;

            // Pop-up dialog box to ask for the radius value.
            SpinnerNumberModel radiusModel = new SpinnerNumberModel(1, 1, 10, 1);
            JSpinner radiusSpinner = new JSpinner(radiusModel);
            int option = JOptionPane.showOptionDialog(null, radiusSpinner, radiusstring,
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                radius = radiusModel.getNumber().intValue();
            }

            try{
            // Create and apply the filter
            target.getImage().apply(new MedianFilter(radius));
            target.repaint();
            target.getParent().revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to blur an image with a soft blur filter.
     * </p>
     * 
     * @see SoftBlur
     */
    public class SoftBlurAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        SoftBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        public void actionPerformed(ActionEvent e) {
            //check if image is loaded
            if (!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method if no image is loaded
            }
            try {
                // Create and apply the filter
                target.getImage().apply(new SoftBlur());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to sharpen an image with a sharpen filter.
     * </p>
     * 
     * @see SharpenFilter
     */
    public class SharpenFilterAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        SharpenFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        public void actionPerformed(ActionEvent e) {
            //check if image is loaded
            if (!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method if no image is loaded
            }
            try {
                // Create and apply the filter
                target.getImage().apply(new SharpenFilter());
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to blur an image with a Gaussian blur filter.
     * </p>
     * 
     * @see GaussianBlur
     */
    public class GaussianBlurAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        GaussianBlurAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the Gaussian blur action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the GaussianBlur is triggered.
         * It creates a popup slider and prompts the user for a filter radius, then
         * applies an appropriately sized
         * {@link GaussianBlur}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            //check if image is loaded
            if (!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method if no image is loaded
            }

                // Determine the radius - ask the user.
                int radius = 1;

                // Pop-up dialog box to ask for the radius value.
                JSlider radiusSlider = new JSlider(0, 10, 0);
                radiusSlider.setMajorTickSpacing(1);
                radiusSlider.setPaintTicks(true);
                radiusSlider.setPaintLabels(true);
                radiusSlider.setSnapToTicks(true);
                int option = JOptionPane.showOptionDialog(null, radiusSlider, radiusstring,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiusSlider.getValue();
                }

                // Cancels any operations if the set radius is 0
                if (radius == 0) {
                    return;
                }

                try{
                // Create and apply the filter
                target.getImage().apply(new GaussianBlur(radius));
                target.repaint();
                target.getParent().revalidate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    /**
     * <p>
     * Action to apply block averaging to the image.
     * </p>
     * 
     * @see BlockAveraging
     */
    public class BlockAveragingAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        BlockAveragingAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        public void actionPerformed(ActionEvent e) {
            //check if image is loaded
            if (!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method if no image is loaded
            }

                // Determine the block size - ask the user.
                int blockSize = 1;

                // Pop-up dialog box to ask for the block size value.
                JSlider blockSlider = new JSlider(0, 128, 0);
                blockSlider.setMajorTickSpacing(1);
                blockSlider.setSnapToTicks(true);
                int option = JOptionPane.showOptionDialog(null, blockSlider, blockstring,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    blockSize = blockSlider.getValue();
                }

                // Cancels any operations if the set radius is 0
                if (blockSize == 0) {
                    return;
                }

                try{
                // Create and apply the filter
                target.getImage().apply(new BlockAveraging(blockSize));
                target.repaint();
                target.getParent().revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

   /**
     * <p>
     * Action to emboss an image with Emboss Filter.
     * </p>
     * 
     * @see EmbossFilter
     */
    public class EmbossFilterAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        boolean filterOn = false;

        EmbossFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic, Integer acceleratorKey, Integer acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * 
         * <p>
         * This method is called whenever the EmbossFilterAction is triggered.
         * It prompts the user for a filter direction, then applies an appropriately sized
         * {@link EmbossFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
         public void actionPerformed(ActionEvent e) {
             //check if image is loaded
             if (!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method if no image is loaded
            }

            String[] directions = {directionnorthwest, directionnorth, directionnortheast, directionwest, directioneast, directionsouthwest, directionsouth, directionsoutheast};

            JPanel optionPanel = new JPanel(new GridLayout(4, 2));

            for(String eachDirection: directions){
                JButton dButton = new JButton(eachDirection);

                dButton.addActionListener(new ActionListener() {
                    int d = 0;
                    public void actionPerformed(ActionEvent e){

                        for(int i = 0; i < directions.length; i++){
                            if(directions[i] == eachDirection){
                                d = i;
                                break;
                            }
                        }

                        try{
                            // Create and apply the filter
                            if(filterOn == true){
                                target.getImage().undo();
                                target.getImage().apply(new EmbossFilter(d));
                                target.repaint();
                                target.getParent().revalidate();

                            }else{

                                filterOn = true;
                                target.getImage().apply(new EmbossFilter(d));
                                target.repaint();
                                target.getParent().revalidate();
                            }


                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
                            }     
                    }
                });

                optionPanel.add(dButton);
            }

            int option =JOptionPane.showConfirmDialog(Andie.frame, optionPanel, direction, JOptionPane.OK_OPTION);

            if(filterOn == true && option != JOptionPane.OK_OPTION){

                int choice =JOptionPane.showConfirmDialog(Andie.frame, directionOn, embossDirection2, JOptionPane.YES_NO_OPTION);

                if(choice == JOptionPane.YES_OPTION){
                    filterOn = false;
                    return;
                }else{
                    target.getImage().undo();
                    target.repaint();
                    target.getParent().revalidate(); 
                    filterOn = false;
                    return;
                }
            }
            filterOn = false;
        
        }

    }    

   /**
     * <p>
     * Action to sobel an image with Sobel Filter.
     * </p>
     * 
     * @see SobelFilter
     */
    public class SobelFilterAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        boolean filterOn = false;

        SobelFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic, Integer acceleratorKey, Integer acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * 
         * <p>
         * This method is called whenever the SobelFilterAction is triggered.
         * It prompts the user for a filter direction, then applies an appropriately sized
         * {@link SobelFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
         public void actionPerformed(ActionEvent e) {
             //check if image is loaded
             if (!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error,
                        JOptionPane.ERROR_MESSAGE);
                return;//exit the method if no image is loaded
            }

            String[] directions = {directionhorizontal, directionvertical};

            JPanel optionPanel = new JPanel(new GridLayout(1, 2));

            for(String eachDirection: directions){
                JButton dButton = new JButton(eachDirection);

                dButton.addActionListener(new ActionListener() {
                    int d = 0;
                    public void actionPerformed(ActionEvent e){

                        for(int i = 0; i < directions.length; i++){
                            if(directions[i] == eachDirection){
                                d = i;
                                break;
                            }
                        }

                        try{
                            // Create and apply the filter
                            if(filterOn == true){
                                target.getImage().undo();
                                target.getImage().apply(new EmbossFilter(d));
                                target.repaint();
                                target.getParent().revalidate();

                            }else{

                                filterOn = true;
                                target.getImage().apply(new EmbossFilter(d));
                                target.repaint();
                                target.getParent().revalidate();
                            }


                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
                            }     
                    }
                });

                optionPanel.add(dButton);
            }

            int option =JOptionPane.showConfirmDialog(Andie.frame, optionPanel, direction, JOptionPane.OK_OPTION);

            if(filterOn == true && option != JOptionPane.OK_OPTION){
                
                int choice =JOptionPane.showConfirmDialog(Andie.frame, directionOn, sobelDirection2, JOptionPane.YES_NO_OPTION);

                if(choice == JOptionPane.YES_OPTION){
                    filterOn = false;
                    return;
                }else{
                    target.getImage().undo();
                    target.repaint();
                    target.getParent().revalidate(); 
                    filterOn = false;
                    return;
                }
            }
            filterOn = false;
        
        }

    } 
    
    /**
     * <p>
     * Action to apply random scattering to the image.
     * </p>
     * 
     * @see RandomScattering
     */
    public class RandomScatteringAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;

        RandomScatteringAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey,
                int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        public void actionPerformed(ActionEvent e) {
            //check if image is loaded
            if (!target.hasImage()) {
                JOptionPane.showMessageDialog(null, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method if no image is loaded
            }
                // Determine the scatter size - ask the user.
                int radius = 1;

                // Pop-up dialog box to ask for the scatter radius value.
                JSlider radiuSlider = new JSlider(0, 64, 0);
                radiuSlider.setMajorTickSpacing(1);
                radiuSlider.setSnapToTicks(true);
                int option = JOptionPane.showOptionDialog(null, radiuSlider, blockstring,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                // Check the return value from the dialog box.
                if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    radius = radiuSlider.getValue();
                }

                // Cancels any operations if the set radius is 0
                if (radius == 0) {
                    return;
                }

                try{
                // Create and apply the filter
                target.getImage().apply(new RandomScattering(radius));
                target.repaint();
                target.getParent().revalidate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, apply, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
