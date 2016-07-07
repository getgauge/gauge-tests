package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import java.util.List;

public class GaugeProjectBuilder {

    private String scenarioName;
    private String specName;
    private Table steps;
    private boolean implement;
    private boolean appendCode;
    private String subDirPath;

    public GaugeProjectBuilder(){}

    public GaugeProjectBuilder withScenarioName(String scenarioName){
        this.scenarioName = scenarioName;
        return this;
    }

    public GaugeProjectBuilder withSubDirPath(String subDirPath){
        this.subDirPath = subDirPath;
        return this;
    }

    public GaugeProjectBuilder withSpecName(String specName){
        this.specName = specName;
        return this;
    }

    public GaugeProjectBuilder withSteps(Table steps){
        this.steps = steps;
        return this;
    }

    public GaugeProjectBuilder withImplement(boolean implement){
        this.implement = implement;
        return this;
    }

    public GaugeProjectBuilder withAppendCode(boolean appendCode){
        this.appendCode = appendCode;
        return this;
    }

    public void createScenarioAndSteps() throws Exception {
        Specification spec = GaugeProject.currentProject.findSpecification(specName);
        if (spec == null) {
            spec = GaugeProject.currentProject.createSpecification(subDirPath,specName);
        }
        Scenario scenario = new Scenario(scenarioName);
        List<String> columnNames = steps.getColumnNames();
        for (TableRow row : steps.getTableRows()) {
            scenario.addItem(row.getCell(columnNames.get(0)), row.getCell("Type"));
            if (implement)
                GaugeProject.currentProject.implementStep(row.getCell(columnNames.get(0)), row.getCell(columnNames.get(1)), false, appendCode);
        }
        spec.addScenarios(scenario);
        spec.save();
    }
}
