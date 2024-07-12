package cosc202.andie;
import java.awt.image.*;
/**
 * <p>
 * ImageOperation to cycle the colour channels of an image.
 * </p>
 * @author Ninja-turtles
 */
public class ColourChannelCycle implements ImageOperation, java.io.Serializable {

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

    int channelsToCycle;

    /**
     * <p>
     * Constructor for Colour Channel Cycling
     * </p>
     */
    ColourChannelCycle(int channelsToCycle) {
        this.channelsToCycle = channelsToCycle;
    }

    /**
     * <p> 
     * Apply the ColourChannelCycle to the image
     * </p>
     * @param input
     * @param selected
     * @return BufferedImage with the colour channels cycled
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
                
                // Cycle the color components (red -> green, green -> blue, blue -> red)
                if(channelsToCycle == 0) {
                    int temp = red;
                    red = green;
                    green = temp;
                } else if(channelsToCycle == 1) {
                    int temp = red;
                    red = blue;
                    blue = temp;
                } else {
                    int temp = blue;
                    blue = green;
                    green = temp;
                }
                
                // Combine the color components
                int cycledPixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                
                // Set the pixel in the output image
                output.setRGB(x, y, cycledPixel);
            }
        }
        return output;
    }

}