package cosc202.andie;

import java.awt.Graphics2D;
import java.awt.image.*;


/**
 * <p>
 * Provides methods that address issues filters encounter during 
 * image processing.
 * <p>
 * 
 * <p>
 * This class contains methods to deal with issues that we may encounter
 * when applying filters to images.
 * </p>
 * 
 * @author Ninja-turtles
 * @version 1.0
 */
public class FilterIssueHandler {

    /**
     * <p>
     *  An alternative convolution filter method to cater to filters with negative values.
     * <p>
     * 
     * <p>
     *  This method applies a cuteom-made convolution filter to the input BufferedImage, 
     *  catering to the negative values by offsetting each pixel by 127. 
     *  This is done so that values below 127 will be darker and those above will be brighter.
     * <p>
     * 
     * @param input The input BufferedImage.
     * @param output The image to set the changes on and return as the result.
     * @param kernel the kernel used to go through each pixels.
     * @return the filtered BufferedImage(output)
     */
    public static BufferedImage filterNegativeResults(BufferedImage input, BufferedImage output, Kernel kernel){
        int radius = (kernel.getWidth()-1)/2;
        float[] kernelValues = new float[kernel.getWidth() * kernel.getHeight()];
        kernel.getKernelData(kernelValues);

       
         for (int y = 0; y < input.getHeight(); y++) {
             for (int x = 0; x < input.getWidth(); x++) {
                   float[] channels = {127, 127, 127, 127};
                 int index = 0;
                 for (int dy = -radius; dy <= radius; dy++) {
                     for (int dx = -radius; dx <= radius; dx++) {

                        int newXCoordinates = Math.min(Math.max(0, x + dx), input.getWidth() - 1);
                         int newYCoordinates = Math.min(Math.max(0, y + dy), input.getHeight() - 1);

                         int value = input.getRGB(newXCoordinates, newYCoordinates);
                         float k = kernelValues[index];
                         
                         channels[0] += ((value & 0xFF000000) >> 24) * k;
                         channels[1] += ((value & 0x00FF0000) >> 16) * k;
                         channels[2] += ((value & 0x0000FF00) >> 8) * k;
                         channels[3] += ((value & 0x000000FF)) * k;

                         index++;
                     }
                 }
                 int aSum = (Math.min(Math.max(0, (int)channels[0]), 255));
                 int rSum = (Math.min(Math.max(0, (int)channels[1]), 255));
                 int gSum = (Math.min(Math.max(0, (int)channels[2]), 255));
                 int bSum = (Math.min(Math.max(0, (int)channels[3]), 255));

                 int ov = (aSum << 24) | (rSum << 16) | (gSum << 8) | bSum;
                 output.setRGB(x, y, ov);
             }
         }               

        return output;
    
    }



    /**
     * 
     * <p>
     * A method to scale up an image, populating its borders with copies of the
     * image.
     * <p>
     * 
     * 
     * <p>
     * This method creates a new BufferedImage with dimensions sized up by twice the given radius.
     * The input image is placed at the centre of the scaled-image, and copies of the input image is
     * placed at the borders of the scaled-image.
     * <p>
     * 
     * @param input BufferedImage to scale up
     * @param radius the amount to scale the image up by.
     * @return a bufferedImage containing the scaledImage.
     */
    public static BufferedImage scaleUpImage(BufferedImage input , int radius) {

        //calculate the dimensions of the scaled image
        int newWidth = input.getWidth() + (2 * radius);
        int newHeight = input.getHeight() + (2 * radius);

        //create the scaled image with the calculated dimensions 
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, input.getType());

        //create graphics object to dfraw on the scaledImage
        Graphics2D g = scaledImage.createGraphics();

        //create images from the borders of the original image
        BufferedImage topRow = topRow(input, radius);
        BufferedImage bottomRow = bottomRow(input, radius);
        BufferedImage leftColumn = leftColumn(input, radius);
        BufferedImage rightColumn = rightColumn(input, radius);
        BufferedImage topLeftCorner = topLeftCorner(input, radius);
        BufferedImage topRightCorner = topRightCorner(input, radius);
        BufferedImage bottomLeftCorner = bottomLeftCorner(input, radius);
        BufferedImage bottomRightCorner = bottomRightCorner(input, radius);

        //draws each image around the borders of the scaled-image
        g.drawImage(topLeftCorner, 0, 0, topLeftCorner.getWidth(), topLeftCorner.getHeight(), null);
        g.drawImage(topRow, radius, 0, topRow.getWidth(), topRow.getHeight(), null);
        g.drawImage(topRightCorner, radius + input.getWidth(), 0, topRightCorner.getWidth(), topRightCorner.getHeight(), null);

        g.drawImage(leftColumn, 0, radius, leftColumn.getWidth(), leftColumn.getHeight(), null);
        g.drawImage(input, radius, radius, input.getWidth(), input.getHeight(), null);
        g.drawImage(rightColumn, radius + input.getWidth() , radius, rightColumn.getWidth(), rightColumn.getHeight(), null);

        g.drawImage(bottomLeftCorner, 0, radius + input.getHeight(), bottomLeftCorner.getWidth(), bottomLeftCorner.getHeight(), null);
        g.drawImage(bottomRow, radius, radius + input.getHeight(), bottomRow.getWidth(), bottomRow.getHeight(), null);
        g.drawImage(bottomRightCorner, radius + input.getWidth(), radius + input.getHeight(), bottomRightCorner.getWidth(), bottomRightCorner.getHeight(), null);

        g.dispose();

        return scaledImage;
    }
    
    /**
     * <p>
     * Extracts the top row of pixels of the input image and creates a new image with that row repeated 
     * by the amount of radius.
     * <p>
     * 
     * <p>
     * This method gets the top row of pixels of the input image. Creates a new bufferedImage(output), with a width 
     * the same as the input's width and the height determined by the radius number. Uses a loop to fill the output 
     * image with pixels from the extracted top row, repeated radius amount of times.
     * <p>
     * @param input The BufferedImage to extract the top row from.
     * @param radius Determines the height of the output BufferedImage.
     * @return A BufferedImage containing copies of the top row of the input.
     */
    public static BufferedImage topRow(BufferedImage input, int radius) {
        
        //get the top row pixels of the input
        BufferedImage section = input.getSubimage(0, 0, input.getWidth(), 1);

        //create the output BufferedImage
        BufferedImage output = new BufferedImage(input.getWidth(), radius, BufferedImage.TYPE_INT_ARGB);

        //create graphics object to draw on the output BufferedImage
        Graphics2D g = output.createGraphics();

        //Loop to draw the top row on the output radius amount of times
        for(int i = 0; i < radius; i++){
            g.drawImage(section, 0, i, section.getWidth(), section.getHeight(), null);    
        }

        g.dispose();

        return output;
    }

    /**
     * <p>
     * Extracts the bottom row of pixels of the input image and creates a new image with that row repeated 
     * by the amount of radius.
     * <p>
     * 
     * <p>
     * This method gets the bottom row of pixels of the input image. Creates a new bufferedImage(output), with a width 
     * the same as the input's width and the height determined by the radius number. Uses a loop to fill the output 
     * image with pixels from the extracted bottom row, repeated radius amount of times.
     * <p>
     * @param input The BufferedImage to extract the bottom row from.
     * @param radius Determines the height of the output BufferedImage.
     * @return A BufferedImage containing copies of the bottom row of the input.
     */    
    private static BufferedImage bottomRow(BufferedImage input, int radius) {
       
        //gets the bottom row pixels of the input
        BufferedImage section = input.getSubimage(0, input.getHeight()-1, input.getWidth(), 1);

        //create the output BufferedImage
        BufferedImage output = new BufferedImage(input.getWidth(), radius, BufferedImage.TYPE_INT_ARGB);

        //create graphics object to draw on the output BufferedImage
        Graphics2D g = output.createGraphics();

        //loop to draw the pixels in the output radius amount of times
        for(int i = 0; i < radius; i++){
            g.drawImage(section, 0, i, section.getWidth(), section.getHeight(), null);    
        }

        g.dispose();
        return output;
    }    

    /**
     * <p>
     * Extracts the left column of pixels of the input image and creates a new image with that column repeated 
     * by the amount of radius.
     * <p>
     * 
     * <p>
     * This method gets the left column of pixels of the input image. Creates a new bufferedImage(output), with a width 
     * determined by the radius and a height that is the same as the height of the input image. Uses a loop to fill the output 
     * image with pixels from the extracted left column, repeated radius amount of times.
     * <p>
     * @param input The BufferedImage to extract the left column from.
     * @param radius Determines the width of the output BufferedImage.
     * @return A BufferedImage containing copies of the left column of the input.
     */     
    private static BufferedImage leftColumn(BufferedImage input, int radius) {

        //gets the left column pixels of the input
        BufferedImage section = input.getSubimage(0, 0, 1, input.getHeight());

        //create the output BufferedImage
        BufferedImage output = new BufferedImage(radius, input.getHeight(), BufferedImage.TYPE_INT_ARGB);

        //create graphics object to draw on the output BufferedImage
        Graphics2D g = output.createGraphics();

        //loop to draw the pixels in the output radius amount of times
        for(int i = 0; i < radius; i++){
            g.drawImage(section, i, 0, section.getWidth(), section.getHeight(), null);    
        }

        g.dispose();
        return output;
    }  

  /**
     * <p>
     * Extracts the right column of pixels of the input image and creates a new image with that column repeated 
     * by the amount of radius.
     * <p>
     * 
     * <p>
     * This method gets the right column of pixels of the input image. Creates a new bufferedImage(output), with a width 
     * determined by the radius and a height that is the same as the height of the input image. Uses a loop to fill the output 
     * image with pixels from the extracted right column, repeated radius amount of times.
     * <p>
     * @param input The BufferedImage to extract the right column from.
     * @param radius Determines the width of the output BufferedImage.
     * @return A BufferedImage containing copies of the right column of the input.
     */        
    private static BufferedImage rightColumn(BufferedImage input, int radius) {
 
        //gets the right column pixels of the input        
        BufferedImage section = input.getSubimage(input.getWidth()-1, 0, 1, input.getHeight());

        //create the output BufferedImage
        BufferedImage output = new BufferedImage(radius, input.getHeight(), BufferedImage.TYPE_INT_ARGB);

        //create graphics object to draw on the output BufferedImage        
        Graphics2D g = output.createGraphics();

        //loop to draw the pixels in the output radius amount of times
        for(int i = 0; i < radius; i++){
            g.drawImage(section, i, 0, section.getWidth(), section.getHeight(), null);    
        }

        g.dispose();
        return output;
    }

    /**
     * <p>
     * Extracts the top left corner pixel of the input image and creates a new image with that pixel repeated 
     * by the amount of radius.
     * <p>
     * 
     * <p>
     * This method gets the top left corner pixel of the input image. Creates a new bufferedImage(output), with a width and height
     * determined by the radius. Uses a loop to fill the output 
     * image with pixels from the extracted top left corner, repeated radius amount of times.
     * <p>
     * @param input The BufferedImage to extract the top left corner pixel from.
     * @param radius Determines the height and width of the output BufferedImage.
     * @return A BufferedImage containing copies of the top left corner of the input.
     */
    private static BufferedImage topLeftCorner(BufferedImage input, int radius) {

        //gets the top left corner pixel of the input
        BufferedImage section = input.getSubimage(0, 0, 1, 1);

        //create the output BufferedImage
        BufferedImage output = new BufferedImage(radius, radius, BufferedImage.TYPE_INT_ARGB);

        //create graphics object to draw on the output BufferedImage
        Graphics2D g = output.createGraphics();

        //loop to draw the pixels in the output radius amount of times
        for(int y = 0; y < radius; y++){
            for(int x = 0; x < radius; x++){
                g.drawImage(section, x, y, section.getWidth(), section.getHeight(), null);    
            }
        }

        g.dispose();
        return output;       
    }

    /**
     * <p>
     * Extracts the top right corner pixel of the input image and creates a new image with that pixel repeated 
     * by the amount of radius.
     * <p>
     * 
     * <p>
     * This method gets the top right corner pixel of the input image. Creates a new bufferedImage(output), with a width and height
     * determined by the radius. Uses a loop to fill the output 
     * image with pixels from the extracted top right corner, repeated radius amount of times.
     * <p>
     * @param input The BufferedImage to extract the top right corner pixel from.
     * @param radius Determines the height and width of the output BufferedImage.
     * @return A BufferedImage containing copies of the top right corner of the input.
     */    
    private static BufferedImage topRightCorner(BufferedImage input, int radius) {

        //gets the top right corner pixel of the input        
        BufferedImage section = input.getSubimage(input.getWidth()-1, 0, 1, 1);

        //create the output BufferedImage
        BufferedImage output = new BufferedImage(radius, radius, BufferedImage.TYPE_INT_ARGB);

        //create graphics object to draw on the output BufferedImage
        Graphics2D g = output.createGraphics();

        //loop to draw the pixels in the output radius amount of times
        for(int y = 0; y < radius; y++){
            for(int x = 0; x < radius; x++){
                g.drawImage(section, x, y, section.getWidth(), section.getHeight(), null);    
            }
        }

        g.dispose();
        return output;       
    }

    /**
     * <p>
     * Extracts the bottom left corner pixel of the input image and creates a new image with that pixel repeated 
     * by the amount of radius.
     * <p>
     * 
     * <p>
     * This method gets the bottom left corner pixel of the input image. Creates a new bufferedImage(output), with a width and height
     * determined by the radius. Uses a loop to fill the output 
     * image with pixels from the extracted bottom left corner, repeated radius amount of times.
     * <p>
     * @param input The BufferedImage to extract the bottom left corner pixel from.
     * @param radius Determines the height and width of the output BufferedImage.
     * @return A BufferedImage containing copies of the bottom left corner of the input.
     */    
    private static BufferedImage bottomLeftCorner(BufferedImage input, int radius) {
 
        //gets the bottom corner pixel of the input        
        BufferedImage section = input.getSubimage(0, input.getHeight()-1, 1, 1);

        //create the output BufferedImage
        BufferedImage output = new BufferedImage(radius, radius, BufferedImage.TYPE_INT_ARGB);

        //create graphics object to draw on the output BufferedImage
        Graphics2D g = output.createGraphics();

        //loop to draw the pixels in the output radius amount of times
        for(int y = 0; y < radius; y++){
            for(int x = 0; x < radius; x++){
                g.drawImage(section, x, y, section.getWidth(), section.getHeight(), null);    
            }
        }

        g.dispose();
        return output;       
    }   
    
     /**
     * <p>
     * Extracts the bottom right corner pixel of the input image and creates a new image with that pixel repeated 
     * by the amount of radius.
     * <p>
     * 
     * <p>
     * This method gets the bottom right corner pixel of the input image. Creates a new bufferedImage(output), with a width and height
     * determined by the radius. Uses a loop to fill the output 
     * image with pixels from the extracted bottom right corner, repeated radius amount of times.
     * <p>
     * @param input The BufferedImage to extract the bottom right corner pixel from.
     * @param radius Determines the height and width\ of the output BufferedImage.
     * @return A BufferedImage containing copies of the bottom right corner of the input.
     */   
    private static BufferedImage bottomRightCorner(BufferedImage input, int radius) {

        //gets the bottom right corner pixel of the input        
        BufferedImage section = input.getSubimage(input.getWidth()-1, input.getHeight()-1, 1, 1);

        //create the output BufferedImage
        BufferedImage output = new BufferedImage(radius, radius, BufferedImage.TYPE_INT_ARGB);

        //create graphics object to draw on the output BufferedImage
        Graphics2D g = output.createGraphics();

        //loop to draw the pixels in the output radius amount of times 
        for(int y = 0; y < radius; y++){
            for(int x = 0; x < radius; x++){
                g.drawImage(section, x, y, section.getWidth(), section.getHeight(), null);    
            }
        }

        g.dispose();
        return output;       
    }




    
}
