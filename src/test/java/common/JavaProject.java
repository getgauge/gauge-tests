package common;

import com.thoughtworks.gauge.AfterClassSteps;
import com.thoughtworks.gauge.BeforeClassSteps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static common.Util.capitalize;
import static common.Util.getUniqueName;

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
        implementation = getStepImplementation(stepValue, implementation, paramTypes);
        classText.append(") {\n").append(implementation).append("\n}\n");
        classText.append("}");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), className + ".java"), classText.toString());
    }

    @Override
    public void refactorStep(String oldStep, String newStep) throws IOException, InterruptedException {
        boolean exitStatus = currentProject.executeRefactor(oldStep, newStep);
        if (!exitStatus) {
            System.out.println(currentProject.getLastProcessStdout());
            System.out.println(currentProject.getLastProcessStderr());
        }
    }

    @Override
    public String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes) {
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
        } else if (implementation.toLowerCase().equals(THROW_EXCEPTION)) {
            return "throw new RuntimeException();";
        } else {

            builder.append("System.out.println(").append(implementation).append(");\n");
        }
        return builder.toString();
    }

    @Override
    public void createHookWithPrint(String hookLevel, String hookType, String printStatement) throws Exception {
        StringBuilder classText = new StringBuilder();
        classText.append(String.format("import com.thoughtworks.gauge.%s;\n", hookName(hookLevel, hookType)));
        String className = getUniqueName();
        classText.append("public class ").append(className).append("{\n");
        String implementation = String.format("System.out.println(\"%s\");", printStatement);
        classText.append(createHookMethod(hookLevel, hookType, implementation));
        classText.append("\n}");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), className + ".java"), classText.toString());
    }

    @Override
    public void createHookWithException(String hookLevel, String hookType) throws IOException {
        StringBuilder classText = new StringBuilder();
        classText.append(String.format("import com.thoughtworks.gauge.%s;\n", hookName(hookLevel, hookType)));
        String className = getUniqueName();
        classText.append("public class ").append(className).append("{\n");
        classText.append(createHookMethod(hookLevel, hookType, "throw new RuntimeException();"));
        classText.append("\n}");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), className + ".java"), classText.toString());
    }

    private String createHookMethod(String hookLevel, String hookType, String implementation) {
        StringBuilder methodText = new StringBuilder();
        methodText.append(String.format("@%s\n", hookName(hookLevel, hookType)));
        methodText.append(String.format("public void hook() {\n"));
        methodText.append(String.format("%s\n", implementation));
        methodText.append("\n}\n");
        return methodText.toString();
    }

    private String hookName(String hookLevel, String hookType) {
        return String.format("%s%s", capitalize(hookType), capitalize(hookLevel));
    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "src/test/java").getAbsolutePath();
    }
}
