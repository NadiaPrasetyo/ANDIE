package cosc202.andie;

import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Median filter.
 * </p>
 * 
 * <p>
 * This class is used to apply a Median filter to an image.
 * User is able to select the radius of the filter.
 * </p>
 * @author Ninja-turtles
 */ 
public class MedianFilter implements ImageOperation, java.io.Serializable {
    /* Selected state*/
    boolean wasSelected;

    /**
     *  <p>
     * Accessor for the selected state when it was applied
     * </p>
     * @return boolean representing the selected state
     */
    public boolean getSelected() {
        return wasSelected;
    }

    private int radius;

    /**
     * <p>
     * Construct a Median filter with the given size.
     * </p>
     * 
     * <p>
     *  A median filter blurs an image. It takes all of the pixel values of the local neighborhood,
     * sorts them, and the new pixel value will be the middle value of the local neighborhood(after sorting).
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianFilter
     */
    MedianFilter(int radius){
        this.radius = radius;
    }

    /**
     * Checks whether the current MedianFilter object is equal to another object.
     * Two MedianFilter objects are considered equal if the other object is not null
     * and is an instance of MedianFilter class.
     *
     * @param obj The object to compare with the current MedianFilter object.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        // Check if obj is not null and is an instance of MedianFilter class
        return obj != null && obj instanceof MedianFilter;
    }

    /**
     * 
     * <p>
     * Apply a Median filter to an image.
     * Creates a new image(output) from the input image to have the operations
     * performed on.
     * Uses nested for-loops to iterate through the pixels in the image, and then
     * the local
     * neighborhood for those pixels. Arranges the channels to their correct colors,
     * sorts each
     * color channel, the middle value is then used as the new pixel value to be
     * applied to the
     * output image.
     * </p>
     * 
     * @param input The image to apply the Median filter to.
     * @return The image after applying the filter(output).
     */
    public BufferedImage apply(BufferedImage input, boolean selected){
        wasSelected = selected;
        int size = (2*radius+1) * (2*radius+1);
        
        //create a new BufferedImage

        BufferedImage newInput = FilterIssueHandler.scaleUpImage(input, radius);
        BufferedImage output = new BufferedImage(newInput.getColorModel(), newInput.copyData(null), newInput.isAlphaPremultiplied(), null);

        //Iterating through the pixels in the image and apply the changes to it
        for(int y = 0; y < newInput.getHeight(); y++){
            for(int x = 0; x < newInput.getWidth(); x++){

                //Create new arrays for each channel(argb)
                int[] a = new int[size];
                int[] r = new int[size];
                int[] g = new int[size];
                int[] b = new int[size];

                int index = 0;//index for the elements in each array
                //Iterating through the local neighborhood
                //(also work on the edge issues using try-catch)
                for(int row = -radius; row <= radius; row++){
                    for(int col = -radius; col <= radius; col++){
                            
                        try{
                           //go to the new pixel to work on
                           int newXCoordinate = x + row;// new x coordinate
                           int newYCoordinate = y + col;// new y coordinate

                           //get the argb value of the new pixel
                           int value = input.getRGB(newXCoordinate, newYCoordinate);

                           //assign the argb value to the right channel
                           a[index] = (value & 0xFF000000) >> 24;
                           r[index] = (value & 0x00FF0000) >> 16;
                           g[index] = (value & 0x0000FF00) >> 8;
                           b[index] = (value & 0x000000FF);

                           
                        } catch(IndexOutOfBoundsException e){
                            continue;
                        }
 
                        index++;// update the index
                    }
                }

                //sort each channel
                Arrays.sort(a);
                Arrays.sort(r);
                Arrays.sort(g);
                Arrays.sort(b);
       
                //find the median
                int mi = size/2;
       
                int ov = (a[mi] << 24) | (r[mi] << 16) | (g[mi] << 8) | b[mi];
       
                //set the output image to its new value
                output.setRGB(x, y, ov);
 
            }
        }                  

        return output.getSubimage(radius, radius, input.getWidth(), input.getHeight());
    }
}
