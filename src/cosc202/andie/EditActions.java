package cosc202.andie;

import java.util.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.*;



 /**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be added.
 * </p>
 * 
 * <p> 
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA 4.0</a>
 * </p>
 * 
 * @author Steven Mills and Ninja-turtles
 * @version 1.0
 */
public class EditActions{
    
    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;
    /**
     * A list of icons for the tool bar menu.
     */
    protected ArrayList<ImageIcon> imageIconList;

    public String error = "Error!";
    public String loadimagefirst = "Please load an image first.";
    public String errorundo = "There is nothing to undo.";
    public String errorredo = "There is nothing to redo.";
    public String errorselect = "Something went wrong with select";
    public String selectoptions = "Select Options";
    public String selectbutton = "Select New";
    public String confirmbutton = "Confirm Selection";
    public String cropbutton = "Crop";
    public String cancelbutton = "Cancel Selection";
    public String selectconfirmerror = "Please confirm the selection first.";
    public String areaselecterror = "Please select an area first.";

    public String drawerror = "Something went wrong with draw";
    public String drawoptions = "Draw Options";
    public String drawmenu = "Draw";
    public String rectangle = "Rectangle";
    public String square = "Square";
    public String oval = "Oval";
    public String circle = "Circle";
    public String freehand = "Freehand";
    public String line = "Line";
    public String drawimage = "Draw a shape or lines on the image";
    public String fillshape = "Fill a shape with a specified colour";

    public String setsettings = "Settings";
    public String setcolor = "Color";
    public String setthickness = "Thickness";

    public String fillmenu = "Fill";
    public String applydraw = "Apply";
    public String cleardraw = "Clear";
    public String undoclear = "Undo Clear";
    public String errorclear = "There is nothing to clear.";

    

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     * Keyboard shortcut for:
     * Undo: Z
     * Redo: R
     * Select: Y
     * Draw: D
     */
    public EditActions() {
        actions = new ArrayList<Action>();
        imageIconList = new ArrayList<ImageIcon>();
        //
        actions.add(new UndoAction("Undo", null, "Undo", Integer.valueOf(KeyEvent.VK_Z), Integer.valueOf(KeyEvent.VK_Z), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new RedoAction("Redo", null, "Redo", Integer.valueOf(KeyEvent.VK_R), Integer.valueOf(KeyEvent.VK_R), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new SelectAction("Select", null, "Select", Integer.valueOf(KeyEvent.VK_S), Integer.valueOf(KeyEvent.VK_Y), Integer.valueOf(ActionEvent.CTRL_MASK)));
        actions.add(new DrawActions("Draw", null, "Draw", Integer.valueOf(KeyEvent.VK_D), Integer.valueOf(KeyEvent.VK_D), Integer.valueOf(ActionEvent.CTRL_MASK)));
        //create the icons 
        //image icons created
        ImageIcon undoIcon = new ImageIcon(ToolActions.class.getClassLoader().getResource("undoIcon.png"));
        undoIcon = resizeIcon(undoIcon, 50, 50);
        //
        ImageIcon redoIcon = new ImageIcon(ToolActions.class.getClassLoader().getResource("redoIcon1.png"));
        redoIcon = resizeIcon(redoIcon, 50, 50);
        //add the icons to the iconslist
        //add the icons to the list
        imageIconList.add(undoIcon);
        imageIconList.add(redoIcon);
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
     * Create a menu containing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setName("edit");

        for (Action action: actions) {
            editMenu.add(new JMenuItem(action));
        }

        for (int j = 0; j < editMenu.getItemCount(); j++) {
            JMenuItem menuItem = editMenu.getItem(j);
            if (menuItem != null) {
                String key = menuItem.getActionCommand();
                key = key.toLowerCase();
                key = key.replaceAll("\\s+","");
                menuItem.setName(key);
                menuItem.setBackground(new Color(173, 216, 230));

            }
        }

        return editMenu;
    }


    /**
     * <p>
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
            target.getImage().undo();
            target.repaint();
            target.getParent().revalidate();
            } catch (EmptyStackException ex) {
                JOptionPane.showMessageDialog(null, errorundo, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

     /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#redo()
     */   
    public class RedoAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        /**
         * <p>
         * Create a new redo action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        
        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try{
            target.getImage().redo();
            target.repaint();
            target.getParent().revalidate();
            } catch (EmptyStackException ex) {
                JOptionPane.showMessageDialog(null, errorredo, error, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * <p>
     * Action to Select a region
     * </p>
     * 
     * @see Select
     * @see SelectionArea
     */
    public class SelectAction extends ImageAction {
        int acceleratorKey;
        int acceleratorModifier;
        private static JDialog selectFrame;
        JButton selectButton;
        JButton cropButton;
        JButton cancelButton;

        /**
         * <p>
         * Create a new Select action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        SelectAction(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * Accessor for the frame to close when select is undone
         */
        public static JDialog getSelectFrame(){
            return selectFrame;
        }

        /**
         * <p>
         * Callback for when the select action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the SelectAction is triggered.
         * It adds a mouse listener allowing the user to select a rectangular area on the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            //check if there is an image in the target
            if(!target.hasImage()){
                JOptionPane.showMessageDialog(null, errorredo, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method
            }

            try{
                //create a new select object
                Select s = new Select(target);

                //create a new dialog for selection
            selectFrame = new JDialog();
            selectFrame.setTitle(selectoptions);
            JPanel selectPanel = new JPanel();
            //create buttons for the panel, including a select, crop and cancel function
            selectButton = new JButton(selectbutton);
            cropButton = new JButton(cropbutton);
            cancelButton = new JButton(cancelbutton);

            SelectListener listener = new SelectListener(); // a listener for the buttons to be shared across the buttons
            listener.setSelect(s); //set the select object in the listener

            //add action listeners to the buttons
            selectButton.addActionListener(listener);
            cropButton.addActionListener(listener);
            cancelButton.addActionListener(listener);
            
            //add the buttons to the panel
            selectPanel.add(selectButton);
            selectPanel.add(cropButton);
            selectPanel.add(cancelButton);

            selectPanel.setVisible(true);//set the panel to visible

            //add the panel to the frame and configure the frame defaults
            selectFrame.add(selectPanel);
            selectFrame.setSize(500, 75);
            selectFrame.setLocation(100, 50);
            selectFrame.setAlwaysOnTop(enabled);// make sure the select frame is always on top
            selectFrame.setVisible(true);
            selectFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            
            } catch (Exception ex){
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, errorselect, error, JOptionPane.ERROR_MESSAGE);
            }
            
        }

        /**
         * <p>
         * ActionListener for the select options dialog
         * </p>
         */
        class SelectListener implements ActionListener{
            Select select;
            static Boolean isConfirmed = false;

            /**
             * <p>
             * ActionListener for the selectOptions dialog
             * </p>
             * 
             * <p>
             * This method is called whenever the SelectAction is triggered.
             * Controls the buttons for the select options dialog.
             * </p>
             * 
             * @param e The event triggering this callback.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try{                
                    //check which button was clicked
                if (e.getSource() == selectButton){
                    //Allow user to select a new area                        
                    target.addMouseListener(select);
                    selectButton.setText(confirmbutton);//changes the text of the button to confirm selection

                    //if the confirm button is clicked
                     if (target.getImage().isSelected()){
                         isConfirmed = true;
                         selectButton.setVisible(false);//hide the select button
                         createSelection();// saves selection
                         target.removeMouseListener(select);
                     }

                } else if (e.getSource() == cropButton){
                    //check that a selection has been made and confirm selection button has been clicked
                    if (isConfirmed){
                    if (target.getImage().isSelected()){
                        //crop the selected area
                        select.removeSelection(target.getImage().getCurrentImage(), true);
                        crop();
                        target.repaint();
                        target.getParent().revalidate();
                        isConfirmed = false;
                    } else {
                        JOptionPane.showMessageDialog(null, areaselecterror, error, JOptionPane.ERROR_MESSAGE);
                    }
                    } else {
                        JOptionPane.showMessageDialog(null, selectconfirmerror, error, JOptionPane.ERROR_MESSAGE);
                    }

                } else if (e.getSource() == cancelButton){
                    //check that a selection has been made and confirm selection button has been clicked
                    if(isConfirmed){
                    if (target.getImage().isSelected()){
                    //cancel the selection
                    select.deSelect();
                    target.repaint();
                    target.getParent().revalidate();
                    target.getImage().setSelected(false);
                    isConfirmed = false;
                    } else {
                        JOptionPane.showMessageDialog(null, areaselecterror, error, JOptionPane.ERROR_MESSAGE);
                    }
                    } else {
                        JOptionPane.showMessageDialog(null, selectconfirmerror, error, JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex){
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, errorselect, error, JOptionPane.ERROR_MESSAGE);
                
            }
            }

            /**
             * <p>
             * Mutator for select object
             * </p>
             * @param select the select object
             */
            public void setSelect(Select select){
                this.select = select;
            }

            /**
             * <p>
             * Crops the selected area
             * Calls the crop operation
             * </p>
             * 
             * @see CropOperation 
             */
            public void crop(){
                //removes select options dialog
                EditActions.SelectAction.getSelectFrame().setVisible(false);
                //update the selection state
                target.getImage().setSelected(false);
                //Add the crop operation to the stack of operations
                CropOperation cropped = new CropOperation(target.getImage().getSelectedArea());
                target.getImage().apply(cropped);

            }

            /**
             * <p>
             * Creates a selection area
             * Calls the apply method on the selection area
             * </p>
             */
            public void createSelection(){
                SelectionArea area = new SelectionArea(target.getImage().getSelectedArea());
                target.getImage().apply(area);
            }

            /**
             * <p>
             * Accessor for the confirmed state
             * </p>
             * @return boolean representing the confirmed state
             */
            public static boolean getIsConfirmed(){
                return isConfirmed;
            }

            /**
             * <p>
             * Mutator for the confirmed state
             * </p>
             * @param isConfirmed the confirmed state
             */
            public static void setIsConfirmed(boolean isConfirmed){
                SelectListener.isConfirmed = isConfirmed;
            }

            
        }
    }

    /**
     * <p>
     * Action to draw shapes on the image
     * There are many options for drawing shapes including rectangles, squares, ovals, circles, freehand and lines
     * Colour, Fill and Thickness can also be set
     * </p>
     * 
     * @see drawShapeOperation
     */
    public class DrawActions extends ImageAction implements MouseListener, MouseMotionListener{
        int acceleratorKey;
        int acceleratorModifier;

        //variables for the shape
        private int x1, y1, x2, y2;
        private char shape = 'r'; //default shape is a rectangle
        private int thickness = 1; //default thickness is 1
        private Color color = Color.BLACK; //default color is black
        private boolean fill = false; //default fill is false
        private ArrayList<Point> points;

        //buttons for the draw options
        private static JDialog drawFrame;
        private JMenuBar drawMenuBar;

        //menu items for the draw options
        private JMenu fillMenu;
        private JMenu drawMenu;
        private JMenu settingsMenu;
        
        ArrayList<drawShapeOperation> drawOperations = new ArrayList<drawShapeOperation>();
        
        /** 
         * <p>
         * Accessor for the draw coordinates
         * </p>
         * used in testing @see DrawActionsTest.java
         * @return int representing the x1 coordinate
         */
        public int getX1(){
            return x1;
        }
        /** 
         * <p>
         * Accessor for the draw coordinates
         * </p>
         * @return int representing the y1 coordinate
         */
        public int getY1(){
            return y1;
        }
        /** 
         * <p>
         * Accessor for the draw coordinates
         * </p>
         * @return int representing the x2 coordinate
         */
        public int getX2(){
            return x2;
        }
        /** 
         * <p>
         * Accessor for the draw coordinates
         * </p>
         * @return int representing the y2 coordinate
         */
        public int getY2(){
            return y2;
        }

        /**
         * <p>
         * Mutator for shape
         * </p>
         * used in testing @see DrawActionsTest.java
         */
        public void setShape(char shape){
            this.shape = shape;
        }

        /**
         * <p>
         * Accessor for the points
         * </p>
         * used in testing @see DrawActionsTest.java
         */
        public java.util.ArrayList<Point> getPoints(){
            return points;
        }
        

        /**
         * <p>
         * Create a new Draw action.
         * </p>
         * 
         * @param name The name of the action (ignored if null).
         * @param icon An icon to use to represent the action (ignored if null).
         * @param desc A brief description of the action  (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut  (ignored if null).
         */
        DrawActions(String name, ImageIcon icon, String desc, Integer mnemonic, int acceleratorKey, int acceleratorModifier) {
            super(name, icon, desc, mnemonic);
            this.acceleratorKey = acceleratorKey;
            this.acceleratorModifier = acceleratorModifier;
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(acceleratorKey, acceleratorModifier));
        }

        /**
         * <p>
         * Callback for when the draw action is triggered.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            //check if there is an image in the target
            if(!target.hasImage()){
                JOptionPane.showMessageDialog(Andie.frame, loadimagefirst, error, JOptionPane.ERROR_MESSAGE);
                return;//exit the method
            }
            try{
                //initialise default values for the draw frame
                drawFrame = new JDialog();
                drawFrame.setTitle(drawoptions);
                drawFrame.setSize(500, 75);
                drawFrame.setLocation(100, 50);
                drawFrame.setAlwaysOnTop(enabled);

                //create a new arraylist to store the draw operations
                drawOperations = new ArrayList<drawShapeOperation>();

                //create a new menu bar for the draw options
                drawMenuBar = new JMenuBar();

                //create the draw options menu
                drawMenu = new JMenu(drawmenu);
                drawMenu.setMnemonic(KeyEvent.VK_A);
                drawMenu.getAccessibleContext().setAccessibleDescription(drawimage);

                //create menu items for the draw menu
                JMenuItem rectangleItem = new JMenuItem(rectangle);
                JMenuItem squareItem = new JMenuItem(square);
                JMenuItem ovalItem = new JMenuItem(oval);
                JMenuItem circleItem = new JMenuItem(circle);
                JMenuItem freehandItem = new JMenuItem(freehand);
                JMenuItem lineItem = new JMenuItem(line);      

                //add action listeners to the menu items
                rectangleItem.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        fill = false;
                        shape = 'r';
                    }
                });

                squareItem.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        fill = false;
                        shape = 's';
                    }
                });

                ovalItem.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        fill = false;
                        shape = 'o';
                    }
                });

                circleItem.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        fill = false;
                        shape = 'c';
                    }
                });

                freehandItem.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        fill = false;
                        shape = 'f';
                    }
                });

                lineItem.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        fill = false;
                        shape = 'l';
                    }
                });

                //add the menu items to the draw menu
                drawMenu.add(rectangleItem);
                drawMenu.add(squareItem);
                drawMenu.add(ovalItem);
                drawMenu.add(circleItem);
                drawMenu.add(freehandItem);
                drawMenu.add(lineItem);

                //create the fill menu and initialise it default values
                fillMenu = new JMenu(fillmenu);
                fillMenu.setMnemonic(KeyEvent.VK_F);
                fillMenu.getAccessibleContext().setAccessibleDescription(fillshape);

                //create menu items for the fill menu
                JMenuItem rectangleI = new JMenuItem(rectangle);
                JMenuItem squareI = new JMenuItem(square);
                JMenuItem ovalI = new JMenuItem(oval);
                JMenuItem circleI = new JMenuItem(circle);

                //add action listeners to the fill menu items
                rectangleI.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        shape = 'r';
                        fill = true;
                    }
                });

                squareI.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        shape = 's';
                        fill = true;
                    }
                });

                ovalI.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        shape = 'o';
                        fill = true;
                    }
                });

                circleI.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        shape = 'c';
                        fill = true;
                    }
                });

                //add the menu items to the fill menu
                fillMenu.add(rectangleI);
                fillMenu.add(squareI);
                fillMenu.add(ovalI);
                fillMenu.add(circleI);

                //create the settings menu and initialise it default values
                settingsMenu = new JMenu(setsettings);
                settingsMenu.setMnemonic(KeyEvent.VK_S);
                settingsMenu.getAccessibleContext().setAccessibleDescription(setsettings);

                //create menu items for the settings menu
                JMenuItem colorItem = new JMenuItem(setcolor);
                JMenuItem thicknessItem = new JMenuItem(setthickness);
                
                //add action listeners to the settings menu items
                colorItem.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        color = JColorChooser.showDialog(null, setcolor, color);

                    }
                });

                thicknessItem.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        // Pop-up dialog box to ask for the thickness value.
                        JSlider thicknessSlider = new JSlider(1, 20, 1);
                        thicknessSlider.setPreferredSize(new java.awt.Dimension(400, 50));
                        thicknessSlider.setMajorTickSpacing(1);
                        thicknessSlider.setPaintTicks(true);
                        thicknessSlider.setPaintLabels(true);
                        thicknessSlider.setSnapToTicks(true);
                        int option = JOptionPane.showOptionDialog(Andie.frame, thicknessSlider, setthickness, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                        // Check the return value from the dialog box.
                        if (option == JOptionPane.CANCEL_OPTION) {
                            return;
                        } else if (option == JOptionPane.OK_OPTION) {
                            thickness = thicknessSlider.getValue();
                        }
                    }
                });

                //add the menu items to the settings menu
                settingsMenu.add(colorItem);
                settingsMenu.add(thicknessItem);               

                //create clear menu item and add action listener to it
                JMenuItem clearDraw = new JMenuItem(cleardraw);
                clearDraw.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if (drawOperations.isEmpty()){
                            JOptionPane.showMessageDialog(Andie.frame, errorclear, error, JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        for (drawShapeOperation draw : drawOperations){
                            target.getImage().removeOperation(draw);
                            
                        }
                        target.repaint();
                        target.getParent().revalidate();
                    }
                });

                //create undo clear menu item and add action listener to it
                JMenuItem undoClear = new JMenuItem(undoclear);
                undoClear.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if (drawOperations.isEmpty()){
                            JOptionPane.showMessageDialog(Andie.frame, errorundo, error, JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        for (drawShapeOperation draw : drawOperations){
                            target.getImage().apply(draw);
                            
                        }
                    }
                });

                //create apply draw menu item and add action listener to it
                JMenuItem applyDraw = new JMenuItem(applydraw);
                applyDraw.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        target.removeMouseListener(EditActions.DrawActions.this);
                        target.removeMouseMotionListener(EditActions.DrawActions.this);
                        drawFrame.dispose();
                    }
                });

                //add all the menus and menu items to the draw menu bar
                drawMenuBar.add(drawMenu);
                drawMenuBar.add(fillMenu);
                drawMenuBar.add(settingsMenu);
                drawMenuBar.add(clearDraw);
                drawMenuBar.add(undoClear);
                drawMenuBar.add(applyDraw);

                //add the menu bar to the frame and configure the frame defaults
                drawFrame.add(drawMenuBar);

                //set the frame to visible
                drawFrame.setVisible(true);
                drawFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                //add a mouse listener to the target
                target.addMouseListener(this);
                target.addMouseMotionListener(this);
            
            
            } catch (Exception ex){
                System.out.println(ex);
                JOptionPane.showMessageDialog(Andie.frame, drawerror, error, JOptionPane.ERROR_MESSAGE);
            }
        }

        
        /**
         * <p>
         * Method for mouse pressed event
         * creates the starting point of the shape
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void mousePressed(MouseEvent e) {
            target.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            this.x1 = e.getX();
            this.y1 = e.getY();
            if(shape == 'f'){
                points = new ArrayList<Point>();
                points.add(e.getPoint());
            }
            
        }

        /**
         * <p>
         * Method for mouse released event
         * creates the ending point of the shape
         * calls the appropriate method to draw the shape/fill the shape
         * </p>
         * @param e The event triggering this callback.
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            //System.out.println("Mouse pressed: " + this.x1 + ", " + this.y1 + " Mouse released: " + this.x2 + ", " + this.y2 );
            target.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            this.x2 = e.getX();
            this.y2 = e.getY();

            createShape(shape, this.x1, this.y1, this.x2, this.y2);
            
        }

        /**
         * <p>
         * Method for mouse dragged event
         * used in freehand drawing
         * </p>
         * @param e The event triggering this callback.
         * @see drawShapeOperation
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            if (shape == 'f'){
                //add the point to the arraylist
                points.add(e.getPoint());
                //System.out.println("Mouse dragged: " + e.getX() + ", " + e.getY());
            }
            
        }

        /** unused method */
        @Override
        public void mouseMoved(MouseEvent e) {
        }

        /** unused method */
        @Override
        public void mouseEntered(MouseEvent e) {            
        }

        /** unused method */
        @Override
        public void mouseExited(MouseEvent e) {   
        }
        
        /** unused method */
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        /**
         * <p>
         * Method to create the shape
         * calls the drawShapeOperation class to draw the shape
         * </p>
         * @see drawShapeOperation
         * @param shape the shape to be drawn and parameters for the shape
         */
        public void createShape(char shape, int x1, int y1, int x2, int y2){
            try{
                int width, height;
                //calculate the width and height of the shape
                width = x2>x1?x2-x1:x1-x2;
                height = y2>y1?y2-y1:y1-y2;
                //INITIALISE THE DRAW SHAPE OPERATION
                drawShapeOperation drawShape = null;

            //checks what shape has been chosen using switch
            switch(shape){
                case 'r':
                    drawShape = new drawShapeOperation(shape, x1, y1, width, height, thickness, color, fill);
                    break;
                case 's':
                    drawShape = new drawShapeOperation(shape, x1, y1, width, height, thickness, color, fill);
                    break;
                case 'o':
                    drawShape = new drawShapeOperation(shape, x1, y1, width, height, thickness, color, fill);
                    break;
                case 'c':
                    drawShape = new drawShapeOperation(shape, x1, y1, width, height, thickness, color, fill);
                    break;
                case 'f':
                    drawShape = new drawShapeOperation(points, thickness, color, fill);
                    break;
                case 'l':
                    drawShape = new drawShapeOperation(shape, x1, y1, x2, y2, thickness, color, fill);
                    break;
            }

            //apply the draw shape operation to the image
            target.getImage().apply(drawShape);
            drawOperations.add(drawShape);//add the draw shape operation to the arraylist

            target.repaint();
            target.getParent().revalidate();
            } catch (Exception ex){
                System.out.println(ex);
                JOptionPane.showMessageDialog(null, drawerror, error, JOptionPane.ERROR_MESSAGE);
                throw ex;
            }
            
            
        }

        /**
         * <p>
         * Mutator for the thickness
         * </p>
         * @param thickness the thickness of the shape
         */
        public void setThickness(int thickness){
            this.thickness = thickness;
        }

        /**
         * <p>
         * Mutator for the color
         * </p>
         * @param color the color of the shape
         */
        public void setColor(Color color){
            this.color = color;
        }



    }

    
}
