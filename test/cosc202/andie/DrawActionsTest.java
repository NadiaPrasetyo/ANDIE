package cosc202.andie;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import cosc202.andie.EditActions.DrawActions;

/**
 * This class is used to test the DrawActions class
 * @see EditActions.DrawActions
 */
public class DrawActionsTest {
    
    /**
     * Test the DrawActions constructor
     * @throws IOException
     */
    @Test
    public void testDrawMouseListener() throws IOException{
        EditActions edit = new EditActions();
        EditActions.DrawActions draw = edit.new DrawActions("Draw", null, "Draw", Integer.valueOf(KeyEvent.VK_D), Integer.valueOf(KeyEvent.VK_D), Integer.valueOf(ActionEvent.CTRL_MASK));
        
        //Adding target to allow testing of DrawActions
        ImagePanel target = new ImagePanel();
        target.getImage().open("src/ninjaturtles.png");
        BufferedImage image = target.getImage().getCurrentImage();
        DrawActions.setTarget(target);

        //Test that the target and image has been set
        Assertions.assertEquals(image, target.getImage().getCurrentImage());


        target.addMouseListener(draw);
        //Trigger a mouse press event
        draw.mousePressed(new java.awt.event.MouseEvent(target, 0, 0, 0, 0, 0, 0, false));
        
        int x1 = 0;
        int y1 = 0;
        
        //Test that the x1 and y1 coordinates have been set
        Assertions.assertEquals(x1, draw.getX1());
        Assertions.assertEquals(y1, draw.getY1());
      
        draw.mousePressed(new java.awt.event.MouseEvent(target, 0, 0, 0, 5, 10, 0, false));

        y1 = 10;
        Assertions.assertNotEquals(x1, draw.getX1());
        Assertions.assertEquals(y1, draw.getY1());

    }

    /**
     * Test the freehand points
     * @throws IOException
     */
    @Test
    public void testFreehandPoints() throws IOException{
        EditActions edit = new EditActions();
        EditActions.DrawActions draw = edit.new DrawActions("Draw", null, "Draw", Integer.valueOf(KeyEvent.VK_D), Integer.valueOf(KeyEvent.VK_D), Integer.valueOf(ActionEvent.CTRL_MASK));
        
        //Adding target to allow testing of DrawActions
        ImagePanel target = new ImagePanel();
        target.getImage().open("src/ninjaturtles.png");
        BufferedImage image = target.getImage().getCurrentImage();
        DrawActions.setTarget(target);

        //Test that the target and image has been set
        Assertions.assertEquals(image, target.getImage().getCurrentImage());

        draw.setShape('f');
        target.addMouseListener(draw);
        target.addMouseMotionListener(draw);

        //Trigger a mouse press event
        draw.mousePressed(new java.awt.event.MouseEvent(target, 0, 0, 0, 0, 0, 0, false));
        //trigger a couple of mouse dragged events
        draw.mouseDragged(new java.awt.event.MouseEvent(target, 0, 0, 0, 5, 10, 0, false));
        draw.mouseDragged(new java.awt.event.MouseEvent(target, 0, 0, 0, 10, 15, 0, false));
        draw.mouseDragged(new java.awt.event.MouseEvent(target, 0, 0, 0, 15, 20, 0, false));
        
        ArrayList<Point> points = draw.getPoints();

        ArrayList<Point> expected = new ArrayList<Point>();
        expected.add(new Point(0, 0));
        expected.add(new Point(5, 10));
        expected.add(new Point(10, 15));
        expected.add(new Point(15, 20));

        //Test that the points are at the correct places
        Assertions.assertEquals(points, expected);

    }

}
