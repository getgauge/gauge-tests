package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Util;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.SoftAssertions;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.thoughtworks.gauge.test.common.Util.getTempDir;

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
        currentProject.deleteSpec("example");
    }

    @Step("The following file structure should be created <table>")
    public void ensureInitCreatesSpecifiedStructure(Table table) throws Exception {
        SoftAssertions softly = new SoftAssertions();
        for (TableRow row : table.getTableRows()) {
            File fileName = new File(getPathRelativeToCurrentProjectDir(row.getCell("name")));
            String fileType = row.getCell("type").toLowerCase();

            softly.assertThat(fileType).isIn("dir", "file");
            if (fileType.equals("dir")) {
                softly.assertThat(fileName).exists().isDirectory();
            } else if (fileType.equals("file")) {
                softly.assertThat(fileName).exists().isFile();
            }
        }
        softly.assertAll();
    }

    @Step("Verify language specific files are created")
    public void verifyFilesForLanguageIsCreated() {
        SoftAssertions softly = new SoftAssertions();

        Map<String, String> files = currentProject.getLanguageSpecificFiles();
        files.forEach((k,v ) -> {
            File fileName = new File(getPathRelativeToCurrentProjectDir(k));
            String fileType = v.toLowerCase();

            softly.assertThat(fileType).isIn("dir", "file");
            if (fileType.equals("dir")) {
                softly.assertThat(fileName).exists().isDirectory();
            } else if (fileType.equals("file")) {
                softly.assertThat(fileName).exists().isFile();
            }
        });
        softly.assertAll();
    }

    @AfterScenario
    public void clearProjectDir() throws IOException {
        if (currentProject.getProjectDir().exists()) {
            FileUtils.deleteQuietly(currentProject.getProjectDir());
        }
    }

    private String getPathRelativeToCurrentProjectDir(String path) {
        return Util.combinePath(currentProject.getProjectDir().getAbsolutePath(), path);
    }
}
