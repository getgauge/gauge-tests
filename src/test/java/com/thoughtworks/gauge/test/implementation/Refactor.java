package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.test.common.Concept;
import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Specification;
import com.thoughtworks.gauge.test.common.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


public class Refactor {
    @Step("Refactor step <Old step> to <New step>")
    public void refactorStep(String oldStep, String newStep) throws Exception {
        if (Util.isWindows()) {
            oldStep = oldStep.replaceAll("\\\"", "\\\\\"");
            newStep = newStep.replaceAll("\\\"", "\\\\\"");
        }
        getCurrentProject().refactorStep(oldStep, newStep);
    }

    @Step("The step <First step> should no longer be used")
    public void verifyStepIsNotPresent(String oldStep) throws IOException {
        GaugeProject currentProject1 = GaugeProject.getCurrentProject();
        List<Specification> specFiles = currentProject1.getAllSpecifications();

        for (Specification specification : specFiles)
            assertThat(isStepPresent(oldStep, specification.getSpecFile()))
                    .isFalse()
                    .withFailMessage("Found Step :'" + oldStep + "' in Spec: " + specification.getSpecFile().getAbsolutePath());

        for (Concept concept : currentProject1.getConcepts())
            assertThat(isStepPresent(oldStep, concept.getConceptFile()))
                    .isFalse()
                    .withFailMessage("Found Step :'" + oldStep + "' in Concept: " + concept.getConceptFile().getAbsolutePath());
    }

    @Step("The step <First step> should be used in project")
    public void verifyStepIsPresent(String step) throws IOException {
        GaugeProject currentProject1 = GaugeProject.getCurrentProject();
        List<Specification> specFiles = currentProject1.getAllSpecifications();

        for (Specification specification : specFiles)
            if (isStepPresent(step, specification.getSpecFile())) return;
        for (Concept concept : currentProject1.getConcepts())
            if (isStepPresent(step, concept.getConceptFile())) return;

        fail("\nCould not find step in any spec/concept files: " + step + "\n");
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
