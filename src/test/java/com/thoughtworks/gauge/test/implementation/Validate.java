package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.test.common.ExecutionSummary;
import com.thoughtworks.gauge.test.common.ExecutionSummaryAssert;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;

/**
 * Created by sswaroop on 7/26/16.
 */
public class Validate {
    @Step("Check for validation errors in the project and ensure failure")
    public void validateAndEnsureFailure() throws Exception {
        assertOn(currentProject.validate(), false);
    }

    @Step("Check for validation errors in the project and ensure success")
    public void validateAndEnsureSuccess() throws Exception {
        assertOn(currentProject.validate(), true);
    }

    private ExecutionSummaryAssert assertOn(ExecutionSummary summary, boolean result) {
        return ExecutionSummaryAssert.assertThat(summary)
                .hasSuccess(result)
                .withFailMessage(getFormattedProcessOutput());
    }

    private String getFormattedProcessOutput() {
        return "\n*************** Process output start************\n" +
                currentProject.getLastProcessStdout() +
                "\n*************** Process output end************\n";
    }
}
