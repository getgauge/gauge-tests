package com.thoughtworks.gauge.test.implementation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlListItem;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;
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

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class HtmlReport {

    @Step("Generated html report should have <some screenshot> for <table>")
    public void verifyCustomScreenshot(String stubScreenshot, Table stepTexts) throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

        String expected = "data:image/png;base64," + Base64.getEncoder().encodeToString(stubScreenshot.getBytes());

        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage(getReportsPath());
        Selectors selectors = new Selectors(new W3CNode(page.getDocumentElement()));
        List<Node> divs = selectors.querySelectorAll(".step-txt");

        for (Node div : divs) {
            stepTexts.getColumnValues(0).stream().filter(stepText -> div.getTextContent().contains(stepText)).forEach(stepText -> {
                Selectors errorSelectors = new Selectors(new W3CNode(div.getParentNode()));
                Node screenshotThumbnail = (Node) errorSelectors.querySelectorAll("img.screenshot-thumbnail").get(0);
                String actual = screenshotThumbnail.getAttributes().getNamedItem("src").getTextContent();
                assertThat(actual).isEqualTo(expected);
            });
        }
    }

    @Step("verify statistics in html with <statistics>")
    public void verifyStatistics(Table statistics) throws IOException, InterruptedException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage(getReportsPath());
        JavaScriptJobManager manager = page.getEnclosingWindow().getJobManager();
        while (manager.getJobCount() > 0)
            Thread.sleep(1000);
        Selectors selectors = new Selectors(new W3CNode(page.getDocumentElement()));

        String expectedTotalCount = ((HtmlDivision) selectors.querySelectorAll(".total-specs").get(0)).getFirstChild().asText();
        String actualTotalCount = statistics.getTableRows().get(0).getCell("totalCount");
        assertEquals("Total count:", expectedTotalCount, actualTotalCount);

        String expectedPassCount = ((HtmlListItem) selectors.querySelectorAll(".pass").get(0)).getFirstChild().asText();
        String actualPassCount = statistics.getTableRows().get(0).getCell("passCount");
        assertEquals("Pass count:", expectedPassCount, actualPassCount);

        String expectedFailCount = ((HtmlListItem) selectors.querySelectorAll(".fail").get(0)).getFirstChild().asText();
        String actualFailCount = statistics.getTableRows().get(0).getCell("failCount");
        assertEquals("Fail count:", expectedFailCount, actualFailCount);

        String expectedSkippedCount = ((HtmlListItem) selectors.querySelectorAll(".skip").get(0)).getFirstChild().asText();
        String actualSkippedCount = statistics.getTableRows().get(0).getCell("skippedCount");
        assertEquals("Skipped count:", expectedSkippedCount, actualSkippedCount);
    }

    private String getReportsPath() {
        return "file://" + Util.combinePath(getCurrentProject().getProjectDir().getAbsolutePath(), "reports", "html-report", "index.html");
    }
}