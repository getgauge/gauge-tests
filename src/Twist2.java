import java.io.File;

public class Twist2 {


    public void initialize(File projectDir, String language) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("twist2", "--init", language);
        processBuilder.directory(projectDir);
        Process process = processBuilder.start();
        process.waitFor();
    }
}
