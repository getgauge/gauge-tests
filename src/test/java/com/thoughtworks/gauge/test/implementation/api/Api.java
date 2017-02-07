package com.thoughtworks.gauge.test.implementation.api;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.connection.GaugeConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;
import static org.assertj.core.api.Assertions.assertThat;

public class Api {

    private List<String> stepValues;
    private gauge.messages.Api.PerformRefactoringResponse refactorResponse;
    private List<String> allSteps;
    private List<String> allConcepts;

    @Step("Start Gauge daemon")
    public void startGauge() throws IOException, InterruptedException {
        currentProject.createGaugeService();
    }

    @Step("Fetch all steps from gauge")
    public void fetchAllSteps() throws IOException {
        allSteps = currentProject.getService().getGaugeConnection().fetchAllSteps()
                .stream().map(stepValue -> stepValue.getStepText()).collect(Collectors.toList());
    }

    @Step("Fetch all concepts from gauge")
    public void fetchAllConcepts() throws IOException {
        allConcepts = currentProject.getService().getGaugeConnection().fetchAllConcepts()
                .stream().map(stepValue -> stepValue.getStepValue().getStepText()).collect(Collectors.toList());
    }

    @Step("Verify all the steps are present <table>")
    public void verify(Table table) throws IOException {
        List<String> expected = getSteps(table);

        assertThat(allConcepts).containsAll(expected);
    }

    @Step("Verify all the steps are present <table> with default steps <steps>")
    public void verifySteps(Table table, Table defaultSteps) throws IOException {
        List<String> expected = defaultSteps.getColumnValues(0);
        expected.addAll(getSteps(table));

        assertThat(allSteps).containsAll(expected);
    }

    @Step("fetch step values for the following <table>")
    public void fetchStepValues(Table table) {
        GaugeConnection gaugeConnection = currentProject.getService().getGaugeConnection();
        stepValues = table.getColumnValues(0).stream().map(s -> gaugeConnection.getStepValue(s).getStepText()).collect(Collectors.toList());
    }

    @Step("Verify all the step values are present <table>")
    public void verifyStepValues(Table table) {
        ArrayList<String> steps = new ArrayList<>();
        List<String> columnNames = table.getColumnNames();
        table.getTableRows().forEach(row -> {
            List<String> parameters = Arrays.asList(row.getCell("parameters").trim().split(","));
            StepValue e = new StepValue(row.getCell("step text").trim(), row.getCell("step annotation text").trim(), parameters);
            steps.add(e.getStepText());
        });

        assertThat(stepValues).containsAll(steps);
    }

    @Step("Refactor step <old step> to <new step> via api")
    public void refactor(String oldStep, String newStep) throws Exception {
        refactorResponse = currentProject.getService().getGaugeConnection().sendPerformRefactoringRequest(oldStep, newStep);
        assertThat(refactorResponse.getSuccess()).isTrue().withFailMessage("Refactoring resulted in error");
    }

    @Step("verify refactoring didn't change files")
    public void verifyRefactoring() {
        assertThat(refactorResponse.getFilesChangedList()).isEmpty();
    }

    private List<String> getSteps(Table table) {
        List<String> columnNames = table.getColumnNames();
        final List<String> steps = new ArrayList<>();
        for (TableRow row : table.getTableRows()) {
            StepValue stepValue = currentProject.getService().getGaugeConnection().getStepValue(row.getCell(columnNames.get(0)));
            if (stepValue != null && stepValue.getStepText() != null)
                steps.add(stepValue.getStepText().trim());
        }
        return steps;
    }
}
