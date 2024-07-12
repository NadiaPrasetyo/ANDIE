package cosc202.andie;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * This class is used to test the DrawShapeOperations class
 * @see DrawShapeOperations
 */
public class DrawShapeOperationsTest {
    /**
     * Test the DrawShapeOperation to create a rectangle
     * @see drawShapeOperation
     */
    @Test
    public void testDrawRectangle() {
        char shape = 'r';
        int x1 = 10;
        int y1 = 10;
        int width = 10;
        int length = 10;
        int thickness = 1;
        java.awt.Color color = java.awt.Color.RED;
        boolean fill = false;
        drawShapeOperation drawShape = new drawShapeOperation(shape, x1, y1, width, length, thickness, color, fill);
        
        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

        BufferedImage output = drawShape.apply(image, false);

        //Check that the image has been modified (pixel on the corner left of the rectangle)
        Assertions.assertEquals(output.getRGB(10, 10), color.getRGB());
        //check the pixel in the centre of the rectangle has not been changed
        Assertions.assertNotEquals(output.getRGB(15, 15), color.getRGB());

        //Change teh color to blue
        color = java.awt.Color.BLUE;

        //Check that the color has changed
        Assertions.assertNotEquals(image, output);
        Assertions.assertNotEquals(output.getRGB(10, 10), color.getRGB());

    }

    /**
     * Test the DrawShapeOperation to create a filled circle
     * @see drawShapeOperation
     * @see DrawShapeOperations
     */
    @Test
    public void testFillCircle() {
        char shape = 'c';
        int x1 = 10;
        int y1 = 10;
        int width = 10;
        int length = 10;
        int thickness = 5;
        java.awt.Color color = java.awt.Color.RED;
        boolean fill = true;
        drawShapeOperation drawShape = new drawShapeOperation(shape, x1, y1, width, length, thickness, color, fill);
        
        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

        BufferedImage output = drawShape.apply(image, false);

        //Check that the image has been modified (check centre of circle pixel)
        Assertions.assertEquals(output.getRGB(15, 15), color.getRGB());
        //check the pixel on the corner of the circle has not been changed
        Assertions.assertNotEquals(output.getRGB(10, 10), color.getRGB());

        //Change teh color to blue
        color = java.awt.Color.BLUE;

        Assertions.assertNotEquals(output.getRGB(10, 10), color.getRGB());
        
    }

    /**
     * Test the DrawShapeOperation to create a freehand line drawing
     */
    @Test
    public void testDrawFreehand(){
        //set the points
        int thickness = 5;
        java.awt.Color color = java.awt.Color.RED;
        boolean fill = false;
        java.awt.Point point1 = new java.awt.Point(10, 10);
        java.awt.Point point2 = new java.awt.Point(15, 15);
        java.awt.Point point3 = new java.awt.Point(20, 20);
        java.util.ArrayList<java.awt.Point> points = new java.util.ArrayList<java.awt.Point>();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        //initialize the drawShapeOperation object
        drawShapeOperation drawShape = new drawShapeOperation(points, thickness, color, fill);
        
        BufferedImage image = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);

        BufferedImage output = drawShape.apply(image, false);

        //Check that the image has been modified (check the pixels on the line)
        Assertions.assertEquals(output.getRGB(10, 10), color.getRGB());
        Assertions.assertEquals(output.getRGB(15, 15), color.getRGB());
        Assertions.assertEquals(output.getRGB(20, 20), color.getRGB());

        //Check that the pixel in between the points have been painted as well
        Assertions.assertEquals(output.getRGB(12, 12), color.getRGB());
        Assertions.assertEquals(output.getRGB(17, 17), color.getRGB());

        //check the pixel not on the line has not been changed
        Assertions.assertNotEquals(output.getRGB(10, 15), color.getRGB());
        Assertions.assertNotEquals(output.getRGB(25, 25), color.getRGB());

        //Change teh color to blue
        color = java.awt.Color.BLUE;

        Assertions.assertNotEquals(output.getRGB(10, 10), color.getRGB());
    }
    
}
