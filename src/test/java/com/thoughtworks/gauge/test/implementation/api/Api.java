package com.thoughtworks.gauge.test.implementation.api;

import com.thoughtworks.gauge.*;
import com.thoughtworks.gauge.test.common.Assert;
import com.thoughtworks.gauge.test.common.AssertInfo;

import java.io.IOException;
import java.util.*;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.junit.Assert.*;

public class Api {

    private AssertInfo info;
    private HashSet<String> stepValues;
    private gauge.messages.Api.PerformRefactoringResponse refactorResponse;

    @Step("Start Gauge daemon")
    public void startGauge() throws IOException, InterruptedException {
        currentProject.createGaugeService();
    }

    @Step("Fetch all steps from gauge")
    public void fetchAllSteps() throws IOException {
        info = new StepsAssertInfo(100000, 500);
    }

    @Step("Fetch all concepts from gauge")
    public void fetchAllConcepts() {
        info = new ConceptsAssertInfo(100000, 500);
    }

    @Step("Verify all the steps are present <table>")
    public void verify(Table table) {
        List<String> steps = getSteps(table);
        info.setExpected(steps.toArray(new String[steps.size()]));
    }

    @Step("Verify all the steps are present <table> with default steps <steps>")
    public void verifySteps(Table table, Table defaultSteps) {
        List<String> stepTexts = new ArrayList<String>();
        List<String> columnNames = defaultSteps.getColumnNames();
        for (TableRow strings : defaultSteps.getTableRows()) {
            stepTexts.add(strings.getCell(columnNames.get(0)));
        }
        final List<String> steps = getSteps(table);
        String[] expected = steps.toArray(new String[steps.size() + stepTexts.size()]);
        System.arraycopy(stepTexts.toArray(), 0, expected, steps.size(), stepTexts.size());
        info.setExpected(expected);
        Assert.till(info);
    }

    @Step("fetch step values for the following <table>")
    public void fetchStepValues(Table table) {
        stepValues = new HashSet<>();
        List<String> columnNames = table.getColumnNames();
        for (TableRow row : table.getTableRows()) {
            StepValue stepValue = currentProject.getService().getGaugeConnection().getStepValue(row.getCell(columnNames.get(0)));
            stepValues.add(stepValue.getStepText());
        }
    }

    @Step("Verify all the step values are present <table>")
    public void verifyStepValues(Table table) {
        HashSet<Object> steps = new HashSet<>();
        List<String> columnNames = table.getColumnNames();
        for (TableRow row : table.getTableRows()) {
            List<String> parameters = row.getCell(columnNames.get(2)).equals("") ? new ArrayList<String>() : Arrays.asList(row.getCell(columnNames.get(2)).trim().split(","));

            StepValue e = new StepValue(row.getCell(columnNames.get(0)).trim(), row.getCell(columnNames.get(1)).trim(), parameters);
            steps.add(e.getStepText());
        }
        assertEquals(steps, stepValues);
    }

    @Step("Refactor step <old step> to <new step> via api")
    public void refactor(String oldStep, String newStep) throws Exception {
        refactorResponse = currentProject.getService().getGaugeConnection().sendPerformRefactoringRequest(oldStep, newStep);
        assertTrue("Refactoring resulted in error", refactorResponse.getSuccess());
    }

    @Step("verify refactoring didn't change files")
    public void verifyRefactoring() {
        assertEquals(0, refactorResponse.getFilesChangedList().size());
    }

    @AfterScenario
    public void tearDown() {
        if (currentProject.getService() != null)
            currentProject.getService().getGaugeProcess().destroy();
    }

    private List<String> getSteps(Table table) {
        List<String> columnNames = table.getColumnNames();
        final List<String> steps = new ArrayList<String>();
        for (TableRow row : table.getTableRows()) {
            StepValue stepValue = currentProject.getService().getGaugeConnection().getStepValue(row.getCell(columnNames.get(0)));
            if (stepValue != null && stepValue.getStepText() != null)
                steps.add(stepValue.getStepText().trim());
        }
        return steps;
    }
}
