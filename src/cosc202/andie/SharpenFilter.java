package cosc202.andie;
import java.awt.image.*;


/**
 * <p>
 * ImageOperation to apply a Sharpen Filter.
 * </p>
 * 
 * <p>
 * This class is used to apply a Sharpen filter to an image.
 * </p>
 * @author Ninja-turtles
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {
    /* Selected state*/
    boolean wasSelected;

    //datafield for kernel array
    private float[] array;

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
     * Checks whether the current SharpenFilter object is equal to another object.
     * Two SharpenFilter objects are considered equal if the other object is not
     * null
     * and is an instance of SharpenFilter class.
     *
     * @param obj The object to compare with the current SharpenFilter object.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        // Check if obj is not null and is an instance of SharpenFilter class
        return obj != null && obj instanceof SharpenFilter;
    }
    /**
     * <p>
     * Constructor for Sharpen Filter
     * </p>
     */
    SharpenFilter() {
    }

    /**
     * <p>
     * Mutator for the kernel array
     * </p>
     * used to set the kernel array to the sharpen filter
     */
    public void setArray() {
        this.array = new float[] { 0, -1 / 2.0f, 0,
                -1 / 2.0f, 3, -1 / 2.0f,
                0, -1 / 2.0f, 0 };
    }

    /**
     * <p>
     * Accessor for the kernel array
     * </p>
     * used in testing @see SharpenFilterTest.java
     * @return float[] representing the kernel array
     */
    public float[] getArray() {
        return array;
    }

    
    /**
     * <p>
     * Apply the Sharpen filter to an image.
     * </p>
     * 
     * @param input the image to apply the filter to
     * @param selected the selected state of the image
     * @return the image with the filter applied
     */
    public BufferedImage apply(BufferedImage input, boolean selected) {
        wasSelected = selected;
        setArray();
        
        // Make a 3x3 filter from the array
        Kernel kernel = new Kernel(3, 3, array);
        // Apply this as a convolution to the input image
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage newInput = FilterIssueHandler.scaleUpImage(input, (kernel.getWidth()-1)/2);
        BufferedImage newOutput = new BufferedImage(newInput.getColorModel(), newInput.copyData(null), newInput.isAlphaPremultiplied(), null);
        convOp.filter(newInput, newOutput);

        return newOutput.getSubimage((kernel.getWidth()-1)/2, (kernel.getWidth()-1)/2, input.getWidth(), input.getHeight());
    }


    
}