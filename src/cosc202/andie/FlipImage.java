package cosc202.andie;
import java.awt.Graphics2D;
import java.awt.image.*;


/**
 * <p>
 * ImageOperation to apply a Flip Image.
 * </p>
 * 
 * <p>
 * This class is used to flip an image either horizontally or vertically.
 * </p>
 * @author Ninja-turtles
 */
public class FlipImage implements ImageOperation, java.io.Serializable{
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

    boolean horizontal;
    /**
     * <p>
     * Constructor to set the flip direction
     * </p>
     * @param flip
     */
    public FlipImage(boolean flip){
        this.horizontal = flip;
    }
    
    /**
     * <p>
     * Method to flip the image
     * </p>
     * 
     * @param input
     * @param h
     * @return BufferedImage representing the flipped image
     */
    public static BufferedImage flip(BufferedImage input, boolean h){
        //get the width and the height of the input BufferedImage
        int width = input.getWidth();
        int height = input.getHeight();
        //intansiate a BufferedImage
        BufferedImage result = null;
        //try catch
        try {
        //create the flipped horizontal image with reversed x coordinates
        result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //create the graphics 
        Graphics2D g2d = result.createGraphics();
        //draw the image 
        //check if the image is being flipped horizontal or vertical
        if (h){
            g2d.drawImage(input, width, 0, 0, height, 0, 0, width, height, null);
        }
        else{
            g2d.drawImage(input, 0, height, width, 0, 0, 0, width, height, null);
        }
        //dispose of the graphics
        g2d.dispose();
        }catch(Exception ex){
            System.out.println(ex);

        }
        //return the new  BufferedImage
        return result;
    } 

        /**
         * <p>
         * Apply the flip image operation to the input image
         * </p>
         * @param input The image to apply the flip operation to
         * @param selected The selected state of the operation
         * @return The resulting image
         */
        @Override
        public BufferedImage apply(BufferedImage input, boolean selected) {
            //set the selected state
            wasSelected = selected;
            //return the image after the resize method
            return flip(input, horizontal);
        }


}
