package step_implementations;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import common.GaugeProject;
import common.Util;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static common.Util.getTempDir;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ProjectInit {
    private GaugeProject currentProject = null;

    @Step("In an empty directory initialize a <language> project")
    public void initializeProjectWithLanguage(String language) throws Exception {
        currentProject = GaugeProject.createProject(getTempDir(), language);
        currentProject.initialize();
    }

    @Step("In an empty directory initialize a project with the current language")
    public void initializeProject() throws Exception {
        String currentLanguage = Util.getCurrentLanguage();
        initializeProjectWithLanguage(currentLanguage);
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

    @Step("Verify language specific files are created")
    public void verifyFilesForLanguageIsCreated() {
        ArrayList<String> failures = new ArrayList<String>();
        Map<String, String> files = currentProject.getLanguageSpecificFiles();
        for (String filePath : files.keySet()) {
            String fileType = files.get(filePath);
            if (fileType.equalsIgnoreCase("dir")) {
                if (!Util.isDirectoryExists(getPathRelativeToCurrentProjectDir(filePath))) {
                    failures.add(filePath + " is not a valid directory");
                }
            } else if (fileType.equalsIgnoreCase("file")) {
                if (!Util.isFileExists(getPathRelativeToCurrentProjectDir(filePath))) {
                    failures.add(filePath + " is not a valid file");
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

    @Step("Console should contain following lines <console output table>")
    public void consoleShouldContainFollowingLines(Table table) throws IOException {
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
            if (!(output.indexOf(row1) < output.indexOf(row2))){
                String message = "Output was not in order\n";
                message += "******************Actual Console Output Start************\n";
                message += outputCopy;
                message += "******************Actual Console Output End************\n";
                fail(message);
            }
        }
    }

    @AfterScenario
    public void clearProjectDir() throws IOException {
        if (currentProject.getProjectDir().exists()) {
            FileUtils.deleteQuietly(currentProject.getProjectDir());
        }
    }

    @Step("Console should not contain following lines <table>")
    public void ConsoleShouldNotContainFollowingLines(Table table) throws IOException {
        String output = currentProject.getStdOut();
        boolean contains = false;
        String message = "\n";
        for(List<String> row : table.getRows()){
            if(output.contains(row.get(0))) {
                contains = true;
                message+="Output contains :"+row.get(0)+"\n";
            }
        }
        if(contains)
            fail(message);
    }
}
