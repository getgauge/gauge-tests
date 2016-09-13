package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProject;
import java.io.IOException;
import java.util.List;
import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sswaroop on 7/22/16.
 */
public class Statistics {

    @Step("Statistics generated should have <table>")
    public void statisticsGenereatedShouldHave(Table expectedStatisticsData) throws IOException {
        String output = currentProject.getStdOut();
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