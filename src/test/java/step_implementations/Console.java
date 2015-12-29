package step_implementations;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

import static common.GaugeProject.currentProject;
import static org.junit.Assert.*;

public class Console {

    @Step("Console should not contain following lines <table>")
    public void consoleShouldNotContainFollowingLines(Table table) throws IOException {
        String output = currentProject.getStdOut();
        boolean contains = false;
        String message = "\n";
        String console_output = table.getColumnNames().get(0);
        for (TableRow row : table.getTableRows()) {
            if (output.contains(row.getCell(console_output))) {
                contains = true;
                message += "Output contains :" + row.getCell(console_output) + "\n";
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
        int currentIndex = 0;

        for (int i = 0; i < table.getTableRows().size(); i++) {
            String colName = table.getColumnNames().get(0);
            String currentRow = table.getTableRows().get(i).getCell(colName);
            int rowIndex = output.indexOf(currentRow, currentIndex);

            if (rowIndex < 0) {
                String message = "Console doesn't contain " + currentRow + " in order\n" +
                        "Actual output: \n" + output;
                fail(message);
            }

            currentIndex = rowIndex + currentRow.length();
        }
    }

}
