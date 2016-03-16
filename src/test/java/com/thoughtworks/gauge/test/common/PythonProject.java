package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PythonProject extends GaugeProject {
    private static final String DEFAULT_AGGREGATION = "AND";
    public static final String IMPORT = "from getgauge.python import step, after_step, before_step, after_scenario, before_scenario, after_spec, before_spec, after_suite, before_suite, Messages, DataStoreFactory\n";

    public PythonProject(File projectDir) {
        super(projectDir, "python");
    }

    @Override
    public void implementStep(String stepText, String implementation, boolean appendCode) throws Exception {
        List<String> paramTypes = new ArrayList<String>();
        StepValueExtractor.StepValue stepValue = new StepValueExtractor().getFor(stepText);
        String fileName = Util.getUniqueName();
        StringBuilder classText = new StringBuilder();
        classText.append(IMPORT);
        classText.append("@step(\"").append(stepValue.value).append("\")\n");
        classText.append("def ").append(Util.getUniqueName()).append("(");
        for (int i = 0; i < stepValue.paramCount; i++) {
            if (i + 1 == stepValue.paramCount) {
                classText.append("param").append(i);
            } else {
                classText.append("param").append(i).append(", ");
            }
            paramTypes.add("string");
        }
        implementation = getStepImplementation(stepValue, implementation, paramTypes, appendCode);
        classText.append("):\n").append(implementation).append("\n\n");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), fileName + ".py"), classText.toString());
    }

    @Override
    public Map<String, String> getLanguageSpecificFiles() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("step_impl", "dir");
        map.put("step_impl/step_impl.py", "file");
        return map;
    }

    @Override
    public String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes, boolean appendCode) {
        StringBuilder builder = new StringBuilder();
        if (implementation.toLowerCase().equals(PRINT_PARAMS)) {
            builder.append("    print ");
            for (int i = 0; i < stepValue.paramCount; i++) {
                if (paramTypes.get(i).toLowerCase().equals("string")) {
                    builder.append("\"param").append(i).append("=\"+").append("param").append(i);
                    if (i != stepValue.paramCount - 1) {
                        builder.append("+\",\"+");
                    }
                }
            }
            builder.append("\n");
        } else if (implementation.toLowerCase().equals(THROW_EXCEPTION)) {
            builder.append("    raise Exception('I do not know Python!')\n");
        } else {
            if (appendCode) {
                builder.append(implementation);
            } else {
                builder.append("    print ").append(implementation).append("\n");
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
        StringBuilder fileText = new StringBuilder();
        fileText.append(String.format(IMPORT + "@%s_%s\ndef %s():    raise Exception('I do not know Python!')\n", hookType, hookLevel, Util.getUniqueName()));
        fileText.append("\n");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), Util.getUniqueName() + ".py"), fileText.toString());
    }

    @Override
    public void createHooksWithTagsAndPrintMessage(String hookLevel, String hookType, String printString, String aggregation, Table tags) throws IOException {
        createHook(hookLevel, hookType, printString, aggregation, tags.getColumnValues("tags"));
    }

    public void createHook(String hookLevel, String hookType, String printString, String aggregation, List<String> tags) throws IOException {
        StringBuilder fileText = new StringBuilder();
        if (!isSuiteHook(hookLevel))
            fileText.append(String.format(IMPORT + "@%s_%s%s\ndef %s():\n    print \"%s\"\n", hookType, hookLevel, getOptions(aggregation, tags), Util.getUniqueName(), printString));
        else
            fileText.append(String.format(IMPORT + "@%s_%s\ndef %s():\n    print \"%s\"\n", hookType, hookLevel, Util.getUniqueName(), printString));
        fileText.append("\n");
        Util.writeToFile(Util.combinePath(getStepImplementationsDir(), Util.getUniqueName() + ".py"), fileText.toString());
    }

    @Override
    public void refactorStep(String oldStep, String newStep) throws IOException, InterruptedException {
        boolean exitStatus = currentProject.executeRefactor(oldStep, newStep);
        if (!exitStatus) {
            System.out.println(currentProject.getLastProcessStdout());
        }
    }

    @Override
    public String getDataStoreWriteStatement(TableRow row, List<String> columnNames) {
        String dataStoreType = row.getCell(columnNames.get(3));
        String key = row.getCell(columnNames.get(1));
        String value = row.getCell(columnNames.get(2));
        return "    print DataStoreFactory." + dataStoreType.toLowerCase() + "_data_store().put(\"" + key + "\", \"" + value + "\")";
    }

    private String getOptions(String aggregation, List<String> tags) {
        return tags.size() < 1 ? "" : "('<" + StringUtils.join(tags, "> " + aggregation.toLowerCase() + " <") + ">')";
    }

    @Override
    public String getDataStorePrintValueStatement(TableRow row, List<String> columnNames) {
        String dataStoreType = row.getCell(columnNames.get(3));
        String key = row.getCell(columnNames.get(1));
        return "    print DataStoreFactory." + dataStoreType.toLowerCase() + "_data_store().get(\"" + key + "\")";
    }

    private String getStepImplementationsDir() {
        return new File(getProjectDir(), "step_impl").getAbsolutePath();
    }

    private boolean isSuiteHook(String hookLevel) {
        return hookLevel.trim().equals("suite");
    }
}
