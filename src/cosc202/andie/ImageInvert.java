package cosc202.andie;
import java.awt.image.*;


/**
 * <p>
 * ImageOperation to Invert the colour in an image.
 * </p>
 * 
 * @author Ninja-turtles
 */ 
public class ImageInvert implements ImageOperation, java.io.Serializable {
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

    /**
     * <p>
     * Construct an image inversion filter.
     * </p>
     * 
     * <p>
     * Image inverting reverses the colour channels in the image so that for example red becomes cyan and blue becomes yellow.
     * </p>
     */
    ImageInvert() {}

    /**
     * <p>
     * Method to invert the image
     * </p>
     * @param input
     * @param selected
     * @return BufferedImage representing the inverted image
     */
    public BufferedImage apply(BufferedImage input, boolean selected) {
        wasSelected = selected;
        int width = input.getWidth();
        int height = input.getHeight();
        
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = input.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xFF;
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;
                
                // Invert the color components
                int invertedRed = 255 - red;
                int invertedGreen = 255 - green;
                int invertedBlue = 255 - blue;
                
                // Combine the inverted color components
                int invertedPixel = (alpha << 24) | (invertedRed << 16) | (invertedGreen << 8) | invertedBlue;
                
                // Set the pixel in the output image
                output.setRGB(x, y, invertedPixel);
            }
        }
        return output;
    }
    
}