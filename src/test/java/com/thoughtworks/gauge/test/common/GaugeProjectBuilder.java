package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

public class GaugeProjectBuilder {

    private String scenarioName;
    private String specName;
    private Table scenarioSteps;
    private boolean appendCode;
    private String subDirPath;
    private String specsDirPath;
    private Table contextSteps;
    private Table tearDownSteps;

    public GaugeProjectBuilder(){}

    public GaugeProjectBuilder withContextSteps(Table contextSteps) {
        this.contextSteps = contextSteps;
        return this;
    }

    public GaugeProjectBuilder withScenarioName(String scenarioName){
        this.scenarioName = scenarioName;
        return this;
    }

    public GaugeProjectBuilder withSpecsDirPath(String specsDirPath){
        this.specsDirPath = specsDirPath;
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
        this.scenarioSteps = steps;
        return this;
    }

    public GaugeProjectBuilder withAppendCode(boolean appendCode){
        this.appendCode = appendCode;
        return this;
    }

    public GaugeProjectBuilder withTeardownSteps(Table tearDownSteps) {
        this.tearDownSteps = tearDownSteps;
        return this;
    }

    public void buildAndAddToProject() throws Exception {
        Specification spec = GaugeProject.currentProject.findSpecification(specName);
        if (spec == null) {
            spec = GaugeProject.currentProject.createSpecification(subDirPath,specName);
        }

        if(contextSteps!=null) {
            for (TableRow row : contextSteps.getTableRows()) {
                spec.addContextSteps(row.getCell("step text"));
                implement(contextSteps, row);
                spec.save();
            }
        }

        if(scenarioName!=null) {
            Scenario scenario = new Scenario(scenarioName);
            for (TableRow row : scenarioSteps.getTableRows()) {
                scenario.addItem(row.getCell("step text"), row.getCell("Type"));
                implement(scenarioSteps,row);
            }
            spec.addScenarios(scenario);
            spec.save();
        }

        if(tearDownSteps!=null)
        {
            for (TableRow row : tearDownSteps.getTableRows()) {
                spec.addTeardownSteps(row.getCell("step text"));
                implement(tearDownSteps, row);
                spec.save();
            }
        }
    }

    private void implement(Table impl, TableRow row) throws Exception {
        if(impl.getColumnNames().contains("implementation")) {
            GaugeProject.currentProject.implementStep(row.getCell("step text"),
                    row.getCell("implementation"),
                    Boolean.parseBoolean(row.getCell("continue on failure")),
                    appendCode);
        }
    }
}
