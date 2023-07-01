package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.Util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;
import static org.assertj.core.api.Assertions.assertThat;

public class XmlReport {
    @Step("verify statistics in xml with <statistics>")
    public void verifyStatistics(Table statistics) throws IOException {
        assertThat(Paths.get(getCurrentProject().getProjectDir().getAbsolutePath())).exists();
        assertThat(Paths.get(getReportsPath())).exists();
        File file = new File(getReportsPath());
        FileInputStream fis = new FileInputStream(file);
        Document doc = Jsoup.parse(fis, null, "", Parser.xmlParser());
        int expectedTotalCount = doc.select("testsuites").get(0).children().size();
        int actualTotalCount = Integer.parseInt(statistics.getTableRows().get(0).getCell("totalCount"));
        assertThat(actualTotalCount).isEqualTo(expectedTotalCount);
        String expectedScenarioCount = doc.select("testsuites").get(0).child(0).attributes().get("tests");
        String actualScenarioCount = statistics.getTableRows().get(0).getCell("scenarioCount");
        assertThat(actualScenarioCount).isEqualTo(expectedScenarioCount);
        String expectedFailCount = doc.select("testsuites").get(0).child(0).attributes().get("failures");
        String actualFailCount = statistics.getTableRows().get(0).getCell("failCount");
        assertThat(actualFailCount).isEqualTo(expectedFailCount);
        String skippedCount = doc.select("testsuites").get(0).child(0).attributes().get("skipped");
        String expectedSkippedCount = skippedCount.length() > 0 ? skippedCount : "0";
        String actualSkippedCount = statistics.getTableRows().get(0).getCell("skippedCount");
        assertThat(actualSkippedCount).isEqualTo(expectedSkippedCount);
    }

    private String getReportsPath() {
        return getXmlReportsPath(null);
    }

    private String getXmlReportsPath(String specName) {
        if (specName == null)
            return Util.combinePath(getCurrentProject().getProjectDir().getAbsolutePath(), "reports", "xml-report", "result.xml");
        return Util.combinePath(getCurrentProject().getProjectDir().getAbsolutePath(), "reports", "xml-report", "specs", specName + ".xml");
    }
}
