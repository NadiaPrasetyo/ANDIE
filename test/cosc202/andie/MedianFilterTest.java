package cosc202.andie;

import java.awt.image.*;
import java.awt.Color;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MedianFilterTest {
    
    @Test
    public void testEdgeHandling() {
        MedianFilter mf = new MedianFilter(1);
        
        BufferedImage input = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);

        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){
                int c = 0;

                if(x == 0 || y == 0 || x == 2 || y == 2){
                    c = new Color(0,0,0).getRGB();
                }else{
                    c = new Color(255,255,255).getRGB();
                }

                input.setRGB(x, y, c);
            }
        }

        BufferedImage output = mf.apply(input, true);
        
         Assertions.assertNotNull(output);

         for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){

                if(x == 0 || y == 0 || x == 2 || y == 2){
                   
                    int outputC = output.getRGB(x, y);

                    int r = (outputC & 0x00FF0000) >> 16;
                    int g = (outputC & 0x0000FF00) >> 8;
                    int b = (outputC & 0x000000FF);

                    Assertions.assertTrue(r <= 128 && g <= 128 && b <= 128, "Should be closer to black");               
                }

            }
         }
    }

}
