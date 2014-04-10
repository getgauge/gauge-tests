import com.thoughtworks.twist2.Step;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;

public class ProjectInit {
    private File projectDir;

    private static final String[] PROJECT_SKELETAL_FILES = new String[]{
            "manifest.json",
            "env/default/default.json",
            "specs/hello_world.spec",
    };

    private static final String[] JAVA_PROJECT_SKELETAL_FILES = new String[]{
            "src/StepImplementation.java",
            "env/default/default.json"
    };

    Map<String, String[]> languageFilesMap = new HashMap<String, String[]>() {{
        put("common", PROJECT_SKELETAL_FILES);
        put("java", JAVA_PROJECT_SKELETAL_FILES);
    }};


    @Step("In an empty directory initialize a {} project")
    public void initializeProject(String language) throws Exception {
        File temp = File.createTempFile("tmp", "").getParentFile();
        projectDir = new File(temp, "java_project");
        projectDir.mkdir();

        new Twist2().initialize(projectDir, language);
    }

    @Step("Verify that skeletal files for {} are copied")
    public void verifySkeletalFilesCopied(String language) throws IOException {
        verifyFiles("common");
        verifyFiles(language);
    }

    @Step("cleanup project dir")
    public void cleanupProjectDir() throws IOException {
        FileUtils.deleteDirectory(projectDir);
    }

    private void verifyFiles(String group) {
        for (String fileName : languageFilesMap.get(group)) {
            File file = new File(projectDir, fileName);
            assertTrue(format("File %s doesn't exist", file.getAbsolutePath()), file.exists());
            assertTrue(format("File %s is empty", file.getAbsolutePath()), file.length() != 0);
        }
    }
}
