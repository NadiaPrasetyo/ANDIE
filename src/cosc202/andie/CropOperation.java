package cosc202.andie;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;


    /**
     * <p>
     * This class is used to crop the image to the selected area
     * </p>
     * 
     * @author Ninja-turtles 
    */
    public class CropOperation implements ImageOperation, Serializable{
        /* Selected state*/
    boolean wasSelected;

    /* Selected area */
    Rectangle selectedArea;

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
     * Construct a crop operation
     * </p>
     * @param selectedArea
     */
    public CropOperation(Rectangle selectedArea) {
        this.selectedArea = selectedArea;
    }

    /**
     * <p>
     * Crops the image to the selected area
     * </p>
     * @param input
     * @param selected - crop will never be applied when selected is false
     * @return BufferedImage cropped to the selected area
     */
    @Override
    public BufferedImage apply(BufferedImage input, boolean selected) {
        wasSelected = selected;
        BufferedImage cropped = input.getSubimage(selectedArea.x, selectedArea.y, selectedArea.width, selectedArea.height);
        return cropped;
        
    }
}