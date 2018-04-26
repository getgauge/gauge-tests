package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.builders.SpecificationBuilder;

public class DataStore {
    @Step("Create a scenario <newScenario> in specification <newSpec> with steps to read and write to datastore <table>")
    public void addToDataStore(String scenario, String spec, Table datastoreValues) throws Exception {
        new SpecificationBuilder().withScenarioName(scenario)
                .withSpecName(spec)
                .withDataStoreWriteStatement(datastoreValues.getColumnNames(), datastoreValues.getTableRows().get(0))
                .withDataStorePrintValues(datastoreValues.getColumnNames(), datastoreValues.getTableRows().get(1))
                .withAppendCode(true)
                .buildAndAddToProject(false);
    }

    @Step("Create a scenario <readScenario> in specification <newSpec> with step to read from datastore <table>")
    public void readFromDataStore(String scenario, String spec, Table datastoreValues) throws Exception {
        new SpecificationBuilder().withScenarioName(scenario)
                .withSpecName(spec)
                .withDataStorePrintValues(datastoreValues.getColumnNames(), datastoreValues.getTableRows().get(0))
                .withAppendCode(true)
                .buildAndAddToProject(false);
    }

    @Step("Create a scenario <writeScenario> in specification <newSpec> with step to write to datastore <table>")
    public void writeToDataStore(String scenario, String spec, Table datastoreValues) throws Exception {
        new SpecificationBuilder().withScenarioName(scenario)
                .withSpecName(spec)
                .withDataStoreWriteStatement(datastoreValues.getColumnNames(), datastoreValues.getTableRows().get(0))
                .withAppendCode(true)
                .buildAndAddToProject(false);
    }
}
