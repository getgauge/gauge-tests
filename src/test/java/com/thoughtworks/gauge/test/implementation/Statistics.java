package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import java.io.IOException;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;
import static org.assertj.core.api.Assertions.assertThat;

public class Statistics {

    @Step("Statistics generated should have <table>")
    public void statisticsGenereatedShouldHave(Table expectedStatisticsData) throws IOException {
        String output = getCurrentProject().getStdOut();
        for (TableRow row : expectedStatisticsData.getTableRows()) {
            StringBuilder statisticsData = new StringBuilder();
            statisticsData.append(row.getCell("Statistics name") + ": ");
            statisticsData.append(row.getCell("executed") + " executed ");
            statisticsData.append(row.getCell("passed") + " passed ");
            statisticsData.append(row.getCell("failed") + " failed ");
            statisticsData.append(row.getCell("skipped") + " skipped");
            assertThat(output.replaceAll("\t", " ")).contains(statisticsData);
        }
    }
}