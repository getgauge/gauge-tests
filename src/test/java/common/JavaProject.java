package common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JavaProject extends GaugeProject {
    private static String stepImplementationsDir = "src/test/java";

    public JavaProject(File projectDir) {
        super(projectDir, "java");
    }

    public Map<String, String> getLanguageSpecificFiles() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("src", "dir");
        map.put("libs", "dir");
        map.put("src/test/java/StepImplementation.java", "file");
        map.put("env/default/java.properties", "file");
        return map;
    }

    public void implementStep(String stepText, String implementation) throws Exception {
        implementation = String.format("System.out.println(%s);\n", implementation);
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getFor(stepText);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String className = String.format("Steps%d%s", System.nanoTime(), dateFormat.format(new Date()));
        StringBuilder classText = new StringBuilder();
        classText.append("import com.thoughtworks.gauge.Step;\n");
        classText.append("public class ").append(className).append("{\n");
        classText.append("@Step(\"").append(stepValue.value).append("\")\n");
        classText.append("public void ").append("stepImplementation(");
        for (int i = 0; i < stepValue.paramCount; i++) {
            if (i + 1 == stepValue.paramCount) {
                classText.append("String param").append(i);
            } else {
                classText.append("String param").append(i).append(", ");
            }

        }
        classText.append(") {\n").append(implementation).append("\n}");
        classText.append("}");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), className + ".java"), classText.toString());
    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "src/test/java").getAbsolutePath();
    }
}
