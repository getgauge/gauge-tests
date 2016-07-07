package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavascriptProject extends GaugeProject {
    private static final String DEFAULT_AGGREGATION = "AND";

    public JavascriptProject(String projName) throws IOException {
        super("js", projName);
    }

    public static String toTitleCase(String input) {
        input = input.toLowerCase();
        char c =  input.charAt(0);
        String s = new String("" + c);
        String f = s.toUpperCase();
        return f + input.substring(1);
    }

    @Override
    public void implementStep(String stepText, String implementation, boolean continueOnFailure, boolean appendCode) throws Exception {
        List<String> paramTypes = new ArrayList<String>();
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getFor(stepText);
        String fileName = Util.getUniqueName();
        StringBuilder jsCode = new StringBuilder();
        jsCode.append("gauge.step(\"").append(stepValue.value).append("\", function (");
        for (int i = 0; i < stepValue.paramCount; i++) {
            jsCode.append("param").append(i).append(", ");
            paramTypes.add("string");
        }
        jsCode.append("done) {\n");
        jsCode.append(getStepImplementation(stepValue, implementation, paramTypes, appendCode));
        jsCode.append("\ndone();");
        jsCode.append("\n});");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), fileName + ".js"), jsCode.toString());
    }

    @Override
    public Map<String, String> getLanguageSpecificFiles() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("tests", "dir");
        map.put("tests/step_implementation.js", "file");
        map.put("env/default/js.properties", "file");
        return map;
    }

    @Override
    public String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes, boolean appendCode) {
        StringBuilder builder = new StringBuilder();
        if (implementation.toLowerCase().equals(PRINT_PARAMS)) {
            builder.append("  console.log(");
            for (int i = 0; i < stepValue.paramCount; i++) {
                if (paramTypes.get(i).toLowerCase().equals("string")) {
                    builder.append(String.format("\"param%d=\"+param%d", i, i));
                    if (i != stepValue.paramCount - 1) {
                        builder.append("+\",\"+");
                    }
                }
            }
            builder.append(");\n");
        } else if (implementation.toLowerCase().equals(THROW_EXCEPTION)) {
            builder.append("  throw new Error(\"exception raised\");\n\n");
        } else {
            if (appendCode) {
                builder.append(implementation);
            } else {
                builder.append("  console.log(").append(implementation).append(");\n");
            }
        }
        return builder.toString();
    }

    @Override
    public void createHookWithPrint(String hookLevel, String hookType, String printString) throws IOException {
        createHook(hookLevel, hookType, printString, DEFAULT_AGGREGATION, new ArrayList<String>());
    }

    @Override
    public void createHookWithException(String hookLevel, String hookType) throws IOException {
        StringBuilder jsFileText = new StringBuilder();
        jsFileText.append(String.format("gauge.hooks.%s%s(", hookType.toLowerCase(), toTitleCase(hookLevel)));
        jsFileText.append("function () {\nthrow new Error(\"exception was raised\");\n}");
        jsFileText.append(");\n");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), Util.getUniqueName() + ".js"), jsFileText.toString());
    }

    @Override
    public void createHooksWithTagsAndPrintMessage(String hookLevel, String hookType, String printString, String aggregation, Table tags) throws IOException {
        createHook(hookLevel, hookType, printString, aggregation, tags.getColumnValues("tags"));
    }

    public void createHook(String hookLevel, String hookType, String printString, String aggregation, List<String> tags) throws IOException {
        StringBuilder jsFileText = new StringBuilder();
        jsFileText.append(String.format("gauge.hooks.%s%s(function () {", hookType.toLowerCase(), toTitleCase(hookLevel)));
        jsFileText.append(String.format("  console.log(\"%s\");\n", printString));
        jsFileText.append(String.format("}, %s);\n", getOptions(aggregation, tags)));
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), Util.getUniqueName() + ".js"), jsFileText.toString());
    }

    @Override
    public String getDataStoreWriteStatement(TableRow row, List<String> columnNames) {
        String dataStoreType = row.getCell(columnNames.get(3));
        String key = row.getCell(columnNames.get(1));
        String value = row.getCell(columnNames.get(2));
        return "gauge.dataStore." + dataStoreType.toLowerCase() + "Store.put(\"" + key + "\", \"" + value + "\");";
    }

    private String getOptions(String aggregation, List<String> tags) {
        String tagsText = Util.joinList(Util.quotifyValues(tags));
        return String.format("{tags: [%s], operator: \"%s\"}", tagsText, aggregation);
    }

    @Override
    public String getDataStorePrintValueStatement(TableRow row, List<String> columnNames) {
        String dataStoreType = row.getCell(columnNames.get(3));
        String key = row.getCell(columnNames.get(1));
        return "console.log(gauge.dataStore." + dataStoreType.toLowerCase() + "Store.get(\"" + key + "\"));";
    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "tests").getAbsolutePath();
    }
}
