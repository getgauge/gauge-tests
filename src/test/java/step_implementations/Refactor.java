package step_implementations;

import com.thoughtworks.gauge.Step;
import common.GaugeProject;
import common.Specification;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static common.GaugeProject.currentProject;
import static org.junit.Assert.fail;

public class Refactor {
    @Step("Refactor step <First step> to <New step>")
    public void refactorStep(String oldStep, String newStep) throws IOException, InterruptedException {
        currentProject.refactorStep(oldStep, newStep);
    }

    @Step("The step <First step> with <param count> params should no longer be used")
    public void verifyStepIsNotPresent(String oldStep, Integer paramCount) throws IOException {
        verifyStepNotPresentInSpecs(oldStep);
        for (File file : currentProject.getImplementationFiles())
            if (isStepPresentInImpl(oldStep, paramCount, FileUtils.readFileToString(file)))
                fail("Step still exists in implementation: " + oldStep);
    }

    @Step("The step <First step> should be used in project with <param count> params")
    public void verifyStepIsPresent(String step, Integer paramCount) throws IOException {
        verifyStepPresentInSpecs(step);
        for (File file : currentProject.getImplementationFiles())
            if (isStepPresentInImpl(step, paramCount, FileUtils.readFileToString(file))) return;
        fail("Step does not exist in implementation: " + step);
    }

    @Step("The step <First step> should be used in specs")
    public void verifyStepIsPresentInSpecs(String step) throws IOException {
        verifyStepPresentInSpecs(step);
    }

    @Step("The step <First step> should no longer be used in specs")
    public void verifyStepIsNotPresentInSoecs(String oldStep) throws IOException {
        verifyStepNotPresentInSpecs(oldStep);
    }

    private void verifyStepPresentInSpecs(String step) throws IOException {
        GaugeProject currentProject = GaugeProject.getCurrentProject();
        List<Specification> specFiles = currentProject.getAllSpecifications();
        String message = "\n";
        for (Specification specification : specFiles)
            if (isStepPresent(step, specification.getSpecFile())) return;
        message += "Step doesn't exist: " + step;
        fail(message + "\n");
    }

    private boolean isStepPresent(String oldStep, File specFile) throws IOException {
        String sCurrentLine;
        BufferedReader br = new BufferedReader(new FileReader(specFile));
        while ((sCurrentLine = br.readLine()) != null) {
            if (!(sCurrentLine.trim().startsWith("*")))
                continue;
            String step = sCurrentLine.trim().substring(1, sCurrentLine.length()).trim();
            if (step.equals(oldStep))
                return true;
        }
        return false;
    }

    private boolean isStepPresentInImpl(String stepText, Integer paramCount, String data) {
        paramCount = paramCount == 0 ? 1 : paramCount;
        String[] lines = data.split(System.getProperty("line.separator"));
        for (int i = 0; i < lines.length; i++) {
            String stepName = lines[i].replace("@Step(\"", "").replace("\")", "");
            if (replaceParams(stepName).equals(replaceParams(stepText))) {
                Pattern p = Pattern.compile("\\(([^)]*)\\)");
                Matcher m = p.matcher(lines[i + 1]);
                while (m.find())
                    if (m.group(1).split(",").length == paramCount)
                        return true;
            }
        }
        return false;
    }

    private String replaceParams(String stepName) {
        return stepName.replaceAll("<[^>]*>", "").replaceAll("(\"[^\"]*?\")", "").trim();
    }

    private void verifyStepNotPresentInSpecs(String oldStep) throws IOException {
        GaugeProject currentProject = GaugeProject.getCurrentProject();
        List<Specification> specFiles = currentProject.getAllSpecifications();
        String message = "\n";
        for (Specification specification : specFiles) {
            if (isStepPresent(oldStep, specification.getSpecFile())) {
                System.out.println(specification.toString());
                System.out.println("***************************");
                System.out.println(FileUtils.readFileToString(specification.getSpecFile()));
                message += "Step still exists: " + oldStep;
                fail(message + "\n");
            }
        }
    }
}
