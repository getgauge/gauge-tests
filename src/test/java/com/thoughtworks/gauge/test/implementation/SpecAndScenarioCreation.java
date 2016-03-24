package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.Scenario;
import com.thoughtworks.gauge.test.common.Specification;

import java.util.List;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;

public class SpecAndScenarioCreation {
    @Step("Create <scenario name> in <spec name> with the following steps <steps table>")
    public void addContextToSpecification(String scenarioName, String specName, Table steps) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            spec = currentProject.createSpecification(specName);
        }
        List<String> columnNames = steps.getColumnNames();
        Scenario scenario = new Scenario(scenarioName);
        for (TableRow row : steps.getTableRows()) {
            scenario.addSteps(row.getCell(columnNames.get(0)));
            boolean b = shouldCreateImplementation(row, columnNames);
            if (b) {
                currentProject.implementStep(row.getCell(columnNames.get(0)), row.getCell(columnNames.get(1)), false);
            }
        }
        spec.addScenarios(scenario);
        spec.save();
    }

    private boolean shouldCreateImplementation(TableRow row, List<String> columnNames) {
        if (columnNames.size() != 2) {
            return false;
        }
        String implementation = row.getCell(columnNames.get(1));
        if (implementation == null) {
            return false;
        } else if (!implementation.isEmpty()) {
            return true;
        }
        return false;
    }

    @Step("Create a specification <spec name> with the following datatable <table>")
    public void createSpecWithDataTable(String specName, Table datatable) throws Exception {
        Specification specification = currentProject.createSpecification(specName);
        specification.addDataTable(datatable);
        specification.save();
    }

    @Step("Create step implementations <table>")
    public void createStepImplementations(Table steps) throws Exception {
        List<String> columnNames = steps.getColumnNames();
        if (columnNames.size() != 2) {
            throw new Exception("Expecting table with 2 columns: steps and implementations");
        }
        for (TableRow row : steps.getTableRows()) {
            currentProject.implementStep(row.getCell(columnNames.get(0)), row.getCell(columnNames.get(1)), false);
        }

    }

}
