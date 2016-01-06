package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.test.common.Concept;
import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Specification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.junit.Assert.fail;

public class Refactor {
    @Step("Refactor step <First step> to <New step>")
    public void refactorStep(String oldStep, String newStep) throws IOException, InterruptedException {
        currentProject.refactorStep(oldStep, newStep);
    }

    @Step("The step <First step> should no longer be used")
    public void verifyStepIsNotPresent(String oldStep) throws IOException {
        GaugeProject currentProject1 = GaugeProject.getCurrentProject();
        List<Specification> specFiles = currentProject1.getAllSpecifications();
        for (Specification specification : specFiles)
            checkInFile(oldStep, specification.getSpecFile());
        for (Concept concept : currentProject1.getConcepts())
            checkInFile(oldStep, concept.getConceptFile());
    }

    private void checkInFile(String oldStep, File specFile) throws IOException {
        String message = "\n";
        if (isStepPresent(oldStep, specFile)) {
            message += "Step still exists: " + oldStep;
            fail(message + "\n");
        }
    }

    @Step("The step <First step> should be used in project")
    public void verifyStepIsPresent(String step) throws IOException {
        GaugeProject currentProject1 = GaugeProject.getCurrentProject();
        List<Specification> specFiles = currentProject1.getAllSpecifications();
        String message = "\n";
        for (Specification specification : specFiles)
            if (isStepPresent(step, specification.getSpecFile())) return;
        for (Concept concept : currentProject1.getConcepts())
            if (isStepPresent(step, concept.getConceptFile())) return;
        message += "Step doesn't exist: " + step;
        fail(message + "\n");

    }

    private boolean isStepPresent(String oldStep, File specFile) throws IOException {
        String sCurrentLine;
        BufferedReader br = new BufferedReader(new FileReader(specFile));
        while ((sCurrentLine = br.readLine()) != null) {
            if (sCurrentLine.trim().startsWith("*") || sCurrentLine.trim().startsWith("#")) {
                String step = sCurrentLine.trim().substring(1, sCurrentLine.length()).trim();
                if (step.equals(oldStep))
                    return true;
            }
        }
        return false;
    }

}
