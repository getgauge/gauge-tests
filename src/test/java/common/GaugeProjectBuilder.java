package common;

import com.thoughtworks.gauge.Table;

import java.util.List;

import static common.GaugeProject.currentProject;

public class GaugeProjectBuilder {

    public static void createScenarioAndSteps(String scenarioName, String specName, Table steps, boolean implement, boolean appendCode) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            spec = currentProject.createSpecification(specName);
        }
        Scenario scenario = new Scenario(scenarioName);
        for (List<String> rows : steps.getRows()) {
            scenario.addSteps(rows.get(0));
            if (implement)
                currentProject.implementStep(rows.get(0), rows.get(1), appendCode);
        }
        spec.addScenarios(scenario);
        spec.save();
    }
}
