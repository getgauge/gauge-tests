package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import java.util.List;

public class GaugeProjectBuilder {

    public static void createScenarioAndSteps(String scenarioName, String specName, Table steps, boolean implement, boolean appendCode) throws Exception {
        Specification spec = GaugeProject.currentProject.findSpecification(specName);
        if (spec == null) {
            spec = GaugeProject.currentProject.createSpecification(specName);
        }
        Scenario scenario = new Scenario(scenarioName);
        List<String> columnNames = steps.getColumnNames();
        for (TableRow rows : steps.getTableRows()) {
            scenario.addSteps(rows.getCell(columnNames.get(0)));
            if (implement)
                GaugeProject.currentProject.implementStep(rows.getCell(columnNames.get(0)), rows.getCell(columnNames.get(1)), appendCode);
        }
        spec.addScenarios(scenario);
        spec.save();
    }
}
