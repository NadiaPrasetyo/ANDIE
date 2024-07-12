package cosc202.andie;

import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.Action;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import java.awt.Image;

/**
 * <p>
 * ToolBar class to create a toolbar for the application.
 * </p>
 * 
 * <p>
 * Uses icons and images for the toolbar
 * </p>
 * @author Ninja-turtles
 */
public class ToolBar {
    public String error = "Error!";


    private JFrame f;
    private EditActions editActions;
    private ViewActions viewActions;
    //private EditActions editActions;
    public ToolBar(JFrame f, EditActions editActions, ViewActions viewActions){ 
        this.f = f; 
        this.editActions = editActions;
        this.viewActions = viewActions;
    }

    /**
     * <p>
     * Create a toolbar containing the list of tool actions.
     * </p>
     *  @throws IOException
     */
    public JToolBar createToolBar() throws IOException {

        Image toolbarImage = ImageIO.read(Andie.class.getClassLoader().getResource("tb.png"));

        // Create a toolbar
        ToolBarBackground toolbar = new ToolBarBackground(toolbarImage);
        try {
            // Combine actions and icons from both EditActions and ViewActions
            ArrayList<Action> editActionsList = new ArrayList<>(editActions.getActions());
            ArrayList<Action> viewActionsList = new ArrayList<>(viewActions.getActions());

            

            ArrayList<ImageIcon> editIcons = new ArrayList<>(editActions.getIcons());
            ArrayList<ImageIcon> viewIcons = new ArrayList<>(viewActions.getIcons());


            // Set the new buttons for the toolbar from the edit actions 
            for (int i = 0; i < editIcons.size(); i++) {
                JButton newButton = new JButton(editActionsList.get(i));
                // Set the name of the new button
                newButton.setName(
                        editActionsList.get(i).getValue(Action.NAME).toString().toLowerCase().replaceAll("\\s+", ""));
                // Set the icon for the button
                newButton.setIcon(editIcons.get(i));
                // Add the button to the toolbar
                toolbar.add(newButton);
            }
            // Set the new buttons for the toolbar from the view actions 
            for (int i = 0; i < viewIcons.size(); i++) {
                JButton newButton = new JButton(viewActionsList.get(i));
                // Set the name of the new button
                newButton.setName(
                    viewActionsList.get(i).getValue(Action.NAME).toString().toLowerCase().replaceAll("\\s+", ""));
                // Set the icon for the button
                newButton.setIcon(viewIcons.get(i));
                // Add the button to the toolbar
                toolbar.add(newButton);
            }
            // Add the toolbar to the NORTH position of the frame
            f.getContentPane().add(toolbar, BorderLayout.NORTH);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, null,  error, JOptionPane.ERROR_MESSAGE);
        }
        return toolbar;
    }
}
