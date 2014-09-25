package common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static common.Util.capitalize;

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
        String className = getUniqueName();
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

    @Override
    public void createHook(String hookLevel, String hookType, String implementation) throws Exception {
        StringBuilder classText = new StringBuilder();
        classText.append(String.format("import com.thoughtworks.gauge.%s;\n",hookName(hookLevel, hookType)));
        String className = getUniqueName();
        classText.append("public class ").append(className).append("{\n");
        classText.append(createHookMethod(hookLevel, hookType, implementation));
        classText.append("\n}");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), className + ".java"), classText.toString());
    }

    private String createHookMethod(String hookLevel, String hookType, String implementation) {
        StringBuilder methodText = new StringBuilder();
        methodText.append(String.format("@%s\n", hookName(hookLevel, hookType)));
        methodText.append(String.format("public void hook() {\n"));
        methodText.append(String.format("System.out.println(\"%s\");\n", implementation));
        methodText.append("\n}\n");
        return methodText.toString();
    }

    private String hookName(String hookLevel, String hookType) {
        return String.format("%s%s", capitalize(hookType), capitalize(hookLevel));
    }

    private String getUniqueName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return String.format("Name%d%s", System.nanoTime(), dateFormat.format(new Date()));
    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "src/test/java").getAbsolutePath();
    }
}
