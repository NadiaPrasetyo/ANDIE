package cosc202.andie;


import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class is used to test the crop operation
 * 
 * <p>
 * Author: Ninja-turtles
 * </p>
 * 
 */
public class CropTest {

    /**
     * Test the crop operation
     * @see CropOperation
     */
    @Test
    public void testCrop(){
        Rectangle rect = new Rectangle(100, 100, 100, 50);
        CropOperation crop = new CropOperation(rect);
        BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(100, 100, 0xFF00FF00);//green
        image.setRGB(120, 130, 0xFFFF0000);//red

        BufferedImage actual = image.getSubimage(100, 100, 100, 50);
        
        BufferedImage cropped = crop.apply(image, false);

        Assertions.assertEquals(cropped.getWidth(), actual.getWidth());//check width
        Assertions.assertEquals(cropped.getHeight(), actual.getHeight());//check height
        Assertions.assertEquals(cropped.getType(), actual.getType());//check type
        //check pixel values
        Assertions.assertEquals(cropped.getRGB(0, 0), actual.getRGB(0, 0));
        Assertions.assertNotEquals(cropped.getRGB(20, 30), actual.getRGB(0, 0));
        Assertions.assertNotEquals(cropped.getRGB(0, 0), actual.getRGB(20, 30));
        Assertions.assertEquals(cropped.getRGB(20, 30), actual.getRGB(20, 30));

    }

    
    
}
