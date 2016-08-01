package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.builders.DataFileBuilder;
import com.thoughtworks.gauge.test.common.builders.ProjectBuilder;
import com.thoughtworks.gauge.test.common.Util;
import com.thoughtworks.gauge.test.common.builders.SpecificationBuilder;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import java.io.File;
import java.util.Map;

public class ProjectInit {

    private GaugeProject currentProject = null;

    @Step("In an empty directory, use default initialization of a project named <projName> in language <language>")
    public void initializeProjectWithLanguage(String projName, String language) throws Exception {
        currentProject = new ProjectBuilder()
                .withLangauge(language)
                .withProjectName(projName)
                .build(language.equals("unknown"));
    }

    @Step({"In an empty directory, use default initialization of a project named <projName> with the current language"})
    public void defaultInitializationProject(String projName) throws Exception {
        currentProject = new ProjectBuilder()
                .withLangauge(Util.getCurrentLanguage())
                .withProjectName(projName)
                .build(false);
    }

    @Step({"In an empty directory initialize a project named <projName> with the current language"})
    public void initializeProject(String projName) throws Exception {
        currentProject = new ProjectBuilder()
                .withLangauge(Util.getCurrentLanguage())
                .withProjectName(projName)
                .withoutExampleSpec()
                .build(false);
    }

    @Step("In an empty directory initialize a project named <projName> without example spec")
    public void projectInitWithoutHelloWorldSpec(String projName) throws Exception {
        currentProject = new ProjectBuilder()
                .withLangauge(Util.getCurrentLanguage())
                .withProjectName(projName)
                .withoutExampleSpec()
                .build(false);
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

    @Step("Create a csv file <aCsvFileName>")
    public void createACsv(String name) throws Exception {
        new DataFileBuilder()
                .withCsvFile(name)
                .build();
    }

    @Step("Create a txt file <aTxtFileName>")
    public void createAText(String name) throws Exception {
        new DataFileBuilder()
                .withTextFile(name)
                .build();

    }
}