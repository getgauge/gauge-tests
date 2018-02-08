package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.StepImpl;

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
        char c = input.charAt(0);
        String s = "" + c;
        String f = s.toUpperCase();
        return f + input.substring(1);
    }

    private StringBuilder createStepTeplate(ArrayList<String> stepTexts) {
        StringBuilder step = new StringBuilder();
        if(stepTexts.size()==1){
            return step.append("gauge.step(\"").append(stepTexts.get(0)).append("\",");
        }
        else {
            StringBuilder commaSeparated = new StringBuilder();
            for(String stepText:stepTexts){
                commaSeparated.append("\"").append(stepText).append("\",");
            }
            return step.append("gauge.step(").append(commaSeparated).append(",");
        }
    }

    @Override
    public void implementStep(StepImpl stepImpl) throws Exception {
        List<String> paramTypes = new ArrayList<String>();
        StepValueExtractor stepValueExtractor = new StepValueExtractor();
        StepValueExtractor.StepValue stepValue = stepValueExtractor.getFor(stepImpl.getFirstStepText());
        String fileName = Util.getUniqueName();
        StringBuilder jsCode = new StringBuilder(createStepTeplate(stepValueExtractor.getValueFor(stepImpl.getAllStepTexts())));
        if (stepImpl.isContinueOnFailure()) {
            jsCode.append(" { continueOnFailure: true },");
        }
        jsCode.append(" function (");
        for (int i = 0; i < stepValue.paramCount; i++) {
            jsCode.append("param").append(i).append(", ");
            paramTypes.add("string");
        }
        jsCode.append("done) {\n");
        jsCode.append(getStepImplementation(stepValue, stepImpl.getImplementation(), paramTypes, stepImpl.isValidStatement()));
        jsCode.append("\ndone();");
        jsCode.append("\n});");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), fileName + ".js"), jsCode.toString());
    }

    @Override
    public Map<String, String> getLanguageSpecificFiles() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("tests", "dir");
        map.put(Util.combinePath("tests", "step_implementation.js"), "file");
        map.put(Util.combinePath("env", "default", "js.properties"), "file");
        return map;
    }

    @Override
    public String getLanguageSpecificGitIgnoreText() {
        return "# Gauge - metadata dir\n" +
                ".gauge\n" +
                "\n" +
                "# Gauge - log files dir\n" +
                "logs\n" +
                "\n" +
                "# Gauge - reports dir\n" +
                "reports\n" +
                "\n" +
                "# Gauge - JavaScript node dependencies\n" +
                "node_modules\n\n";
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
        } else if (implementation.toLowerCase().startsWith("throw")) {
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
        String dataStoreType = row.getCell("datastore type");
        String key = row.getCell("key");
        String value = row.getCell("value");
        return "gauge.dataStore." + dataStoreType.toLowerCase() + "Store.put(\"" + key + "\", \"" + value + "\");";
    }

    private String getOptions(String aggregation, List<String> tags) {
        String tagsText = Util.joinList(Util.quotifyValues(tags));
        return String.format("{tags: [%s], operator: \"%s\"}", tagsText, aggregation);
    }

    @Override
    public String getDataStorePrintValueStatement(TableRow row, List<String> columnNames) {
        String dataStoreType = row.getCell("datastore type");
        String key = row.getCell("key");
        return "console.log(gauge.dataStore." + dataStoreType.toLowerCase() + "Store.get(\"" + key + "\"));";
    }

    @Override
    public void configureCustomScreengrabber(String stubScreenshot) throws IOException {

    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "tests").getAbsolutePath();
    }
}
