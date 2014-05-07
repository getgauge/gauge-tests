package step_implementations;

import com.thoughtworks.twist2.Step;
import com.thoughtworks.twist2.Table;
import common.GaugeProject;
import common.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.Util.getTempDir;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ProjectInit {
    private GaugeProject currentProject = null;

    @Step("In an empty directory initialize a <language> project")
    public void initializeProject(String language) throws Exception {
        currentProject = new GaugeProject(getTempDir(), language);
        currentProject.initialize();
    }

    @Step("The following file structure should be created <table>")
    public void ensureInitCreatesSpecifiedStructure(Table table) throws Exception {
        ArrayList<String> failures = new ArrayList<String>();
        for (List<String> row : table.getRows()) {
            String fileName = row.get(0);
            String fileType = row.get(1);
            if (fileType.equalsIgnoreCase("dir")) {
                if (!Util.isDirectoryExists(getPathRelativeToCurrentProjectDir(fileName))) {
                    failures.add(fileName + " is not a valid directory");
                }
            } else if (fileType.equalsIgnoreCase("file")) {
                if (!Util.isFileExists(getPathRelativeToCurrentProjectDir(fileName))) {
                    failures.add(fileName + " is not a valid file");
                }
            } else {
                fail(fileType + " is invalid");
            }
        }

        if (failures.size() > 0) {
            String consolidatedMessage = "";
            for (String failure : failures) {
                consolidatedMessage += failure + "\n";
            }
            fail("Project initialization failed to create required project structure.\n\n" + consolidatedMessage);
        }
    }

    private String getPathRelativeToCurrentProjectDir(String path) {
        return Util.combinePath(currentProject.getProjectDir().getAbsolutePath(), path);
    }

    @Step("Console should contain <message>")
    public void consoleShouldContain(String message) throws IOException {
        String output = currentProject.getStdOut();
        assertTrue("Console don't contain '" + message + "'", output.contains(message));
    }

    @Step("Console should contain following lines in order <console output table>")
    public void consoleShouldContainFollowingLinesInOrder(Table table) throws IOException {
        String output = currentProject.getStdOut();
        String row1, row2;
        for (int i = 0; i < table.getRows().size() - 1; i++) {
            row1 = table.getRows().get(i).get(0);
            row2 = table.getRows().get(i + 1).get(0);
            if (!output.contains(row1))
                fail("Console doesn't contain " + row1);
            if (!output.contains(row2))
                fail("Console doesn't contain " + row2);
            if (output.indexOf(row1) < output.indexOf(row2))
                output = output.replaceFirst(row1, "");
            else
                fail("Output was not in order");
        }
    }
}
