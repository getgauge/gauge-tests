package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.assertj.core.api.Assertions.assertThat;

public class Console {

    @Step("Console should not contain following lines <table>")
    public void consoleShouldNotContainFollowingLines(Table table) throws IOException {
        String output = currentProject.getStdOut();
        for (String s : table.getColumnValues(0)) {
            assertThat(output).doesNotContain(s);
        }
    }

    @Step("Console should contain <text> <number of times> times")
    public void consoleShouldContain(String text, int numberOfTimes) throws IOException {
        String output = currentProject.getStdOut();
        int matchCount = StringUtils.countMatches(output, text);
        String errorMessage = "Expected '" + output + "' to have '" + text + "' " + numberOfTimes + " times. Found " + matchCount + " times.";

        assertThat(matchCount)
                .isEqualTo(numberOfTimes)
                .withFailMessage(errorMessage);
    }

    @Step({"Console should contain <message>", "The error message <message> should be displayed on console"})
    public void consoleShouldContain(String message) throws IOException {
        String output = currentProject.getStdOut();
        assertThat(output).contains(message);
    }

    @Step("Console should contain following lines in order <console output table>")
    public void consoleShouldContainFollowingLinesInOrder(Table table) throws IOException {
        String output = currentProject.getStdOut();

        for (String s : table.getColumnValues(0)){
            assertThat(output).contains(s);
        }
    }

}
