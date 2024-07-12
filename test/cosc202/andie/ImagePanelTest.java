package cosc202.andie;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ImagePanelTest {
    @Test
    void initialDummyTest(){
    }

    @Test
    void getZoomInitialValue(){
        try{
        ImagePanel testPanel = new ImagePanel();
        Assertions.assertEquals(100.0, testPanel.getZoom());
        } catch(Exception e){
            System.out.println("Error: " + e);
        }

    }

    @Test
    void getZoomAftersetZoom(){
        try{
        ImagePanel testPanel = new ImagePanel();
        testPanel.setZoom(0.0);
        Assertions.assertFalse(testPanel.getZoom() == 100.0);
        Assertions.assertTrue(testPanel.getZoom() <= 50.0);
        }catch(Exception e){
            System.out.println("Error: " + e);
        }

    }

}


