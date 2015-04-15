package step_implementations.api;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.StepValue;
import com.thoughtworks.gauge.Table;
import common.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static common.GaugeProject.currentProject;

public class Api {

    private StepsAssertInfo info;

    @Step("Fetch all steps from gauge")
    public void fetchAllSteps() throws IOException {
        info = new StepsAssertInfo(15000, 150);
    }

    @Step("Start Gauge daemon")
    public void startGauge() throws IOException, InterruptedException {
        currentProject.createGaugeService();
    }

    @Step("Verify all the steps are present <table> with default steps <steps>")
    public void verifySteps(Table table, String defaultSteps) {
        String[] stepTexts = defaultSteps.split(System.getProperty("line.separator"));
        final List<String> steps = new ArrayList<String>();
        for (int i = 0; i < table.getRows().size(); i++) {
            List<String> row = table.getRows().get(i);
            StepValue stepValue = currentProject.getService().getGaugeConnection().getStepValue(row.get(0), false);
            steps.add(stepValue.getStepText().trim());
        }
        String[] dest = steps.toArray(new String[steps.size() + stepTexts.length]);
        System.arraycopy(stepTexts, 0, dest, steps.size(), stepTexts.length);
        info.setExpected(dest);
        Assert.till(info);
    }

    @AfterScenario
    public void tearDown() {
        if (currentProject.getService() != null)
            currentProject.getService().getGaugeProcess().destroy();
    }

}
