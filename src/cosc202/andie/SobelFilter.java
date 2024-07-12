package cosc202.andie;

import java.awt.image.BufferedImage;
import java.awt.image.Kernel;

public class SobelFilter implements  ImageOperation, java.io.Serializable{

    private int direction;

    private final float[] HORIZONTAL = {-1/2, 0, 1/2, -1, 0, 1, -1/2, 0, 1/2};
    private final float[] VERTICAL = {-1/2, -1, -1/2, 0, 0, 0, 1/2, 1, 1/2};

    /**Selection state */
    private boolean wasSelected;

    /**Accessor for selected state */
    public boolean getSelected(){
        return wasSelected;
    }

    /**
     * <p>
     * Constructor to set the direction
     * </p>
     * 
     * <p>
     * Sets the direction in which the sobel filter will be applied.(Chooses which kernel is to be used),
     * </p>
     * 
     * @param direction The direction set for the filter
     */
    public SobelFilter(int direction) {
        this.direction = direction;    
    }

    /**
     * <p>
     * Constructor to set the default int for the direction
     * </p
     * >
     * <p>
     * By default, an Sobel filter points HORIZONTAL(0)
     * </p>
     */
    public SobelFilter() {
        this(0);
    }

       /**
     * <p>
     * Apply an Sobel filter to an image.
     * </p>
     * 
     * <p>
     * The sobel filter enhances the appearance of the edges 
     * of an image. It alters each pixel's value based
     * on its neighboring pixel's brightness or darkness differences.
     * </p>
     * 
     * @param input The image to apply the Sobel filter to.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input, boolean selected) {
        this.wasSelected = selected;

        float[] array;
        
        if(direction == 0){
            array = HORIZONTAL;
        }else{
            array = VERTICAL;
        }
        
        Kernel kernel = new Kernel(3, 3, array);
        BufferedImage newInput = FilterIssueHandler.scaleUpImage(input, 1);
        BufferedImage newOutput = new BufferedImage(newInput.getColorModel(), newInput.copyData(null), newInput.isAlphaPremultiplied(), null);
        FilterIssueHandler.filterNegativeResults(newInput, newOutput, kernel);

        return newOutput.getSubimage(1, 1, input.getWidth(), input.getHeight());


    }

    
}
