package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UnknownProject extends GaugeProject {
    public UnknownProject(String language, String projName) throws IOException {
        super(language, projName);
    }

    @Override
    public void implementStep(String stepText, String implementation, boolean continueOnFailure, boolean appendCode) throws Exception {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Map<String, String> getLanguageSpecificFiles() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getStepImplementation(StepValueExtractor.StepValue stepValue, String implementation, List<String> paramTypes, boolean appendCode) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void createHookWithPrint(String hookLevel, String hookType, String implementation) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void createHookWithException(String hookLevel, String hookType) throws IOException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void createHooksWithTagsAndPrintMessage(String hookLevel, String hookType, String printString, String aggregation, Table tags) throws IOException {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void refactorStep(String oldStep, String newStep) throws IOException, InterruptedException {

    }

    @Override
    public String getDataStoreWriteStatement(TableRow row, List<String> columnNames) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String getDataStorePrintValueStatement(TableRow row, List<String> columnNames) {
        throw new RuntimeException("Not implemented");
    }
}
