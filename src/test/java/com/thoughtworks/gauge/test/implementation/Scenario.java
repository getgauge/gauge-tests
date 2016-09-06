package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.builders.SpecificationBuilder;

public class Scenario {
    @Step("Create <scenario name> in <spec name> with the following steps <steps table>")
    public void addContextToSpecification(String scenarioName, String specName, Table steps) throws Exception {
        new SpecificationBuilder().withScenarioName(scenarioName)
                .withSpecName(specName)
                .withScenarioDataStoreWriteStatement("specName", specName)
                .withScenarioDataStoreWriteStatement("Scenario name",scenarioName)
                .withScenarioDataStoreWriteStatement(scenarioName,steps.toString())
                .withSteps(steps)
                .buildAndAddToProject();
    }

    @Step("Create <scenario name> in <spec name> within sub folder <subFolder> with the following steps <steps table>")
    public void addContextToSpecification(String scenarioName, String specName, String subFolder, Table steps) throws Exception {
        new SpecificationBuilder().withScenarioName(scenarioName)
                .withSpecName(specName)
                .withSteps(steps)
                .withSubDirPath(subFolder)
                .buildAndAddToProject();
    }

    @Step("Create a scenario <scenario name> in specification <spec name> with the following continue on failure steps <table>")
    public void createScenarioWithContinueSteps(String scenarioName, String specName, Table steps) throws Exception {
        new SpecificationBuilder().withScenarioName(scenarioName)
                .withSpecName(specName)
                .withSteps(steps)
                .buildAndAddToProject();
    }
}
