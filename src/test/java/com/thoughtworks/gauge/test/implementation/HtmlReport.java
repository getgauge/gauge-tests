package com.thoughtworks.gauge.test.implementation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.Util;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.w3c.dom.Node;
import se.fishtank.css.selectors.Selectors;
import se.fishtank.css.selectors.dom.W3CNode;

import java.io.File;
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

        final WebClient webClient = getWebClient();
        final HtmlPage page = webClient.getPage("file://" + getReportsPath());
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

    private String getReportsPath() {
        return Util.combinePath(getCurrentProject().getProjectDir().getAbsolutePath(), "reports", "html-report", "index.html");
    }

    @Step("verify statistics in html with <statistics>")
    public void verifyStatistics(Table statistics) throws IOException, InterruptedException {
        org.jsoup.nodes.Document doc = Jsoup.parse(new File(getReportsPath()), "UTF-8");
        String expectedTotalCount = doc.select(".total-specs").get(0).child(0).text();
        String actualTotalCount = statistics.getTableRows().get(0).getCell("totalCount");
        assertEquals("Total count:", expectedTotalCount, actualTotalCount);

        String expectedPassCount = doc.select(".pass").get(0).child(0).text();
        String actualPassCount = statistics.getTableRows().get(0).getCell("passCount");
        assertEquals("Pass count:", expectedPassCount, actualPassCount);

        String expectedFailCount = doc.select(".fail").get(0).child(0).text();
        String actualFailCount = statistics.getTableRows().get(0).getCell("failCount");
        assertEquals("Fail count:", expectedFailCount, actualFailCount);

        String expectedSkippedCount = doc.select(".skip").get(0).child(0).text();
        String actualSkippedCount = statistics.getTableRows().get(0).getCell("skippedCount");
        assertEquals("Skipped count:", expectedSkippedCount, actualSkippedCount);
    }

    private WebClient getWebClient() {
        final WebClient webClient = new WebClient();
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        return webClient;
    }
}