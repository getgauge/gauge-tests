package step_implementations;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import common.GaugeProjectBuilder;
import common.Scenario;
import common.Specification;

import java.io.IOException;

import static common.GaugeProject.currentProject;

public class ContextExecution {

    @Step("Create a specification <spec name> with the following contexts <steps table>")
    public void createContextsInSpec(String specName, Table steps) throws Exception {
        createContexts(specName, steps, true);
    }

    @Step("Create a specification <spec name> with the following unimplemented contexts <steps table>")
    public void createUnimplementedContextsInSpec(String specName, Table steps) throws Exception {
        createContexts(specName, steps, false);
    }

    private void createContexts(String specName, Table steps, boolean implement) throws Exception {
        Specification spec = currentProject.createSpecification(specName);
        for (TableRow rows : steps.getTableRows()) {
            spec.addContextSteps(rows.getCell("step text"));
            if (implement) {
                if (steps.getColumnNames().size() != 2)
                    throw new RuntimeException("Expected two columns for table");
                currentProject.implementStep(rows.getCell("step text"), rows.getCell("implementation"), false);
            }
            spec.save();
        }
    }

    @Step("Create a scenario <scenario name> in specification <spec name> with the following steps with implementation <steps table>")
    public void createScenarioWithImpl(String scenarioName, String specName, Table steps) throws Exception {
        GaugeProjectBuilder.createScenarioAndSteps(scenarioName, specName, steps, true, false);
    }

    @Step("Create a scenario <scenario name> in specification <spec name> with the following steps <steps table>")
    public void createScenario(String scenarioName, String specName, Table steps) throws Exception {
        GaugeProjectBuilder.createScenarioAndSteps(scenarioName, specName, steps, false, false);
    }

    @Step("Add tags <tags> to scenario <scenario name> in specification <specification name>")
    public void addTagsToScenario(String tags, String scenarioName, String specName) throws IOException {
        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            spec = currentProject.createSpecification(specName);
        }
        Scenario currentScenario = currentProject.findScenario(scenarioName, spec.getScenarios());
        currentScenario.addTags(tags);
        spec.save();
    }

    @Step("Add tags <tags> to specification <specification name>")
    public void addTagsToSpec(String tags, String specName) throws IOException {
        Specification spec = currentProject.findSpecification(specName);
        if (spec == null) {
            spec = currentProject.createSpecification(specName);
        }
        spec.addTags(tags);
        spec.save();
    }
}


