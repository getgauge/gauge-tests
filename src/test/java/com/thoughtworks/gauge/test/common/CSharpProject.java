package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSharpProject extends GaugeProject {
    private static final String DEFAULT_AGGREGATION = "And";

    public CSharpProject(String projName) throws IOException {
        super("csharp", projName);
    }

    public Map<String, String> getLanguageSpecificFiles() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("StepImplementation.cs", "file");
        return map;
    }

    @Override
    public void implementStep(String stepText, String implementation, boolean continueOnFailure, boolean appendCode) throws Exception {
        List<String> paramTypes = new ArrayList<String>();
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getFor(stepText);
        String className = Util.getUniqueName();
        StringBuilder classText = new StringBuilder();
        classText.append("public class ").append(className).append("\n{\n");
        classText.append("[Step(\"").append(stepValue.value).append("\")]\n");
        if(continueOnFailure) {
            classText.append("[ContinueOnFailure]\n");
        }
        classText.append("public void ").append("stepImplementation(");
        for (int i = 0; i < stepValue.paramCount; i++) {
            if (i + 1 == stepValue.paramCount) {
                classText.append("string param").append(i);
            } else {
                classText.append("string param").append(i).append(", ");
            }
            paramTypes.add("string");
        }
        implementation = getStepImplementation(stepValue, implementation, paramTypes, appendCode);
        classText.append(")\n{\n").append(implementation).append("\n}\n");
        classText.append("}\n");
        Util.appendToFile(Util.combinePath(getStepImplementationsDir(), "StepImplementation.cs"), classText.toString());
    }

    @Override
    public String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes, boolean appendCode) {
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
        } else if (implementation.toLowerCase().equals(THROW_EXCEPTION)) {
            return "throw new SystemException();";
        } else {
            if (appendCode) {
                builder.append(implementation);
            } else {
                builder.append("Console.WriteLine(").append(implementation).append(");\n");
            }
        }
        return builder.toString();
    }

    @Override
    public void createHookWithPrint(String hookLevel, String hookType, String printStatement) throws Exception {
        String implementation = String.format("Console.WriteLine(\"%s\");", printStatement);
        String method = createHookMethod(hookLevel, hookType, implementation);
        createHook(method);
    }

    @Override
    public void createHookWithException(String hookLevel, String hookType) throws IOException {
        String method = createHookMethod(hookLevel, hookType, "throw new SystemException();");
        createHook(method);
    }

    @Override
    public void createHooksWithTagsAndPrintMessage(String hookLevel, String hookType, String printString, String aggregation, Table tags) throws IOException {
        String implementation = String.format("Console.WriteLine(\"%s\");", printString);
        String method = createHookMethod(hookLevel, hookType, implementation, tags.getColumnValues("tags"), aggregation);
        createHook(method);
    }


    private String createHookMethod(String hookLevel, String hookType, String implementation) {
        return createHookMethod(hookLevel, hookType, implementation, new ArrayList<String>(), DEFAULT_AGGREGATION);
    }

    private String createHookMethod(String hookLevel, String hookType, String implementation, List<String> tags, String aggregation) {
        StringBuilder methodText = new StringBuilder();
        String hookString = hookString(hookLevel, hookType, tags);
        methodText.append(hookString).append("\n");
        methodText.append(aggregationAttribute(aggregation));
        methodText.append(String.format("public void %s() {\n", Util.getUniqueName()));
        methodText.append(String.format("%s\n", implementation));
        methodText.append("\n}\n");
        return methodText.toString();
    }

    private String aggregationAttribute(String aggregation) {
        if (aggregation.equals("AND") || aggregation.equals(DEFAULT_AGGREGATION)) {
            return String.format("[TagAggregationBehaviour(TagAggregation.%s)]\n", DEFAULT_AGGREGATION);
        }
        return String.format("[TagAggregationBehaviour(TagAggregation.%s)]\n", "Or");
    }

    private void createHook(String method) throws IOException {
        StringBuilder classText = new StringBuilder();
        String className = Util.getUniqueName();
        classText.append("public class ").append(className).append("{\n");
        classText.append(method);
        classText.append("\n}\n");
        Util.appendToFile(Util.combinePath(getStepImplementationsDir(), "StepImplementation.cs"), classText.toString());
    }

    @Override
    public String getDataStoreWriteStatement(TableRow row, List<String> columnNames) {
        String dataStoreType = row.getCell(columnNames.get(3));
        String key = row.getCell(columnNames.get(1));
        String value = row.getCell(columnNames.get(2));
        return "DataStoreFactory." + dataStoreType + "DataStore.Add(\"" + key + "\",\"" + value + "\");";
    }

    @Override
    public String getDataStorePrintValueStatement(TableRow row, List<String> columnNames) {
        String dataStoreType = row.getCell(columnNames.get(3));
        String key = row.getCell(columnNames.get(1));
        return "Console.WriteLine(DataStoreFactory." + dataStoreType + "DataStore.Get(\"" + key + "\"));";
    }

    @Override
    public void configureCustomScreengrabber(String stubScreenshot) throws IOException {

    }

    private String hookString(String hookLevel, String hookType, List<String> tags) {
        String tagsText = isSuiteLevel(hookLevel) ? "" : Util.joinList(Util.quotifyValues(tags));
        return String.format("[%s(%s)]", hookName(hookLevel, hookType), tagsText);
    }

    private String hookName(String hookLevel, String hookType) {
        return String.format("%s%s", Util.capitalize(hookType), Util.capitalize(hookLevel));
    }

    private boolean isSuiteLevel(String hookLevel) {
        return hookLevel.trim().equals("suite");
    }

    private String getStepImplementationsDir() {
        return getProjectDir().getAbsolutePath();
    }
}
