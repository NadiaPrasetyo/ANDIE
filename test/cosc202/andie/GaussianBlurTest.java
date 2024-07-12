package cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GaussianBlurTest {

    @Test
    public void testDefaultGaussianBlur() {
        GaussianBlur gb = new GaussianBlur(1);
        float[] actual = gb.getKernel();

        
         float[] expected = 
             {(float)0.000, (float)0.011, (float)0.000,
             (float)0.011, (float)0.957, (float)0.011,
             (float)0.000, (float)0.011, (float)0.000};
        
         Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testRadius2GaussianBlur() {
        GaussianBlur gb = new GaussianBlur(2);
        float[] actual = gb.getKernel();

        
         float[] expected = 
             {(float)0.000, (float)0.001, (float)0.004, (float)0.001, (float)0.000,
             (float)0.001, (float)0.038, (float)0.116, (float)0.038, (float)0.001,
             (float)0.004, (float)0.116, (float)0.358, (float)0.116, (float)0.004,
             (float)0.001, (float)0.038, (float)0.116, (float)0.038, (float)0.001,
            (float)0.000, (float)0.001, (float)0.004, (float)0.001, (float)0.000,};
        
         Assertions.assertArrayEquals(expected, actual);
    }

}
