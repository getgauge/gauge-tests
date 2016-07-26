package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.Util;
import org.assertj.core.api.Assertions;

import java.io.IOException;
import java.util.Base64;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;

public class HtmlReport {
    @Step("Generated html report should have <some screenshot> for <table>")
    public void verifyCustomScreenshot(String stubScreenshot, Table stepTexts) throws IOException {
        String resultPath = Util.combinePath(currentProject.getProjectDir().getAbsolutePath(), "reports", "html-report", "js", "result.js");

        String result = Util.read(resultPath);
        result = result.substring(result.indexOf("var gaugeExecutionResult = ") + 1, result.indexOf(";"));

        Assertions.assertThat(result).contains("\"screenShot\":\"" + Base64.getEncoder().encodeToString(stubScreenshot.getBytes())+"\"");
    }
}