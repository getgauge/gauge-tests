package step_implementations;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import common.Scenario;
import common.Specification;

import java.util.List;

import static common.GaugeProject.currentProject;

public class SpecAndScenarioCreation {


    public SpecAndScenarioCreation() {
    }

    @Step("Create <scenario name> in <spec name> with the following steps <steps table>")
    public void addContextToSpecification(String scenarioName, String specName, Table steps) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            spec = currentProject.createSpecification(specName);
        }

        Scenario scenario = new Scenario(scenarioName);
        for (List<String> rows : steps.getRows()) {
            scenario.addSteps(rows.get(0));
            if (shouldCreateImplementation(rows)) {
                currentProject.implementStep(rows.get(0), rows.get(1), false);
            }
        }
        spec.addScenarios(scenario);
        spec.save();
    }

    private boolean shouldCreateImplementation(List<String> row) {
        if (row.size() == 1) {
            return false;
        } else if (row.get(1) != null && !row.get(1).isEmpty()) {
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
        if (steps.getColumnNames().size() != 2) {
            throw new Exception("Expecting table with 2 columns: steps and implementations");
        }
        for (List<String> row : steps.getRows()) {
            currentProject.implementStep(row.get(0), row.get(1), false);
        }

    }

}
