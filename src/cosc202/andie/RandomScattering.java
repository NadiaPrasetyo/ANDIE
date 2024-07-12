package cosc202.andie;
import java.awt.image.*;
import java.util.Random;

/**
 * <p>
 * ImageOperation to perform random scattering effect on an image.
 * </p>
 * @author Ninja-turtles
 */
public class RandomScattering implements ImageOperation, java.io.Serializable {
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

    int radius;
    Random random;

    /**
     * <p>
     * Construct a random scattering filter with given radius.
     * </p>
     * @param radius The radius of the newly constructed RandomScattering
     */
    RandomScattering(int radius) {
        this.radius = radius;
        this.random = new Random();
    }

    /**
     * <p>
     * Apply a random scattering effect to an image.
     * </p>
     * @param input The image to apply the random scattering effect to.
     * @param selected The selected state of the image
     * @return BufferedImage representing the image with random scattering effect
     */
    public BufferedImage apply(BufferedImage input, boolean selected) {
        wasSelected = selected;
        int width = input.getWidth();
        int height = input.getHeight();

        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Determine the random pixel within the radius
                int newX = x + random.nextInt(2 * radius + 1) - radius;
                int newY = y + random.nextInt(2 * radius + 1) - radius;

                // Ensure the random pixel falls within the image bounds
                newX = Math.max(0, Math.min(width - 1, newX));
                newY = Math.max(0, Math.min(height - 1, newY));

                // Get the color of the randomly selected pixel
                int randomPixel = input.getRGB(newX, newY);

                // Set the pixel in the output image
                output.setRGB(x, y, randomPixel);
            }
        }
        return output;
    }
}
