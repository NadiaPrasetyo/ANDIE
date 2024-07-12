package cosc202.andie;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.*;

import javax.swing.JOptionPane;
/**
 * <p>
 * ImageOperation to apply a Rotate Image.
 * </p>
 * 
 * <p>
 * This class is used to rotate an image either left or right.
 * </p>
 * @author Ninja-turtles
 */
public class RotateImage implements ImageOperation, java.io.Serializable {
    /* Selected state*/
    boolean wasSelected;

    /**
     * Accessor for the selected state when it was applied
     * @return boolean representing the selected state
     */
    public boolean getSelected() {
        return wasSelected;
    }

    boolean Right;
    /**
     * <p>
     * Constructor for Resize Image
     * </p>
    *  @param leftRight
    */
    RotateImage(boolean leftRight) {
        this.Right = leftRight;
    }

    /**
     * <p>
     * Rotate the image with the Rotate() method
     * </p>
     * @param input
     * @return the result image due to the button pressed
     */
    public BufferedImage Rotate(BufferedImage input) {
        double radian;
        //set the degrees that you would like to rotate the image by
        if(Right){
            radian = Math.toRadians(90);
        }else{
            radian = Math.toRadians(-90);
        }
        //get the side opposite to the angle using the maths class
        double sin = Math.abs(Math.sin(radian));
        //get the angle to the length of the hypotenuse 
        double cos = Math.abs(Math.cos(radian));
        //get the height and width of the image
        int width = input.getWidth();
        int height = input.getHeight();
        //change the new width and height based on the trigonometry calculation below
        int nWidth = (int) Math.floor((double) width * cos + (double) height * sin);
        int nHeight = (int) Math.floor((double) height * cos + (double) width * sin);
        //create a new BufferedImage
        BufferedImage rotatedImage = null;
        try{ 
            //add the new dimentions to the rotatedImage which is a BufferedImage
            rotatedImage = new BufferedImage(nWidth, nHeight, BufferedImage.TYPE_INT_ARGB);
            //create the graphics for this new buffered image so you edit the output display
            Graphics2D graphics = rotatedImage.createGraphics();
            //set the rendering height of the graphics
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            //translate it to the correct size
            graphics.translate((nWidth - width) / 2, (nHeight - height) / 2);
            // rotate the graphics around the center point of the image
            graphics.rotate(radian, (double) (width / 2), (double) (height / 2));
            //draw the image
            graphics.drawImage(input, 0, 0, null);
            //dispose of the graphics to save space as its no longer used
            graphics.dispose();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "e");
        }
        //return the rotated image 
        return rotatedImage;
    }


    /**
     * <p>
     * Apply the rotate image operation to the input image
     * </p>
     * @param input The image to apply the rotate operation to
     * @param selected The selected state of the operation
     * @return The resulting image
     */
    @Override
    public BufferedImage apply(BufferedImage input, boolean selected) {
        //set the selected state
        wasSelected = selected;
        //return the image after the resize method
        return Rotate(input);
    }
}