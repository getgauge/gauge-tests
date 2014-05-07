package step_implementations;

import com.thoughtworks.twist2.Step;
import com.thoughtworks.twist2.Table;
import common.GaugeProject;
import common.Scenario;
import common.Specification;

import java.util.List;

public class SpecAndScenarioCreation {

    private final GaugeProject currentProject;

    public SpecAndScenarioCreation() {
        this.currentProject = GaugeProject.getCurrent();
    }

    @Step("Create <scenario name> in <spec name> with the following steps <steps table>")
    public void addContextToSpecification(String scenarioName, String specName, Table steps) throws Exception {
        if (steps.getColumnNames().size() != 2) {
            throw new RuntimeException("Expected two columns for table");
        }

        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            spec = currentProject.createSpecification(specName);
        }

        Scenario scenario = new Scenario(scenarioName);
        for (List<String> rows : steps.getRows()) {
            scenario.addSteps(rows.get(0));
            currentProject.implementStep(rows.get(0), rows.get(1));
        }
        spec.addScenarios(scenario);
        spec.save();
    }

}
