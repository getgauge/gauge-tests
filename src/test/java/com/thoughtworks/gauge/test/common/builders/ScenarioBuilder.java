package com.thoughtworks.gauge.test.common.builders;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Scenario;

public class ScenarioBuilder {

    private String scenarioName;
    private Table scenarioSteps;
    private boolean appendCode;

    public ScenarioBuilder withScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
        return this;
    }

    public ScenarioBuilder withSteps(Table steps){
        this.scenarioSteps = steps;
        return this;
    }

    public ScenarioBuilder withAppendCode(boolean appendCode) {
        this.appendCode = appendCode;
        return this;
    }

    public Scenario buildScenario() throws Exception {
        if(!canBuild())
            throw new Exception("scenario name and steps needed for initialization");

        Scenario scenario = new Scenario(scenarioName);
        for (TableRow row : scenarioSteps.getTableRows()) {
            scenario.addItem(row.getCell("step text"), row.getCell("Type"));
            implement(scenarioSteps,row);
        }

        return scenario;
    }

    public boolean canBuild(){
        return (scenarioName!=null && scenarioSteps!=null);
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

