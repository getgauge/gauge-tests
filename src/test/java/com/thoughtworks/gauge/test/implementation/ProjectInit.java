package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Util;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;

import java.io.File;
import java.util.Map;

public class ProjectInit {

    private GaugeProject currentProject = null;

    @Step("In an empty directory initialize a <language> project named <projName>")
    public void initializeProjectWithLanguage(String language, String projName) throws Exception {
        currentProject = GaugeProject.createProject(language, projName);
        currentProject.initialize();
    }

    @Step({"In an empty directory initialize a project named <projName> with the current language"})
    public void initializeProject(String projName) throws Exception {
        String currentLanguage = Util.getCurrentLanguage();
        initializeProjectWithLanguage(currentLanguage, projName);
    }

    @Step("In an empty directory initialize a project named <projName> without example spec")
    public void projectInitWithoutHelloWorldSpec(String projName) throws Exception {
        initializeProject(projName);
        currentProject.deleteSpec("specs/example");
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
        files.forEach((k, v) -> {
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

    private String getPathRelativeToCurrentProjectDir(String path) {
        return Util.combinePath(currentProject.getProjectDir().getAbsolutePath(), path);
    }

    @Step("Directory <init_proj_unknown> should be empty")
    public void verifyEmptyDir(String dirName) {
        File[] files = currentProject.getProjectDir().listFiles();
        Assert.assertNotNull(files);
        Assert.assertEquals(files.length, 0);
    }
}