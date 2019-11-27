package com.thoughtworks.gauge.test.implementation;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.Util;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Node;
import se.fishtank.css.selectors.Selectors;
import se.fishtank.css.selectors.dom.W3CNode;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HtmlReport {

    @Step("Step <stepText> should appear in <specName> <times> times in the report")
    public void stepShouldAppearNTimesInReport(String stepText,String specName, Integer times) throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
        final WebClient webClient = getWebClient();
        String reportsPath = getReportsPath(specName);
        final HtmlPage page = webClient.getPage("file://" + reportsPath);
        Selectors<Node,W3CNode> selectors = new Selectors<Node,W3CNode>(new W3CNode(page.getDocumentElement()));
        List<Node> divs = selectors.querySelectorAll(".step-txt");
        int actualNumberOfTimes = 0;
        for (Node div : divs) {
            if(div.getTextContent().contains(stepText))
            {
                actualNumberOfTimes++;
            }
        }
        assertThat(actualNumberOfTimes).isEqualTo(times);
    }

    public enum ElementTypes {
        SUITE,
        SPEC,
        SCENARIO,
        STEP
    }

    @Step("Generated html report should have screenshot in spec <specName> for element <type> <hooks>")
    public void verifyScreenshot(String specName, ElementTypes elementType, Table scenarios) throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
        final WebClient webClient = getWebClient();
        String reportsPath = getReportsPath(specName);
        final HtmlPage page = webClient.getPage("file://" + reportsPath);
        Selectors<Node, W3CNode> selectors = new Selectors<>(new W3CNode(page.getDocumentElement()));
        String elementSelector = null;
        String elementTypeName = null;
        switch (elementType) {
            case SCENARIO:
                elementSelector = ".scenario-head";
                elementTypeName = "scenario";
                break;
            default:
        }
        List<Node> divs = selectors.querySelectorAll(elementSelector);
        assertThat(divs.size() > 0);
        for (Node div : divs) {
            scenarios.getColumnValues(elementTypeName).stream().filter(scenario -> div.getTextContent().contains(scenario)).forEach(scenario -> {
                Selectors<Node, W3CNode> errorSelectors = new Selectors<>(new W3CNode(div.getParentNode()));
                Node screenshotThumbnail = (Node) errorSelectors.querySelectorAll("img.screenshot-thumbnail").get(0);
                String actual = screenshotThumbnail.getAttributes().getNamedItem("src").getTextContent();
                assertThat(actual).isNotNull();
            });
        }
    }

    @Step("Generated html report should have <some_screenshot.png> file in spec <spec> for <table>")
    public void verifyCustomScreenshot(String stubScreenshot, String specName, Table stepTexts) throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
        String expected = "../images/" + stubScreenshot;
        final WebClient webClient = getWebClient();
        String reportsPath = getReportsPath(specName);
        final HtmlPage page = webClient.getPage("file://" + reportsPath);
        Selectors<Node, W3CNode>  selectors = new Selectors<>(new W3CNode(page.getDocumentElement()));
        List<Node> divs = selectors.querySelectorAll(".step-txt");
        for (Node div : divs) {
            stepTexts.getColumnValues("step text").stream().filter(stepText -> div.getTextContent().contains(stepText)).forEach(stepText -> {
                Selectors<Node, W3CNode>  errorSelectors = new Selectors<>(new W3CNode(div.getParentNode()));
                Node screenshotThumbnail = (Node) errorSelectors.querySelectorAll("img.screenshot-thumbnail").get(0);
                String actual = screenshotThumbnail.getAttributes().getNamedItem("src").getTextContent();
                assertThat(actual).isEqualTo(expected);
            });
        }
    }

    private String getReportsPath() {
        return getReportsPath(null);
    }

    private String getReportsPath(String specName) {
        if (specName == null)
            return Util.combinePath(getCurrentProject().getProjectDir().getAbsolutePath(), "reports", "html-report", "index.html");
        return Util.combinePath(getCurrentProject().getProjectDir().getAbsolutePath(), "reports", "html-report", "specs", specName + ".html");
    }

    @Step("verify statistics in html with <statistics>")
    public void verifyStatistics(Table statistics) throws IOException {
        assertTrue(Files.exists(Paths.get(getCurrentProject().getProjectDir().getAbsolutePath())));
        assertTrue(Files.exists(Paths.get(getReportsPath())));
        org.jsoup.nodes.Document doc = Jsoup.parse(new File(getReportsPath()), "UTF-8");
        String expectedTotalCount = doc.select(".total-specs").get(0).child(1).text();
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
