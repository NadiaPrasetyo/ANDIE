package cosc202.andie;

import java.awt.image.*;
import java.text.DecimalFormat;

/**
 * <p>
 * ImageOperation to apply a Gaussian Blur.
 * </p>
 * 
 * <p>
 * This class is used to apply a Gaussian blur to an image.
 * User is able to select the radius of the blur.
 * </p>
 * @author Ninja-turtles
 */
public class GaussianBlur implements ImageOperation, java.io.Serializable {
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
    
    
    private float sigma;
    private int radius;
    private float[] array;

    /**
     * <p>
     * Construct a Gaussian blur operation with a given radius.
     * Calculate the sigma value for the Gaussian function.
     * </p>
     * @param radius the radius of the blur.
     */
    GaussianBlur(int radius)  {
        this.radius = radius;
        this.sigma = (radius/3.0f);
        
        
    }
    
    @Override
    public boolean equals(Object obj) {
        // Check if obj is not null and is an instance of GaussianBlur class
        return obj != null && obj instanceof GaussianBlur;
    }

    /**
     * <p>
     * Construct a Gaussian blur operation with the default size.
     * By default, a Gaussian blur operation has radius of 1.
     * </p>
     * @see GaussianBlur(int)
     * 
     */
    GaussianBlur() {
        this(1);

    }   

    /**
     * <p>
     * Apply the Gaussian blur operation to the input image.
     * </p>
     * @param input the input image.
     * @return the output image.
     */
    public BufferedImage apply(BufferedImage input, boolean selected) {
        wasSelected = selected;
        //calculte the kernel
        getKernel();  

        // Apply kernel as a convolution
        //Create a ConvolveOp with Edge_NO_OP to prevent edge issue
        Kernel kernel = new Kernel(2*radius+1, 2*radius+1, array);
        ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage newInput = FilterIssueHandler.scaleUpImage(input, radius);
        BufferedImage newOutput = new BufferedImage(newInput.getColorModel(), newInput.copyData(null), newInput.isAlphaPremultiplied(), null);
        convOp.filter(newInput, newOutput);

        return newOutput.getSubimage(radius, radius, input.getWidth(), input.getHeight());
    }

    /**
     * <p>
     * Calculate the Gaussian function for a given x and y.
     * </p>
     * @param x the x value.
     * @param y the y value.
     * @return the Gaussian function value.
     */
    private float gaussian(int x, int y) {
        //Gaussian function
        float G = (float) ((1 / (2 * Math.PI * Math.pow(sigma, 2))) * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(sigma, 2))));
        return G;
        
    }

    /**
     * <p>
     * Format a float to 3 decimal places.
     * </p>
     * @param value the float value to format.
     * @return the float value with 3 decimal places.
     */
    public static float withThreeDecimalPlaces(float value) {
        DecimalFormat df = new DecimalFormat("#.000");
        return Float.valueOf(df.format(value));
    }

    /**
     * <p>
     * calculate the Kernel for the Gaussian blur.
     * </p>
     * used in testing @see GaussianBlurTest.java
     * @return array of the Kernel
     */
    public float[] getKernel() {
        // The values for the kernel
        int size =  (2*radius+1) *  (2*radius+1);
        array = new float[size];
        
        // Adding the total of all the entries
        float total = 0;
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                //System.out.println("i: " + i + " j: " + j + " gaussian: " + gaussian(i, j));
                total += gaussian(i, j);
                
            }
        }
        //System.out.println("total: " + total);
        

        // Normalizing the kernel
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                //System.out.println("i: " + i + " j: " + j );
                array[(i+radius)*(2*radius+1) + (j+radius)] = withThreeDecimalPlaces(gaussian(i, j) / total);
                //System.out.println("array: " + array[(i+radius)*(2*radius+1) + (j+radius)]);
                
            }
        }
        
        return array;
    }

    /**
     * <p>
     * Get the array of the kernel.
     * </p>
     * @return the array of the kernel.
     */
    public float[] getArray() {
        return array;
    }

}
