package step_implementations.api;

import com.thoughtworks.gauge.*;
import common.Assert;
import common.AssertInfo;

import java.io.IOException;
import java.util.*;

import static common.GaugeProject.currentProject;
import static org.junit.Assert.fail;

public class Api {

    private AssertInfo info;
    private List<StepValue> stepValues;
    private Comparator<StepValue> comparator;
    private gauge.messages.Api.PerformRefactoringResponse refactorResponse;

    public Api() {
        comparator = new Comparator<StepValue>() {
            @Override
            public int compare(StepValue o1, StepValue o2) {
                return o1.getStepText().compareTo(o2.getStepText());
            }
        };
    }

    @Step("Start Gauge daemon")
    public void startGauge() throws IOException, InterruptedException {
        currentProject.createGaugeService();
    }

    @Step("Fetch all steps from gauge")
    public void fetchAllSteps() throws IOException {
        info = new StepsAssertInfo(100000, 150);
    }

    @Step("Fetch all concepts from gauge")
    public void fetchAllConcepts() {
        info = new ConceptsAssertInfo(50000, 150);
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
        String[] dest = steps.toArray(new String[steps.size() + stepTexts.size()]);
        System.arraycopy(stepTexts.toArray(), 0, dest, steps.size(), stepTexts.size());
        info.setExpected(dest);
        Assert.till(info);
    }

    @Step("fetch step values for the following <table>")
    public void fetchStepValues(Table table) {
        stepValues = new ArrayList<StepValue>();
        List<String> columnNames = table.getColumnNames();
        for (TableRow row : table.getTableRows()) {
            StepValue stepValue = currentProject.getService().getGaugeConnection().getStepValue(row.getCell(columnNames.get(0)));
            stepValues.add(stepValue);
        }
    }

    @Step("Verify all the step values are present <table>")
    public void verifyStepValues(Table table) {
        List<StepValue> steps = new ArrayList<StepValue>();
        List<String> columnNames = table.getColumnNames();
        for (TableRow row : table.getTableRows()) {
            List<String> parameters = row.getCell(columnNames.get(2)).equals("") ? new ArrayList<String>() : Arrays.asList(row.getCell(columnNames.get(2)).trim().split(","));
            steps.add(new StepValue(row.getCell(columnNames.get(0)).trim(), row.getCell(columnNames.get(1)).trim(), parameters));
        }
        Collections.sort(steps, comparator);
        Collections.sort(stepValues, comparator);
        if (!steps.equals(stepValues)) {
            System.out.println(stepValues);
            System.out.println(steps);
            fail();
        }
    }

    @Step("Refactor step <old step> to <new step> via api")
    public void refactor(String oldStep, String newStep) throws Exception {
        refactorResponse = currentProject.getService().getGaugeConnection().sendPerformRefactoringRequest(oldStep, newStep);
        if (!refactorResponse.getSuccess()) fail("Refactoring resulted in error");
    }

    @Step("verify refactoring didn't change files")
    public void verifyRefactoring() {
        if (refactorResponse.getFilesChangedList().size() != 0)
            throw new RuntimeException("");
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
