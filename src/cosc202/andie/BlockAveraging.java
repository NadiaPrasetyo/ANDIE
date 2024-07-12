package cosc202.andie;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to perform block averaging on an image.
 * </p>
 * @author Ninja-turtles
 */
public class BlockAveraging implements ImageOperation, java.io.Serializable {
    boolean wasSelected;

    int blockSize;

    /**
     * <p>
     * Construct a block averaging filter.
     * </p>
     */
    BlockAveraging(int blockSize) {
        this.blockSize = blockSize;
    }

    /**
     * <p>
     * Apply the block averaging filter to an image.
     * </p>
     * @param input
     * @param selected
     * @return BufferedImage with block averaging applied
     */
    public BufferedImage apply(BufferedImage input, boolean selected) {
        wasSelected = selected;
        int width = input.getWidth();
        int height = input.getHeight();

        //creates a new image with the same dimensions as the input image
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y += blockSize) {
            for (int x = 0; x < width; x += blockSize) {
                // Compute the average color values for the current block
                int totalRed = 0, totalGreen = 0, totalBlue = 0, totalPixels = 0;

                for (int dy = 0; dy < blockSize && y + dy < height; dy++) {
                    for (int dx = 0; dx < blockSize && x + dx < width; dx++) {
                        int pixel = input.getRGB(x + dx, y + dy);
                        totalRed += (pixel >> 16) & 0xFF;
                        totalGreen += (pixel >> 8) & 0xFF;
                        totalBlue += pixel & 0xFF;
                        totalPixels++;
                    }
                }

                // Calculate the average color values
                int avgRed = totalRed / totalPixels;
                int avgGreen = totalGreen / totalPixels;
                int avgBlue = totalBlue / totalPixels;

                // Set the same average color values for all pixels in the block
                for (int dy = 0; dy < blockSize && y + dy < height; dy++) {
                    for (int dx = 0; dx < blockSize && x + dx < width; dx++) {
                        int alpha = (input.getRGB(x + dx, y + dy) >> 24) & 0xFF;
                        int avgPixel = (alpha << 24) | (avgRed << 16) | (avgGreen << 8) | avgBlue;
                        output.setRGB(x + dx, y + dy, avgPixel);
                    }
                }
            }
        }
        return output;
    }

    /**
     * Accessor for the selected state when it was applied
     * @return boolean representing the selected state
     */
    public boolean getSelected() {
        return wasSelected;
    }
}
