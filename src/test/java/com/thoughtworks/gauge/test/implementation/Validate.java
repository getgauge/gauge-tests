package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.test.common.ExecutionSummary;
import com.thoughtworks.gauge.test.common.ExecutionSummaryAssert;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;

public class Validate {
    @Step("Check for validation errors in the project and ensure failure")
    public void validateAndEnsureFailure() throws Exception {
        assertOn(getCurrentProject().validate(), false);
    }

    @Step("Check for validation errors in the project and ensure success")
    public void validateAndEnsureSuccess() throws Exception {
        assertOn(getCurrentProject().validate(), true);
    }

    private ExecutionSummaryAssert assertOn(ExecutionSummary summary, boolean result) {
        return ExecutionSummaryAssert.assertThat(summary)
                .withFailMessage(getFormattedProcessOutput())
                .hasSuccess(result);
    }

    private String getFormattedProcessOutput() {
        return "\n*************** Process output start************\n" +
                getCurrentProject().getLastProcessStdout() +
                "\n*************** Process output end************\n";
    }
}
