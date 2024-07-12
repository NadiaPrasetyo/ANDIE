package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.image.*;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.JOptionPane;

/**
 * <p>
 * ImageOperation to apply a Resize Image.
 * </p>
 * 
 * <p>
 * This class is used to resize an image either larger or smaller.
 * </p>
 * @author Ninja-turtles
 */
public class ResizeImage implements ImageOperation, java.io.Serializable {
    /* Selected state*/
    boolean wasSelected;

    /**
     * <p>
     * Accessor for the selected state when it was applied
     * </p>
     * @return boolean representing the selected state
     */
    public boolean getSelected() {
        return wasSelected;
    }
    
    boolean larger;

    /**
     * <p>
     * Constructor for Resize Image
     * </p>
     * @param smallLarge
     */
    ResizeImage(boolean smallLarge) {
        this.larger = smallLarge;
    }

    /**
     * <p>
     * Change the size of the image with the Resize() method
     * </p>
     * @param input
     * @return the result image due to the button pressed
     */
    public BufferedImage Resize(BufferedImage input) {
        // grab the width and height from the input BufferedImage
        int width = input.getWidth();
        int height = input.getHeight();
        // create new width and height variables
        int newWidth;
        int newHeight;
        // run the try catch on the operations

        // check the bool value to know which width and height to use
        if (larger) {
            newWidth = width * 2;
            newHeight = height * 2;

        } else {
            newWidth = width / 2;
            newHeight = height / 2;
        }
        //BufferedImage result = null;
        //Image result = null;
        BufferedImage bufferedScaled = null;
        Image scaled = null;
        try {
            
            // create a new BufferedImage with the new width and height
            //result = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            int scalingHint = larger ? Image.SCALE_SMOOTH : Image.SCALE_AREA_AVERAGING;
            scaled = input.getScaledInstance(newWidth, newHeight, scalingHint);
            bufferedScaled = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_RGB);
            
            Graphics2D g2dImage = bufferedScaled.createGraphics();
            // draw the graphics
            //g2dImage.drawImage(result, 0, 0, newWidth, newHeight, null);
            g2dImage.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g2dImage.drawImage(scaled, 0, 0, null);

            // dispose of the graphics as they are no longer needed and take up space to
            g2dImage.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "e");
        }

        // return the resulting BufferedImage
        return bufferedScaled;
    }

    /**
     * <p>
     * Apply method for the ResizeImage class
     * </p>
     * @param input The image to apply the resize operation to
     * @param selected The selected state of the operation
     * @return the resized BufferedImage via the Resize method
     */
    @Override
    public BufferedImage apply(BufferedImage input, boolean selected) {
        // set the selected state
        wasSelected = selected;
        // return the image after the resize method
        return Resize(input);
    }

}
