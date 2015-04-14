package step_implementations;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import common.Scenario;
import common.Specification;

import java.io.IOException;
import java.util.List;

import static common.GaugeProject.currentProject;

public class ContextExecution {

    private Specification spec;


    @Step("Create a specification <spec name> with the following contexts <steps table>")
    public void createContextsInSpec(String specName , Table steps)throws Exception{

        if (steps.getColumnNames().size() != 2) {
            throw new RuntimeException("Expected two columns for table");
        }

        spec = currentProject.createSpecification(specName);

        for (List<String> rows : steps.getRows()) {
            spec.addContextSteps(rows.get(0));
            currentProject.implementStep(rows.get(0),rows.get(1));
            spec.save();
        }
    }


    @Step("Create a scenario <scenario name> in specification <spec name> with the following steps with implementation <steps table>")
    public void createScenarioWithImpl(String scenarioName, String specName, Table steps) throws Exception {
        createScenarioAndSteps(scenarioName, specName, steps, true);
    }

    private void createScenarioAndSteps(String scenarioName, String specName, Table steps, boolean implement) throws Exception {
        spec = currentProject.findSpecification(specName);
        if (spec == null) {
            spec = currentProject.createSpecification(specName);
        }
        Scenario scenario = new Scenario(scenarioName);
        for (List<String> rows : steps.getRows()) {
            scenario.addSteps(rows.get(0));
            if (implement)
                currentProject.implementStep(rows.get(0), rows.get(1));
        }
        spec.addScenarios(scenario);
        spec.save();
    }

    @Step("Create a scenario <scenario name> in specification <spec name> with the following steps <steps table>")
    public void createScenario(String scenarioName, String specName, Table steps) throws Exception {
        createScenarioAndSteps(scenarioName, specName, steps, false);
    }

    @Step("Add tags <tags> to scenario <scenario name> in specification <specification name>")
    public void addTagsToScenario(String tags, String scenarioName, String specName) throws IOException {
        spec = currentProject.findSpecification(specName);
        if (spec == null){
            spec = currentProject.createSpecification(specName);
        }
        Scenario currentScenario = currentProject.findScenario(scenarioName, spec.getScenarios());
        currentScenario.addTags(tags);
        spec.save();
    }

    @Step("Add tags <tags> to specification <specification name>")
    public void addTagsToSpec(String tags, String specName) throws IOException {
        spec = currentProject.findSpecification(specName);
        if (spec == null){
            spec = currentProject.createSpecification(specName);
        }
        spec.addTags(tags);
        spec.save();
    }
}


