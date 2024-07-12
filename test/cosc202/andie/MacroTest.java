package cosc202.andie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Stack;

/**
 * <p>
 * Test class to verify the recording of macros, by comparing the test stack of
 * operations with the expected stack of operations, equals() methods were overriden in each action class used.
 * </p>
 * 
 * @author Lachlan McIntyre
 * @version 1.0
 */
public class MacroTest {

    /**
     * <p>
     * Test method for {@link MacroActions}.
     * Creates test stack and a real stack of operations
     * testing to see whether they are identical
     * </p>
     */
    @Test
    void macroStackTest() {
        Stack<ImageOperation> correctStack = new Stack<>();
        correctStack.push(new MedianFilter(5));
        correctStack.push(new GaussianBlur());
        correctStack.push(new Brightness(6));
        correctStack.push(new SharpenFilter());

        EditableImage imageToTest = new EditableImage(); 
        
        // Create the test image of goku
        try {
            imageToTest.open("test/cosc202/andie/goku.png");
            System.out.println("Image loaded successfully");
        } catch (Exception e) {
            System.out.println("Error opening the test image of Goku");
            e.printStackTrace();
            fail("Failed to open the test image");
        }

        // Start recording the macro
        EditableImage.isRecording = true;
        // Apply operations to the test image
        try {
            imageToTest.apply(new MedianFilter(5));
            imageToTest.apply(new GaussianBlur());
            imageToTest.apply(new Brightness(6));
            imageToTest.apply(new SharpenFilter());

        } catch (Exception e) {
            System.out.println("Error applying filters");
            e.printStackTrace();
            fail("Failed to apply filters to the test image");
        }

        // Stop recording the macro and get the stack of applied operations
        EditableImage.isRecording = false;
        Stack<ImageOperation> testStack = imageToTest.getMacroStack(); 

        // Compare stack sizes
        assertEquals(correctStack.size(), testStack.size(), "The sizes of the stacks are not the same");

        // Iterate through both stacks and compare each operation
        while (!correctStack.isEmpty() && !testStack.isEmpty()) {
            ImageOperation expected = correctStack.pop();
            ImageOperation actual = testStack.pop();
            assertEquals(expected, actual, "The operations do not match");
        }
        System.out.println("Macro stack test completed successfully.");
    }

}
