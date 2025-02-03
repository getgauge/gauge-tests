package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;
import static org.assertj.core.api.Assertions.assertThat;

public class Statistics {

    @Step("Statistics generated should have <table>")
    public void statisticsGeneratedShouldHave(Table expectedStatisticsData) {
        String output = getCurrentProject().getStdOut();
        for (TableRow row : expectedStatisticsData.getTableRows()) {
            StringBuilder statisticsData = new StringBuilder();
            statisticsData.append(row.getCell("Statistics name")).append(": ");
            statisticsData.append(row.getCell("executed")).append(" executed ");
            statisticsData.append(row.getCell("passed")).append(" passed ");
            statisticsData.append(row.getCell("failed")).append(" failed ");
            statisticsData.append(row.getCell("skipped")).append(" skipped");
            assertThat(output.replaceAll("\t", " ")).contains(statisticsData);
        }
    }
}