package common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import java.util.List;

import static common.GaugeProject.currentProject;

public class GaugeProjectBuilder {

    public static void createScenarioAndSteps(String scenarioName, String specName, Table steps, boolean implement, boolean appendCode) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            spec = currentProject.createSpecification(specName);
        }
        Scenario scenario = new Scenario(scenarioName);
        List<String> columnNames = steps.getColumnNames();
        for (TableRow rows : steps.getTableRows()) {
            scenario.addSteps(rows.getCell(columnNames.get(0)));
            if (implement)
                currentProject.implementStep(rows.getCell(columnNames.get(0)), rows.getCell(columnNames.get(1)), appendCode);
        }
        spec.addScenarios(scenario);
        spec.save();
    }
}
