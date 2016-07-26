package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.ExecutionSummary;
import com.thoughtworks.gauge.test.common.ExecutionSummaryAssert;
import com.thoughtworks.gauge.test.common.Specification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.assertj.core.api.Assertions.assertThat;

public class Execution {


    @Step("Execute the spec <spec> from folder <specs/subfolder> and ensure success")
    public void executeFromSpecFolderAndEnsureSuccess(String spec, String subFolder) throws Exception {
        assertThat(currentProject.executeSpecFromFolder(spec + ".spec", subFolder))
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
        assertOn(currentProject.execute(false), true);
    }

    @Step("Execute the current project in parallel and ensure success")
    public void executeCurrentProjectInParallelAndEnsureSuccess() throws Exception {
        assertOn(currentProject.executeInParallel(), true);
    }

    @Step("Execute the current project in parallel in <n> streams and ensure success")
    public void executeCurrentProjectInParallelStreamsAndEnsureSuccess(int n) throws Exception {
        assertOn(currentProject.executeInParallel(n), true);

    }

    @Step("Execute the specs in order and ensure success")
    public void executeSpecsInOrderAndEnsureSuccess() throws Exception {
        assertOn(currentProject.execute(true), true);
    }

    @Step("Execute the current project and ensure failure")
    public void executeCurrentProjectAndEnsureFailure() throws Exception {
        assertOn(currentProject.execute(false), false);
    }

    @Step("Rerun failed scenarios and ensure failure")
    public void rerunCurrentProjectAndEnsureFailure() throws Exception {
        assertOn(currentProject.rerun(), false);
    }

    @Step("Execute the spec <spec name> and ensure success")
    public void executeSpecAndEnsureSuccess(String specName) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        assertThat(spec).isNotNull();

        assertOn(currentProject.executeSpec(specName), true);
    }

    @Step("Execute the spec <spec name> with scenario at <line number> and ensure success")
    public void executeScenarioWithLineNumber(String specName, int lineNumber) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        assertThat(spec).isNotNull();
        assertOn(currentProject.executeSpecWithScenarioLineNumber(specName, lineNumber), true);
    }

    @Step("Execute the spec <spec name> with row range <row range> and ensure success")
    public void executeScenarioWithRowRange(String specName, String rowRange) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        assertThat(spec).isNotNull();
        assertOn(currentProject.executeSpecWithRowRange(specName, rowRange), true);
    }

    @Step("Execute the spec <spec name> and ensure failure")
    public void executeSpecAndEnsureFailure(String specName) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        assertThat(spec).isNotNull();
        assertOn(currentProject.executeSpec(specName), false);

    }

    @Step("Execute the tags <tags> in spec <spec name> and ensure success")
    public void executeTagsAndEnsureSuccess(String tags, String specName) throws Exception {
        Specification spec = currentProject.findSpecification(specName);

        assertThat(spec).isNotNull();
        assertOn(currentProject.executeTagsInSpec(tags, specName), true);
    }

    private String getFormattedProcessOutput() {
        return "\n*************** Process output start************\n" +
                currentProject.getLastProcessStdout() +
                "\n*************** Process output end************\n";
    }

    private ExecutionSummaryAssert assertOn(ExecutionSummary summary, boolean result) {
        return ExecutionSummaryAssert.assertThat(summary)
                .hasSuccess(result)
                .withFailMessage(getFormattedProcessOutput());
    }

    @Step("Configure project to take custom screenshot and return <some screenshot> as byte array")
    public void configureScreengrabber(String stubScreenshot) throws IOException {
        currentProject.configureCustomScreengrabber(stubScreenshot);
    }
}
