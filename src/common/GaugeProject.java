package common;


import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GaugeProject {

    private static String executableName = "twist2";
    private static String specsDirName = "specs";
    private static String stepImplementationsDir = "src/test/java";

    private File projectDir;
    private String language;
    private Process lastProcess = null;
    private ArrayList<Specification> specifications = new ArrayList<Specification>();
    private static GaugeProject current;
    private String lastProcessStderr;
    private String lastProcessStdout;

    public static GaugeProject getCurrent() {
        if (current == null) {
            throw new RuntimeException("Gauge project is not initialized yet");
        }
        return current;
    }

    public GaugeProject(File projectDir, String language) {
        this.projectDir = projectDir;
        this.language = language;
        current = this;
    }

    public boolean initialize() throws Exception {
        executeGaugeCommand("--init", language);
        return lastProcess.exitValue() == 0;
    }

    public String getStdOut() throws IOException {
        return lastProcessStdout;
    }

    public String getStdErr() throws IOException {
        return lastProcessStderr;
    }

    public File getProjectDir() {
        return projectDir;
    }

    public Specification createSpecification(String name) throws IOException {
        File specFile = new File(projectDir, Util.combinePath(specsDirName, name) + ".spec");
        if (specFile.exists()) {
            throw new RuntimeException("Failed to create specification with name: " + name + "." + specFile.getAbsolutePath() + ": File already exists");
        }
        Specification specification = new Specification(name);
        specification.saveAs(specFile);
        specifications.add(specification);
        return specification;
    }

    public Specification findSpecification(String specName) {
        for (Specification specification : specifications) {
            if (specification.getName().equalsIgnoreCase(specName)) {
                return specification;
            }
        }

        return null;
    }

    public void implementStep(String stepText, String implementation) throws IOException {
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getFor(stepText);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String className = String.format("Steps%d%s", System.nanoTime(), dateFormat.format(new Date()));
        StringBuilder classText = new StringBuilder();
        classText.append("import com.thoughtworks.twist2.Step;\n");
        classText.append("public class ").append(className).append("{\n");
        classText.append("@Step(\"").append(stepValue.value).append("\")\n");
        classText.append("public void ").append("stepImplementation(");
        for (int i = 0; i < stepValue.paramCount; i++) {
            classText.append("String param").append(i).append(", ");
        }
        classText.append(") {\n").append(implementation).append("\n}");
        classText.append("}");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), className + ".java"), classText.toString());
    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "src/test/java").getAbsolutePath();
    }

    public boolean execute() throws Exception {
        executeGaugeCommand("specs/");
        return lastProcess.exitValue() == 0;
    }

    private void executeGaugeCommand(String... args) throws IOException, InterruptedException {
        ArrayList<String> command = new ArrayList<String>();
        command.add(executableName);
        for (String arg : args) {
            command.add(arg);
        }
        ProcessBuilder processBuilder = new ProcessBuilder(command.toArray(new String[command.size()]));
        processBuilder.directory(projectDir);
        lastProcess = processBuilder.start();
        lastProcess.waitFor();
        lastProcessStdout = IOUtils.toString(lastProcess.getInputStream());
        lastProcessStderr = IOUtils.toString(lastProcess.getErrorStream());
    }
}
