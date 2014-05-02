package common;



import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;

public class GaugeProject {

    private static String executableName = "twist2";
    private File projectDir;
    private String language;
    private Process lastProcess = null;

    public GaugeProject(File projectDir, String language) {
        this.projectDir = projectDir;
        this.language = language;
    }

    public void initialize() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder(executableName, "--init", language);
        processBuilder.directory(projectDir);
        lastProcess = processBuilder.start();
        lastProcess.waitFor();
    }

    public String getStdOut() throws IOException {
        if (lastProcess != null) {
            return IOUtils.toString(lastProcess.getInputStream());
        }
        return "";
    }

    public String getStdErr() throws IOException {
        if (lastProcess != null) {
            return IOUtils.toString(lastProcess.getErrorStream());
        }
        return "";
    }

    public File getProjectDir() {
        return projectDir;
    }
}
