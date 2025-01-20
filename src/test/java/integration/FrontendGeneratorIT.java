package integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

public class FrontendGeneratorIT {

    @Test
    public void testInitCommand() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "--version");
        Process process = processBuilder.start();
        // Read the output of the command
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        System.out.println("line");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        int exitCode = process.waitFor();
        assertEquals(0, exitCode);
    }

//    @Test
//    public void testGenerateCommand() throws IOException, InterruptedException {
//        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "your-jar-file.jar", "generate");
//        Process process = processBuilder.start();
//        int exitCode = process.waitFor();
//        assertEquals(0, exitCode, "Generate command failed");
//    }
//
//    @Test
//    public void testRunCommand() throws IOException, InterruptedException {
//        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", "your-jar-file.jar", "run");
//        Process process = processBuilder.start();
//        int exitCode = process.waitFor();
//        assertEquals(0, exitCode, "Run command failed");
//    }
}
