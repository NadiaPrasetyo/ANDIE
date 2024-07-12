
package cosc202.andie;

import java.awt.*;
import javax.swing.*;


/**
 * <p>
 * UI display element for {@link EditableImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JMenuBar} to allow us to customize the background of the menubar for Andie.
 * in and out. 
 * </p>
 * 
 * @author Ninja-turtles
 * @version 1.0
 */
public class ToolBarBackground extends JToolBar{

    private Image background;

    /**
     * <p>
     * A constructor method for the ToolBarBackgound
     * <p>
     * 
     */
    public ToolBarBackground(Image background){
        this.background = background;
    }

    /**
     * <p>
     * The component the toolbar will be drawn on
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g);
    
        // Draw the image
        if (background != null) {

            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        }

    }
  
}
