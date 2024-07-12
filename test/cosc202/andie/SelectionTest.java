package cosc202.andie;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SelectionTest {

    /**
     * Test the default selection state
     * @see Select
     */
    @Test
    public void testSelectionDefault(){
        try{
        ImagePanel imagePanel = new ImagePanel();
        Select select = new Select(imagePanel);

        //Checks that the before selected image is the same as the current image (no selections made yet)
        Assertions.assertEquals(select.getBeforeSelected(), imagePanel.getImage().getCurrentImage());
        
        //Checks that the cursor of the imagePanel is successfully set to CROSSHAIR_CURSOR
        Assertions.assertEquals(imagePanel.getCursor().getType(), java.awt.Cursor.CROSSHAIR_CURSOR);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test the selection area creation
     * @see Select
     */
    @Test
    public void testSelectionAreaCreation() {
        try{
            ImagePanel imagePanel = new ImagePanel();
            Select select = new Select(imagePanel);
            select.mousePressed(new MouseEvent(imagePanel, 0, 0, 0, 100, 100, 0, false));
            select.mouseReleased(new MouseEvent(imagePanel, 0, 0, 0, 200, 150, 0, false));

            Rectangle actual = new Rectangle(100, 100, 100, 50);
            
            //Check that the selected area is created and is the same as the actual rectangle
            Assertions.assertEquals(select.getSelectedArea(), actual);
    
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Test
    public void testDeSelect(){
        try{
            ImagePanel imagePanel = new ImagePanel();
            Select select = new Select(imagePanel);

            Rectangle rect = new Rectangle(100, 100, 100, 50);
            select.setSelectedArea(rect);
            
            Assertions.assertEquals(select.getSelectedArea(), rect);

            select.deSelect();
            
            Assertions.assertEquals(select.getSelectedArea(), null);
    
            } catch (Exception e) {
                e.printStackTrace();
            }
    }




}
