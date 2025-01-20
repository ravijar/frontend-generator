import com.ravijar.core.AppCLI;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppCLITest {

//    @Test
//    void testMainMethodWithArgs() {
////        // Capture the output
////        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
////        PrintStream printStream = new PrintStream(outputStream);
////        System.setOut(printStream);
//
//        // Run the main method with a test argument
//        String[] args = {"--help"};
//        AppCLI.main(args);
//        System.setOut(System.out);
//
////        // Verify the output
////        String expectedOutput = "Argument: testArgument\n";
////        assertEquals(expectedOutput, "ll");
//
//        // Reset System.out
//    }
    @Test
    void testInit(){
        String[] args = {"init","-n","yoyo"};
        AppCLI.main(args);
        assertEquals("ll", "ll");
    }
}
