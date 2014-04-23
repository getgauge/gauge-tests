package step_implementations;

import com.thoughtworks.twist2.AfterScenario;
import com.thoughtworks.twist2.Step;
import com.thoughtworks.twist2.Table;
import common.Twist2;
import common.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProjectInit {
    private File projectDir;
    private Process initializeProcess;

    @Step("In an empty directory initialize a <language> project")
    public void initializeProject(String language) throws Exception {
        this.projectDir = getTempDir();
        initializeProcess = new Twist2().initialize(projectDir, language);
    }

    private File getTempDir() throws IOException {
        File temp = File.createTempFile("tmp", "").getParentFile();
        String projectDirName = "project_" + System.nanoTime();
        File projectDir = new File(temp, projectDirName);
        projectDir.mkdir();
        return projectDir;
    }

    @Step("The following file structure should be created")
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
        return Util.combinePath(this.projectDir.getAbsolutePath(), path);
    }

    @AfterScenario
    public void cleanupProjectDir() throws IOException {
        FileUtils.deleteDirectory(projectDir);
    }

    @Step("Console should contain <message>")
    public void consoleShouldContain(String message) throws IOException {
        String output = IOUtils.toString(initializeProcess.getInputStream());
        String expectedErrorMessage = String.format("%s\n", message);
        assertEquals(expectedErrorMessage, output);
    }
}
