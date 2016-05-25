package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.Specification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.assertj.core.api.Assertions.assertThat;

public class Execution {


    @Step("Execute the spec <spec> from folder <specs/subfolder> and ensure success")
    public void executeFromSpecFolderAndEnsureSuccess(String spec, String subFolder) throws Exception {
        assertThat(currentProject.executeSpecFromFolder(spec + ".spec",subFolder))
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the spec folder <specs/subfolder> and ensure success")
    public void executeTheSpecFolderAndEnsureSuccess(String subFolder) throws Exception {
        assertThat(currentProject.executeSpecFolder(subFolder))
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());
    }

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

    @Step("Rerun failed scenarios and ensure failure")
    public void rerunCurrentProjectAndEnsureFailure() throws Exception {
        assertThat(currentProject.rerun())
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

    @Step("Execute the specs and ensure success <table>")
    public void executeSpecAndEnsureSuccess(Table givenSpecNames) throws Exception {
        List<String> columnNames = givenSpecNames.getColumnNames();
        List<String> specNames = new ArrayList<>();
        for (TableRow row : givenSpecNames.getTableRows()) {
            String specName = row.getCell(columnNames.get(0));
            Specification specification = currentProject.findSpecification(specName);
            assertThat(specification).isNotNull();

            specNames.add(specName);
        }

        assertThat(currentProject.executeSpec(specNames))
                .isTrue()
                .withFailMessage(getFormattedProcessOutput());

    }

    @Step("Execute the spec <spec name> with scenario at <line number> and ensure success")
    public void executeScenarioWithLineNumber(String specName, int lineNumber) throws Exception {
        Specification spec = currentProject.findSpecification(specName);

        assertThat(spec).isNotNull();
        assertThat(currentProject.executeSpecWithScenarioLineNumber(specName, lineNumber))
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

    private String getFormattedProcessOutput() {
        return "*************** Process output start************\n" +
                currentProject.getLastProcessStdout() +
                "\n*************** Process output end************\n";
    }
}
