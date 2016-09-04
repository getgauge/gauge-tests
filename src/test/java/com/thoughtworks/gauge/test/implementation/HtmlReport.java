package com.thoughtworks.gauge.test.implementation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.Util;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;
import se.fishtank.css.selectors.Selectors;
import se.fishtank.css.selectors.dom.W3CNode;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class HtmlReport {

    @Step("Generated html report should have <some screenshot> for <table>")
    public void verifyCustomScreenshot(String stubScreenshot, Table stepTexts) throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
        String expected = "data:image/png;base64," + Base64.getEncoder().encodeToString(stubScreenshot.getBytes());
        String reportsPath = "file://" + Util.combinePath(currentProject.getProjectDir().getAbsolutePath(), "reports", "html-report", "index.html");
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(reportsPath);
            Selectors selectors = new Selectors(new W3CNode(page.getDocumentElement()));
            List<Node> divs = selectors.querySelectorAll(".step-txt");
            for (Node div : divs) {
                for (String stepText : stepTexts.getColumnValues(0)) {
                    if (div.getTextContent().contains(stepText)) {
                        Selectors errorSelectors = new Selectors(new W3CNode(div.getParentNode()));
                        Node screenshotThumbnail = (Node) errorSelectors.querySelectorAll("img.screenshot-thumbnail").get(0);
                        String actual = screenshotThumbnail.getAttributes().getNamedItem("src").getTextContent();
                        assertThat(actual).isEqualTo(expected);
                    }
                }
            }
        }
    }

    @Step("verify statistics in html with totalCount <actualTotalCount>, passCount <actualPassCount>, failCount <actualFailCount>, skippedCount <actualSkippedCount>")
    public void verifyStatistics(String actualTotalCount, String actualPassCount, String actualFailCount, String actualSkippedCount) throws IOException {
        String reportsPath = "file://" + Util.combinePath(currentProject.getProjectDir().getAbsolutePath(), "reports", "html-report", "index.html");
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage(reportsPath);
            Selectors selectors = new Selectors(new W3CNode(page.getDocumentElement()));
            String expectedTotalCount = ((HtmlDivision) selectors.querySelectorAll(".total-specs").get(0)).getFirstChild().asText();
            String expectedPassCount = ((HtmlListItem) selectors.querySelectorAll(".pass").get(0)).getFirstChild().asText();
            String expectedFailCount = ((HtmlListItem) selectors.querySelectorAll(".fail").get(0)).getFirstChild().asText();
            String expectedSkippedCount = ((HtmlListItem) selectors.querySelectorAll(".skip").get(0)).getFirstChild().asText();
            assertEquals(expectedTotalCount, actualTotalCount);
            assertEquals(expectedPassCount, actualPassCount);
            assertEquals(expectedFailCount, actualFailCount);
            assertEquals(expectedSkippedCount, actualSkippedCount);
        }
    }
}