package step_implementations.api;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.Table;
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
        info = new StepsAssertInfo(15000, 150);
    }

    @Step("Fetch all concepts from gauge")
    public void fetchAllConcepts() {
        info = new ConceptsAssertInfo(15000, 150);
    }

    @Step("Verify all the steps are present <table>")
    public void verify(Table table) {
        List<String> steps = getSteps(table);
        info.setExpected(steps.toArray(new String[steps.size()]));
    }

    @Step("Verify all the steps are present <table> with default steps <steps>")
    public void verifySteps(Table table, String defaultSteps) {
        String[] stepTexts = defaultSteps.split(System.getProperty("line.separator"));
        final List<String> steps = getSteps(table);
        String[] dest = steps.toArray(new String[steps.size() + stepTexts.length]);
        System.arraycopy(stepTexts, 0, dest, steps.size(), stepTexts.length);
        info.setExpected(dest);
        Assert.till(info);
    }

    @Step("fetch step values for the following <table>")
    public void fetchStepValues(Table table) {
        stepValues = new ArrayList<StepValue>();
        for (int i = 0; i < table.getRows().size(); i++) {
            List<String> row = table.getRows().get(i);
            StepValue stepValue = currentProject.getService().getGaugeConnection().getStepValue(row.get(0));
            stepValues.add(stepValue);
        }
    }

    @Step("Verify all the step values are present <table>")
    public void verifyStepValues(Table table) {
        List<StepValue> steps = new ArrayList<StepValue>();
        for (List<String> row : table.getRows()) {
            List<String> parameters = row.get(2).equals("") ? new ArrayList<String>() : Arrays.asList(row.get(2).trim().split(","));
            steps.add(new StepValue(row.get(0).trim(), row.get(1).trim(), parameters));
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
        final List<String> steps = new ArrayList<String>();
        for (int i = 0; i < table.getRows().size(); i++) {
            List<String> row = table.getRows().get(i);
            StepValue stepValue = currentProject.getService().getGaugeConnection().getStepValue(row.get(0));
            steps.add(stepValue.getStepText().trim());
        }
        return steps;
    }
}
