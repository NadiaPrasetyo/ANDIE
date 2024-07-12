package cosc202.andie;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to adjust contrast of an image.
 * </p>
 * @author Ninja-turtles
 */ 
public class Contrast implements ImageOperation, java.io.Serializable {
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

    int factor;

    /**
     * <p>
     * Construct a contrast adjustment filter.
     * </p>
     */
    Contrast(int factor) {
        this.factor = factor;
    }

    /**
     * <p>
     * Apply the contrast adjustment filter to an image.
     * </p>
     * @param input
     * @param selected
     * @return BufferedImage with contrast adjusted
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
                
                // Adjust contrast for each color component
                double contrastFactor = factor / 128.0;
                int contrastRed = (int)((red-128)*contrastFactor)+128;
                int contrastGreen = (int)((green-128)*contrastFactor)+128;
                int contrastBlue = (int)((blue-128)*contrastFactor)+128;

                // Clamp values to valid range [0, 255]
                contrastRed = Math.min(Math.max(0, contrastRed), 255);
                contrastGreen = Math.min(Math.max(0, contrastGreen), 255);
                contrastBlue = Math.min(Math.max(0, contrastBlue), 255);
                
                // Combine the adjusted color components
                int contrastPixel = (alpha << 24) | (contrastRed << 16) | (contrastGreen << 8) | contrastBlue;
                
                // Set the pixel in the output image
                output.setRGB(x, y, contrastPixel);
            }
        }
        return output;
    }
}
