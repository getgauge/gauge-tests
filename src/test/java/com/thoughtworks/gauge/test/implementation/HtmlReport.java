package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.Util;

import java.io.IOException;
import java.util.Base64;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.assertj.core.api.Assertions.assertThat;

public class HtmlReport {
    @Step("Generated html report should have <some screenshot> for <table>")
    public void verifyCustomScreenshot(String stubScreenshot, Table stepTexts) throws IOException {
        for (String stepText : stepTexts.getColumnValues(0)) {
            String path = this.getClass().getClassLoader().getResource("get-screenshot.js").getPath();
            String expected = "data:image/png;base64," + Base64.getEncoder().encodeToString(stubScreenshot.getBytes());
            String reportsPath = "file://" + Util.combinePath(currentProject.getProjectDir().getAbsolutePath(), "reports", "html-report", "index.html");
            Process process = new ProcessBuilder("phantomjs", path, reportsPath, stepText).start();

            String screenshotText = convertStreamToString(process.getInputStream());

            assertThat(screenshotText).contains(expected);
        }
    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}