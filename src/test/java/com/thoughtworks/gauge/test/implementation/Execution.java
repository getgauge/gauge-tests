package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.test.common.Specification;

import java.io.IOException;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.assertj.core.api.Assertions.assertThat;

public class Execution {

    @Step("Execute the current project and ensure success")
    public void executeCurrentProjectAndEnsureSuccess() throws Exception {
        assertThat(currentProject.execute(false))
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the current project in parallel and ensure success")
    public void executeCurrentProjectInParallelAndEnsureSuccess() throws IOException, InterruptedException {
        assertThat(currentProject.executeInParallel())
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the current project in parallel in <n> streams and ensure success")
    public void executeCurrentProjectInParallelStreamsAndEnsureSuccess(int n) throws IOException, InterruptedException {
        assertThat(currentProject.executeInParallel(n))
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the specs in order and ensure success")
    public void executeSpecsInOrderAndEnsureSuccess() throws Exception {
        assertThat(currentProject.execute(true))
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the current project and ensure failure")
    public void executeCurrentProjectAndEnsureFailure() throws Exception {
        assertThat(currentProject.execute(false))
                .isFalse()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the spec <spec name> and ensure success")
    public void executeSpecAndEnsureSuccess(String specName) throws Exception {
        Specification spec = currentProject.findSpecification(specName);

        assertThat(spec).isNotNull();
        assertThat(currentProject.executeSpec(specName))
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the spec <spec name> with scenario index <scenario index> and ensure success")
    public void executeScenarioWithIndex(String specName, int scenarioIndex) throws Exception {
        Specification spec = currentProject.findSpecification(specName);

        assertThat(spec).isNotNull();
        assertThat(currentProject.executeSpecWithScenarioIndex(specName, scenarioIndex))
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the spec <spec name> with row range <row range> and ensure success")
    public void executeScenarioWithRowRange(String specName, String rowRange) throws Exception {
        Specification spec = currentProject.findSpecification(specName);

        assertThat(spec).isNotNull();
        assertThat(currentProject.executeSpecWithRowRange(specName, rowRange))
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the spec <spec name> and ensure failure")
    public void executeSpecAndEnsureFailure(String specName) throws Exception {
        Specification spec = currentProject.findSpecification(specName);

        assertThat(spec).isNotNull();
        assertThat(currentProject.executeSpec(specName))
                .isFalse()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the tags <tags> in spec <spec name> and ensure success")
    public void executeTagsAndEnsureSuccess(String tags, String specName) throws IOException, InterruptedException {
        Specification spec = currentProject.findSpecification(specName);

        assertThat(spec).isNotNull();
        assertThat(currentProject.executeTagsInSpec(tags, specName))
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Check for validation errors in the project and ensure failure")
    public void validateAndEnsureFailure() throws IOException, InterruptedException {
        assertThat(currentProject.validate())
                .isFalse()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Check for validation errors in the project and ensure success")
    public void validateAndEnsureSuccess() throws IOException, InterruptedException {
        assertThat(currentProject.validate())
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

    private String getFormattedProcessOutput()
    {
        return "*************** Process output start************\n" +
                currentProject.getLastProcessStdout() +
                "\n*************** Process output end************\n";
    }
}
