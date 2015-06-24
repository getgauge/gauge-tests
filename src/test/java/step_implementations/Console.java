package step_implementations;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static common.GaugeProject.currentProject;

public class Console {

    @Step("Console should not contain following lines <table>")
    public void consoleShouldNotContainFollowingLines(Table table) throws IOException {
        String output = currentProject.getStdOut();
        boolean contains = false;
        String message = "\n";
        for (List<String> row : table.getRows()) {
            if (output.contains(row.get(0))) {
                contains = true;
                message += "Output contains :" + row.get(0) + "\n";
            }
        }
        if (contains)
            fail(message);
    }

    @Step("Console should contain <text> <number of times> times")
    public void consoleShouldContain(String text, int numberOfTimes) throws IOException {
        String output = currentProject.getStdOut();
        assertEquals(numberOfTimes, StringUtils.countMatches(output, text));
    }

    @Step({"Console should contain <message>", "The error message <message> should be displayed on console"})
    public void consoleShouldContain(String message) throws IOException {
        String output = currentProject.getStdOut();
        assertTrue("Console doesn't contain '" + message + "'. The output given is : " + output, output.contains(message));
    }

    @Step("Console should contain following lines in order <console output table>")
    public void consoleShouldContainFollowingLinesInOrder(Table table) throws IOException {
        String output = currentProject.getStdOut();
        String outputCopy = output;
        String row1, row2;

        for (int i = 0; i < table.getRows().size() - 1; i++) {
            row1 = table.getRows().get(i).get(0);
            row2 = table.getRows().get(i + 1).get(0);
            if (!output.contains(row1)) {
                String message = "Console doesn't contain " + row1 + "\n" +
                        "Actual output: \n" + outputCopy;
                fail(message);
            }
            if (!output.contains(row2)) {
                String message = "Console doesn't contain " + row2 + "\n" +
                        "Actual output: \n" + outputCopy;
                fail(message);
            }
            if (output.indexOf(row1) < output.indexOf(row2)) {
                output = output.replaceFirst(row1, "");
            } else {
                String message = "Output was not in order \n";
                message += "******************Actual Console Output Start************\n";
                message += outputCopy;
                message += "******************Actual Console Output End************\n";
                fail(message);
            }
        }
    }

}
