package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProjectBuilder;

import java.util.Arrays;
import java.util.List;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;

public class DataStore {
    @Step("Create a scenario <newScenario> in specification <newSpec> with steps to read and write to datastore <table>")
    public void addToDataStore(String scenario, String spec, Table datastoreValues) throws Exception {
        List<String> columnNames = datastoreValues.getColumnNames();
        Table table = new Table(Arrays.asList(columnNames.get(0), "implementation"));
        List<TableRow> rows = datastoreValues.getTableRows();

        TableRow row = rows.get(0);
        table.addRow(Arrays.asList(row.getCell(columnNames.get(0)), currentProject.getDataStoreWriteStatement(row, columnNames)));

        row = rows.get(1);
        table.addRow(Arrays.asList(row.getCell(columnNames.get(0)), currentProject.getDataStorePrintValueStatement(row, columnNames)));

        GaugeProjectBuilder.createScenarioAndSteps(scenario, spec, table, true, true);
    }

    @Step("Create a scenario <readScenario> in specification <newSpec> with step to read from datastore <table>")
    public void readFromDataStore(String scenario, String spec, Table datastoreValues) throws Exception {
        List<String> columnNames = datastoreValues.getColumnNames();
        Table table = new Table(Arrays.asList(columnNames.get(0), "implementation"));
        List<TableRow> rows = datastoreValues.getTableRows();

        // write to datastore
        TableRow row = rows.get(0);
        table.addRow(Arrays.asList(row.getCell(columnNames.get(0)), currentProject.getDataStorePrintValueStatement(row, columnNames)));

        GaugeProjectBuilder.createScenarioAndSteps(scenario, spec, table, true, true);
    }

    @Step("Create a scenario <writeScenario> in specification <newSpec> with step to write to datastore <table>")
    public void writeToDataStore(String scenario, String spec, Table datastoreValues) throws Exception {
        List<String> columnNames = datastoreValues.getColumnNames();
        Table table = new Table(Arrays.asList(columnNames.get(0), "implementation"));
        List<TableRow> rows = datastoreValues.getTableRows();

        TableRow row = rows.get(0);
        table.addRow(Arrays.asList(row.getCell(columnNames.get(0)), currentProject.getDataStoreWriteStatement(row, columnNames)));
        GaugeProjectBuilder.createScenarioAndSteps(scenario, spec, table, true, true);
    }
}
