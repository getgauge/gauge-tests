package common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.Util.capitalize;
import static common.Util.getUniqueName;

public class CSharpProject extends GaugeProject {
    public CSharpProject(File projectDir) {
        super(projectDir, "csharp");
    }

    public Map<String, String> getLanguageSpecificFiles() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("StepImplementation.cs", "file");
        return map;
    }

    public void implementStep(String stepText, String implementation) throws Exception {
        List<String> paramTypes = new ArrayList<String>();
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getFor(stepText);
        String className = getUniqueName();
        StringBuilder classText = new StringBuilder();
        classText.append("public class ").append(className).append("\n{\n");
        classText.append("[Step(\"").append(stepValue.value).append("\")]\n");
        classText.append("public void ").append("stepImplementation(");
        for (int i = 0; i < stepValue.paramCount; i++) {
            if (i + 1 == stepValue.paramCount) {
                classText.append("string param").append(i);
            } else {
                classText.append("string param").append(i).append(", ");
            }
            paramTypes.add("string");
        }
        implementation = getStepImplementation(stepValue, implementation, paramTypes);
        classText.append(")\n{\n").append(implementation).append("\n}\n");
        classText.append("}\n");
        this.addImplementationFile(Util.combinePath(getStepImplementationsDir(), "StepImplementation.cs"));
        Util.appendToFile(Util.combinePath(getStepImplementationsDir(), "StepImplementation.cs"), classText.toString());
    }

    @Override
    public String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes) {
        StringBuilder builder = new StringBuilder();
        if (implementation.toLowerCase().equals(PRINT_PARAMS)) {
            builder.append("Console.WriteLine(");
            for (int i = 0; i < stepValue.paramCount; i++) {
                if (paramTypes.get(i).toLowerCase().equals("string")) {
                    builder.append("\"param").append(i).append("=\"+").append("param").append(i);
                    if (i != stepValue.paramCount - 1) {
                        builder.append("+\",\"+");
                    }
                }
            }
            builder.append(");\n");
        }else if(implementation.toLowerCase().equals(THROW_EXCEPTION)){
            return "throw new SystemException();";
        }
        else {

            builder.append("Console.WriteLine(").append(implementation).append(");\n");
        }
        return builder.toString();
    }

    @Override
    public void createHookWithPrint(String hookLevel, String hookType, String printStatement) throws Exception {
        StringBuilder classText = new StringBuilder();
        String className = getUniqueName();
        classText.append("public class ").append(className).append("{\n");
        String implementation = String.format("Console.WriteLine(\"%s\");", printStatement);
        classText.append(createHookMethod(hookLevel, hookType, implementation));
        classText.append("\n}\n");
        Util.appendToFile(Util.combinePath(getStepImplementationsDir(), "StepImplementation.cs"), classText.toString());
    }

    @Override
    public void createHookWithException(String hookLevel, String hookType) throws IOException {
        StringBuilder classText = new StringBuilder();
        String className = getUniqueName();
        classText.append("public class ").append(className).append("{\n");
        classText.append(createHookMethod(hookLevel, hookType, "throw new SystemException();"));
        classText.append("\n}\n");
        Util.appendToFile(Util.combinePath(getStepImplementationsDir(), "StepImplementation.cs"), classText.toString());
    }

    @Override
    public void refactorStep(String oldStep, String newStep) throws IOException, InterruptedException {
        boolean exitStatus = currentProject.executeRefactor(oldStep, newStep);
        if (!exitStatus){
            System.out.println(currentProject.getLastProcessStdout());
            System.out.println(currentProject.getLastProcessStderr());
        }
    }

    @Override
    public boolean isStepPresentInImpl(String oldStep, Integer paramCount, String implText) {
        return true;
    }

    private String createHookMethod(String hookLevel, String hookType, String implementation) {
        StringBuilder methodText = new StringBuilder();
        String hookName = hookName(hookLevel, hookType);
        methodText.append(String.format("[%s]\n", hookName));
        methodText.append(String.format("public void %s() {\n", hookName));
        methodText.append(String.format("%s\n", implementation));
        methodText.append("\n}\n");
        return methodText.toString();
    }

    private String hookName(String hookLevel, String hookType) {
        return String.format("%s%s", capitalize(hookType), capitalize(hookLevel));
    }

    private String getStepImplementationsDir() {
        return getProjectDir().getAbsolutePath();
    }
}
