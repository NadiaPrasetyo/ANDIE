package cosc202.andie;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to Brighten an image.
 * </p>
 * @author Ninja-turtles
 */ 
public class Brightness implements ImageOperation, java.io.Serializable {
    /* Selected state*/
    boolean wasSelected;

    /**
     * Accessor for the selected state when it was applied
     * @return boolean representing the selected state
     */
    public boolean getSelected() {
        return wasSelected;
    }

    int amount;

    /**
     * <p>
     * Construct an brighten filter.
     * </p>
     */
    Brightness(int amount) {
        this.amount = amount;
    }

    /**
     * Checks whether the current Brightness object is equal to another object.
     * Two Brightness objects are considered equal if the other object is not null
     * and is an instance of Brightness class.
     *
     * @param obj The object to compare with the current Brightness object.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        // Check if obj is not null and is an instance of Brightness class
        return obj != null && obj instanceof Brightness;
    }

    /**
     * <p>
     * Apply the brighten filter to an image.
     * </p>
     * 
     * @param input
     * @param selected
     * @return BufferedImage with brightness adjusted
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
                int brightRed =  red + amount;
                int brightGreen = green + amount;
                int brightBlue = blue + amount;

                // Clamp values to valid range [0, 255]
                brightRed = Math.min(Math.max(0, brightRed), 255);
                brightGreen = Math.min(Math.max(0, brightGreen), 255);
                brightBlue = Math.min(Math.max(0, brightBlue), 255);
                
                // Combine the inverted color components
                int brightPixel = (alpha << 24) | (brightRed << 16) | (brightGreen << 8) | brightBlue;
                
                // Set the pixel in the output image
                output.setRGB(x, y, brightPixel);
            }
        }
        return output;
    }
    
}