package cosc202.andie;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Soft Blur.
 * </p>
 * 
 * <p>
 * This class is used to apply a Soft Blur to an image.
 * </p>
 * @author Ninja-turtles
 */
public class SoftBlur implements ImageOperation, java.io.Serializable {
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
     * Constructor for SoftBlur
     * </p>
     */
    SoftBlur() {
    }

    /**
     * <p>
     * Applies the Soft Blur filter to the image
     * </p>
     * 
     * @param input The image to apply the Soft Blur to
     * @param selected The selected state of the image
     * @return The image with the Soft Blur applied
     */
    public BufferedImage apply (BufferedImage input, boolean selected) {
        // The values for the kernel as a 9-element array
        float [] array = { 0 , 1/8.0f, 0 , 1/8.0f, 1/2.0f, 1/8.0f, 0 , 1/8.0f, 0 };
        // Make a 3x3 filter from the array
        Kernel kernel = new Kernel(3, 3, array);
        // Apply this as a convolution - same code as in MeanFilter
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage newInput = FilterIssueHandler.scaleUpImage(input, (kernel.getWidth()-1)/2);
        BufferedImage newOutput = new BufferedImage(newInput.getColorModel(), newInput.copyData(null), newInput.isAlphaPremultiplied(), null);
        convOp.filter(newInput, newOutput);

        return newOutput.getSubimage((kernel.getWidth()-1)/2, (kernel.getWidth()-1)/2, input.getWidth(), input.getHeight());
        
    }
}