package step_implementations;

import com.thoughtworks.twist2.Step;
import com.thoughtworks.twist2.Table;
import common.GaugeProject;
import common.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.Util.getTempDir;
import static org.junit.Assert.assertEquals;
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
}
