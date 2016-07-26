package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProjectBuilder;
import com.thoughtworks.gauge.test.common.Scenario;
import com.thoughtworks.gauge.test.common.Specification;

import java.io.IOException;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;

public class ContextExecution {

    @Step({"Create a specification <spec name> with the following contexts <steps table>",
            "Create a specification <spec name> with the following unimplemented contexts <steps table>"})
    public void createContextsInSpec(String specName, Table steps) throws Exception {
        new GaugeProjectBuilder()
                .withSpecName(specName)
                .withContextSteps(steps)
                .buildAndAddToProject();
    }

    @Step({"Add the following teardown steps in specification <spec name> <steps table>",
            "Add the following unimplemented teardown steps in specification <spec name> <steps table>"})
    public void createTeardownInSpec(String specName, Table steps) throws Exception {
        new GaugeProjectBuilder()
                .withSpecName(specName)
                .withTeardownSteps(steps)
                .buildAndAddToProject();
    }

    @Step({"Create a scenario <scenario name> in specification <spec name> with the following steps with implementation <steps table>",
            "Create a scenario <scenario name> in specification <spec name> with the following steps <steps table>"})
    public void createScenarioWithImpl(String scenarioName, String specName, Table steps) throws Exception {
        new GaugeProjectBuilder().withScenarioName(scenarioName)
                .withSpecName(specName)
                .withSteps(steps)
                .buildAndAddToProject();
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