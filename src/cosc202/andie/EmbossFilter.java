package cosc202.andie;

import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a Emboss Filter.
 * </p>
 * 
 * @author Ninja-turtles
 */ 
public class EmbossFilter implements ImageOperation, java.io.Serializable{
    /**Selection state */
    private boolean wasSelected;

    /**Accessor for selected state */
    public boolean getSelected(){
        return wasSelected;
    }

    //the direction to indicate which kernel to use
    private int direction;

    //Emboss kernels
    private final float[] NORTH_WEST = {1, 0, 0, 0, 0, 0, 0, 0, -1};

    private final float[] NORTH = { 0, 1, 0, 0, 0, 0, 0, -1, 0};

    private final float[] NORTH_EAST = { 0, 0, 1, 0, 0, 0, -1, 0, 0};

    private final float[] SOUTH_WEST = { 0, 0, -1, 0, 0, 0, 1, 0, 0};

    private final float[] SOUTH = { 0, -1, 0, 0, 0, 0, 0, 1, 0};

    private final float[] SOUTH_EAST = { -1, 0, 0, 0, 0, 0, 0, 0, 1};

    private final float[] WEST = {0, 0, 0, 1, 0, -1, 0, 0, 0};

    private final float[] EAST = { 0, 0, 0, -1, 0, 1, 0, 0, 0};

    /**
     * <p>
     * Constructor to set the direction
     * </p>
     * 
     * <p>
     * Sets the direction in which the emboss filter will be applied.(Chooses which kernel is to be used),
     * </p>
     * 
     * @param direction The direction set for the filter
     */
    public EmbossFilter(int direction) {
        this.direction = direction;    
    }

    /**
     * <p>
     * Constructor to set the default int for the direction
     * </p
     * >
     * <p>
     * By default, an Emboss filter points north(1)
     * </p>
     */
    public EmbossFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply an Emboss filter to an image.
     * </p>
     * 
     * <p>
     * The emboss filter enhances the appearance of the edges 
     * of an image. It alters each pixel's value based
     * on its neighboring pixel's brightness or darkness differences.
     * </p>
     * 
     * @param input The image to apply the Emboss filter to.
     * @return The resulting image.
     */
    public BufferedImage apply(BufferedImage input, boolean wasSelected) {
        this.wasSelected = wasSelected;//set the selected state
        
        float[] array;
        
        if(direction == 0){
            array = NORTH_WEST;
        }else if(direction == 1){
            array = NORTH;
        }else if(direction == 2){
            array = NORTH_EAST;
        }else if(direction == 3){
            array = EAST;
        }else if(direction == 4){
            array = SOUTH_EAST;
        }else if(direction == 5){
            array = SOUTH;
        }else if(direction == 6){
            array = SOUTH_WEST;
        }else{
            array = WEST;
        }

        Kernel kernel = new Kernel(3, 3, array);
        BufferedImage newInput = FilterIssueHandler.scaleUpImage(input, 1);
        BufferedImage newOutput = new BufferedImage(newInput.getColorModel(), newInput.copyData(null), newInput.isAlphaPremultiplied(), null);
        FilterIssueHandler.filterNegativeResults(newInput, newOutput, kernel);

        return newOutput.getSubimage(1, 1, input.getWidth(), input.getHeight());


    }    
}
