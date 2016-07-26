package com.thoughtworks.gauge.test.common.builders;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Scenario;
import com.thoughtworks.gauge.test.common.Specification;

public class ScenarioBuilder {

    private String scenarioName;
    private Table scenarioSteps;
    private boolean appendCode;
    private Specification spec;

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

    private Scenario buildScenario() throws Exception {
        if(!canBuild())
            throw new Exception("scenario name and steps needed for initialization");

        Scenario scenario = new Scenario(scenarioName);
        for (TableRow row : scenarioSteps.getTableRows()) {
            scenario.addItem(row.getCell("step text"), row.getCell("Type"));
            GaugeProject.implement(scenarioSteps, row,appendCode);
        }

        return scenario;
    }

    public boolean canBuild(){
        return (scenarioName!=null && scenarioSteps!=null);
    }

    public ScenarioBuilder withSpecification(Specification spec) {
        this.spec = spec;
        return this;
    }

    public void build() throws Exception {
        spec.addScenarios(buildScenario());
    }
}

