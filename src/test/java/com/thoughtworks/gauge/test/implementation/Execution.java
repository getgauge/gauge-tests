package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.ExecutionSummary;
import com.thoughtworks.gauge.test.common.ExecutionSummaryAssert;
import com.thoughtworks.gauge.test.common.Specification;
import java.io.IOException;
import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;
import static org.assertj.core.api.Assertions.assertThat;

public class Execution {

    @Step("Rerun failed scenarios and ensure success")
    public void rerunFailedAndEnsureSuccess() throws Exception {
        assertOn(getCurrentProject().rerunFailed(), true);
    }

    @Step("Rerun failed scenarios with log level debug")
    public void rerunWithFailedAndEnsureFailure() throws Exception {
        assertOn(getCurrentProject().rerunFailedWithLogLevel(), false);
    }

    @Step("Rerun failed scenarios with specific directory")
    public void rerunFailedWithSpecificDirectory() throws Exception {
        assertThat(getCurrentProject().rerunFailedWithSpecificDir());
    }

    @Step("Execute current project with failed and repeat flags")
    public void executeCurrentProjectWithFailedAndRepeat() throws Exception {
        assertThat(getCurrentProject().executeRepeatWithFailed());
    }

    @Step("Repeat last run with log level debug and ensure success")
    public void repeatLastRunWithLogLevelAndEnsureSuccess() throws Exception {
        assertOn(getCurrentProject().repeatLastRunWithLogLevel(), true);
    }

    @Step("Repeat last run with specific directory")
    public void repeatLastRunWithSpecificDirectory() throws Exception {
        assertThat(getCurrentProject().repeatLastRunWithSpecificDir());
    }

    public enum Result {
        FAILURE,
        SUCCESS
    }

    @Step("Execute the spec <spec> from folder <specs/subfolder> and ensure success")
    public void executeFromSpecFolderAndEnsureSuccess(String spec, String subFolder) throws Exception {
        assertThat(getCurrentProject().executeSpecFromFolder(spec + ".spec", subFolder)).isTrue().withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the spec folder <specs/subfolder> and ensure success")
    public void executeTheSpecFolderAndEnsureSuccess(String subFolder) throws Exception {
        assertThat(getCurrentProject().executeSpecFolder(subFolder)).isTrue().withFailMessage(getFormattedProcessOutput());
    }

    @Step("Execute the current project and ensure success")
    public void executeCurrentProjectAndEnsureSuccess() throws Exception {
        assertOn(getCurrentProject().execute(false), true);
    }

    @Step("Execute the current project in parallel and ensure success")
    public void executeCurrentProjectInParallelAndEnsureSuccess() throws Exception {
        assertOn(getCurrentProject().executeInParallel(), true);
    }

    @Step("Execute the current project in parallel in <n> streams and ensure failure")
    public void executeCurrentProjectInParallelStreamsAndEnsureFailure(int n) throws Exception {
        assertOn(getCurrentProject().executeInParallel(n), false);
    }

    @Step("Execute the current project in parallel in <n> streams and ensure success")
    public void executeCurrentProjectInParallelStreamsAndEnsureSuccess(int n) throws Exception {
        assertOn(getCurrentProject().executeInParallel(n), true);
    }

    @Step("Execute the specs in order and ensure success")
    public void executeSpecsInOrderAndEnsureSuccess() throws Exception {
        assertOn(getCurrentProject().execute(true), true);
    }

    @Step("Execute the following specs in order and ensure success <specs>")
    public void executeGivenSpecsInOrderAndEnsureSuccess(Table specs) throws Exception {
        assertOn(getCurrentProject().executeSpecsInOrder(specs.getColumnValues(0)), true);
    }

    @Step("Execute the current project and ensure failure")
    public void executeCurrentProjectAndEnsureFailure() throws Exception {
        assertOn(getCurrentProject().execute(false), false);
    }

    @Step("Rerun failed scenarios and ensure failure")
    public void rerunCurrentProjectAndEnsureFailure() throws Exception {
        assertOn(getCurrentProject().rerunFailed(), false);
    }

    @Step("Execute the spec <spec name> and ensure success")
    public void executeSpecAndEnsureSuccess(String specName) throws Exception {
        Specification spec = getCurrentProject().findSpecification(specName);
        assertThat(spec).isNotNull();
        assertOn(getCurrentProject().executeSpec(specName), true);
    }

    @Step("Execute the spec <spec name> with scenario at <line number> and ensure success")
    public void executeScenarioWithLineNumber(String specName, int lineNumber) throws Exception {
        Specification spec = getCurrentProject().findSpecification(specName);
        assertThat(spec).isNotNull();
        assertOn(getCurrentProject().executeSpecWithScenarioLineNumber(specName, lineNumber), true);
    }

    @Step("Execute the spec <spec name> with row range <row range> and ensure success")
    public void executeScenarioWithRowRange(String specName, String rowRange) throws Exception {
        Specification spec = getCurrentProject().findSpecification(specName);
        assertThat(spec).isNotNull();
        assertOn(getCurrentProject().executeSpecWithRowRange(specName, rowRange), true);
    }

    @Step("Execute the spec <spec name> and ensure failure")
    public void executeSpecAndEnsureFailure(String specName) throws Exception {
        Specification spec = getCurrentProject().findSpecification(specName);
        assertThat(spec).isNotNull();
        assertOn(getCurrentProject().executeSpec(specName), false);
    }

    @Step("Execute the tags <tags> in spec <spec name> and ensure success")
    public void executeTagsAndEnsureSuccess(String tags, String specName) throws Exception {
        Specification spec = getCurrentProject().findSpecification(specName);
        assertThat(spec).isNotNull();
        assertOn(getCurrentProject().executeTagsInSpec(tags, specName), true);
    }

    private String getFormattedProcessOutput() {
        return "\n*************** Process output start************\n" + getCurrentProject().getLastProcessStdout() + "\n*************** Process output end************\n";
    }

    private ExecutionSummaryAssert assertOn(ExecutionSummary summary, boolean result) {
        return ExecutionSummaryAssert.assertThat(summary).hasSuccess(result).withFailMessage(getFormattedProcessOutput());
    }

    @Step("Configure project to take custom screenshot and return <some screenshot> as byte array")
    public void configureScreengrabber(String stubScreenshot) throws IOException {
        getCurrentProject().configureCustomScreengrabber(stubScreenshot);
    }

    @Step("Repeat last run and ensure <result>")
    public void repeatLastRun(Result argResult) throws Exception {
        assertOn(getCurrentProject().repeatLastRun(), argResult == Result.SUCCESS);
    }
}