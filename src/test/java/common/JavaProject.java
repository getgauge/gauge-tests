package common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        List<String> paramTypes = new ArrayList<String>();
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
            paramTypes.add("String");
        }
        implementation = getStepImplemetation(stepValue, implementation, paramTypes);
        classText.append(") {\n").append(implementation).append("\n}");
        classText.append("}");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), className + ".java"), classText.toString());
    }

    @Override
    public String getStepImplemetation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes) {
        StringBuilder builder = new StringBuilder();
        if (implementation.toLowerCase().equals(PRINT_PARAMS)) {
            builder.append("System.out.println(");
            for (int i = 0; i < stepValue.paramCount; i++) {
                if (paramTypes.get(i).toLowerCase().equals("string")) {
                    builder.append("\"param").append(i).append("=\"+").append("param").append(i);
                    if (i != stepValue.paramCount - 1) {
                        builder.append("+\",\"+");
                    }
                }
            }
            builder.append(");\n");
        } else {
            builder.append("System.out.println(").append(implementation).append(");\n");
        }
        return builder.toString();
    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "src/test/java").getAbsolutePath();
    }
}
