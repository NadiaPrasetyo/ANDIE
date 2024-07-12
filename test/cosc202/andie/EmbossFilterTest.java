
package cosc202.andie;

import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmbossFilterTest {
    
    @Test
    public void testEmbossFilter() {

        for(int i = 1; i < 9; i++){
            EmbossFilter ef = new EmbossFilter(i);

            try{
    
                BufferedImage input = ImageIO.read(new File("test\\cosc202\\andie\\goku.png"));
                BufferedImage output = ef.apply(input, true);
                Assertions.assertNotNull(output, "Image is null after applying the filter");
    
                boolean similarStates = false;
                for(int y = 0; y < input.getHeight(); y++){
                    for(int x = 0; x < input.getWidth(); x++){
    
                        if(input.getRGB(x, y) != output.getRGB(x, y)){
    
                            similarStates = true;
                            break;
                        }
                    }
                }
    
                Assertions.assertTrue(similarStates, "There were no changes to the image after applying the filter");
            
    
            }catch(Exception e){
                System.out.println("Error while opening image");
            }
        }

    }

    

}
