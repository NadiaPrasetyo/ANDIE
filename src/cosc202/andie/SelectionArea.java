package cosc202.andie;
import java.awt.Rectangle;
import java.io.Serializable;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to apply a Selection Area.
 * </p>
 * 
 * <p>
 * This class is used to apply a selection area to an image.
 * </p>
 * @author Ninja-turtles
 */
public class SelectionArea implements ImageOperation, Serializable{
    /**selection state */
    private Rectangle selectionArea;
    private boolean wasSelected;

    public SelectionArea(Rectangle selectionArea){
        this.selectionArea = selectionArea;
    }

    /**
     * <p>
     * Accessor for the selected state when it was applied
     * </p>
     * @return boolean representing the selected state
     */
    public boolean getSelected(){
        return wasSelected;
    }

    /**
     * Accessor for the selected area
     * @return Rectangle representing the selected area
     */
    public Rectangle getSelectionArea(){
        return selectionArea;
    }

    /**
     * <p>
     * Applies the selection area and relays it to the image
     * This method seemingly adds an empty operation to the image
     * </p>
     * 
     * @param input The image to apply the selection area to
     * @param wasSelected The selected state of the image
     * @return The image with the selection area applied
     */
    @Override
    public BufferedImage apply(BufferedImage input, boolean wasSelected){
        this.wasSelected = false;        
        return input;
    }
        
    


}