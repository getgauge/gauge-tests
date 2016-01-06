package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Util;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.thoughtworks.gauge.test.common.Util.getTempDir;
import static org.junit.Assert.fail;

public class ProjectInit {
    private GaugeProject currentProject = null;

    @Step("In an empty directory initialize a <language> project")
    public void initializeProjectWithLanguage(String language) throws Exception {
        currentProject = GaugeProject.createProject(getTempDir(), language);
        currentProject.initialize();
    }

    @Step({"In an empty directory initialize a project with the current language"})
    public void initializeProject() throws Exception {
        String currentLanguage = Util.getCurrentLanguage();
        initializeProjectWithLanguage(currentLanguage);
    }

    @Step("Project is initialized without example spec")
    public void projectInitWithoutHelloWorldSpec() throws Exception {
        initializeProject();
        currentProject.deleteSpec("hello_world");
    }

    @Step("The following file structure should be created <table>")
    public void ensureInitCreatesSpecifiedStructure(Table table) throws Exception {
        List<String> failures = null;
        for (TableRow row : table.getTableRows()) {
            String fileName = row.getCell("name");
            String fileType = row.getCell("type");
            failures = verifyFileType(fileName, fileType);
        }

        doFail(failures);
    }

    private void doFail(List<String> failures) {
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
        List<String> failures = null;
        Map<String, String> files = currentProject.getLanguageSpecificFiles();
        for (String filePath : files.keySet()) {
            String fileType = files.get(filePath);
            failures = verifyFileType(filePath, fileType);
        }
        doFail(failures);
    }

    @AfterScenario
    public void clearProjectDir() throws IOException {
        if (currentProject.getProjectDir().exists()) {
            FileUtils.deleteQuietly(currentProject.getProjectDir());
        }
    }

    private List<String> verifyFileType(String filePath, String fileType) {
        List<String> failures = new ArrayList<>();
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
        return failures;
    }

    private String getPathRelativeToCurrentProjectDir(String path) {
        return Util.combinePath(currentProject.getProjectDir().getAbsolutePath(), path);
    }
}
