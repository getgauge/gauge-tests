package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.test.common.Specification;

import java.io.IOException;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Execution {

    @Step("Execute the current project and ensure success")
    public void executeCurrentProjectAndEnsureSuccess() throws Exception {
        assertTrue(isExecutionPassed());
    }

    @Step("Execute the current project in parallel and ensure success")
    public void executeCurrentProjectInParallelAndEnsureSuccess() throws IOException, InterruptedException {
        boolean passed = currentProject.executeInParallel();
        printProcessOutput(passed);
        assertTrue(passed);
    }

    @Step("Execute the current project in parallel in <n> streams and ensure success")
    public void executeCurrentProjectInParallelStreamsAndEnsureSuccess(int n) throws IOException, InterruptedException {
        boolean passed = currentProject.executeInParallel(n);
        printProcessOutput(passed);
        assertTrue(passed);
    }

    @Step("Execute the specs in order and ensure success")
    public void executeSpecsInOrderAndEnsureSuccess() throws Exception {
        assertTrue(isExecutionPassed(true));
    }

    @Step("Execute the current project and ensure failure")
    public void executeCurrentProjectAndEnsureFailure() throws Exception {
        assertTrue(!isExecutionPassed());
    }

    private boolean isExecutionPassed() throws Exception {
        boolean passed = currentProject.execute(false);
        printProcessOutput(passed);
        return passed;
    }

    private boolean isExecutionPassed(boolean sorted) throws Exception {
        boolean passed = currentProject.execute(sorted);
        printProcessOutput(passed);
        return passed;
    }

    @Step("Execute the spec <spec name> and ensure success")
    public void executeSpecAndEnsureSuccess(String specName) throws Exception {
        boolean passed = executeSpec(specName);
        printProcessOutput(passed);
        assertTrue(passed);
    }

    public void printProcessOutput(boolean passed) {
        if (!passed) {
            System.out.println("*************** Process output start************");
            System.out.println(currentProject.getLastProcessStdout());
            System.out.println("*************** Process output end************");
        }
    }

    @Step("Execute the spec <spec name> with scenario index <scenario index> and ensure success")
    public void executeScenarioWithIndex(String specName, int scenarioIndex) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            throw new RuntimeException("Specified spec is not present : " + specName);
        }
        boolean passed = currentProject.executeSpecWithScenarioIndex(specName, scenarioIndex);
        printProcessOutput(passed);
        assertTrue(passed);
    }

    @Step("Execute the spec <spec name> with row range <row range> and ensure success")
    public void executeScenarioWithRowRange(String specName, String rowRange) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            throw new RuntimeException("Specified spec is not present : " + specName);
        }
        boolean passed = currentProject.executeSpecWithRowRange(specName, rowRange);
        printProcessOutput(passed);
        assertTrue(passed);
    }

    @Step("Execute the spec <spec name> and ensure failure")
    public void executeSpecAndEnsureFailure(String specName) throws Exception {
        assertTrue(!executeSpec(specName));
    }

    public boolean executeSpec(String specName) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            throw new RuntimeException("Specified spec is not present : " + specName);
        }
        boolean passed = currentProject.executeSpec(specName);
        printProcessOutput(passed);
        return passed;
    }

    @Step("Execute the tags <tags> in spec <spec name> and ensure success")
    public void executeTagsAndEnsureSuccess(String tags, String specName) throws IOException, InterruptedException {
        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            throw new RuntimeException("Specified spec is not present : " + specName);
        }
        boolean passed = currentProject.executeTagsInSpec(tags, specName);
        printProcessOutput(passed);
        assertTrue(passed);
    }

    @Step("Check for validation errors in the project and ensure failure")
    public void validateAndEnsureFailure() throws IOException, InterruptedException {
        boolean passed = currentProject.validate();
        printProcessOutput(passed);
        assertFalse(passed);
    }

    @Step("Check for validation errors in the project and ensure success")
    public void validateAndEnsureSuccess() throws IOException, InterruptedException {
        boolean passed = currentProject.validate();
        printProcessOutput(passed);
        assertTrue(passed);
    }
}
