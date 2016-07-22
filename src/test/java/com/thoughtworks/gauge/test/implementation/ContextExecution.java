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

    @Step("Create a specification <spec name> with the following contexts <steps table>")
    public void createContextsInSpec(String specName, Table steps) throws Exception {
        createContexts(specName, steps, true);
    }

    @Step("Create a specification <spec name> with the following unimplemented contexts <steps table>")
    public void createUnimplementedContextsInSpec(String specName, Table steps) throws Exception {
        createContexts(specName, steps, false);
    }

    @Step("Add the following teardown steps in specification <spec name> <steps table>")
    public void createTeardownInSpec(String specName, Table steps) throws Exception {
        createTeardownSteps(specName, steps, true);
    }

    @Step("Add the following unimplemented teardown steps in specification <spec name> <steps table>")
    public void createUnimplementedTeardownInSpec(String specName, Table steps) throws Exception {
        createTeardownSteps(specName, steps, false);
    }

    private void createContexts(String specName, Table steps, boolean implement) throws Exception {
        Specification spec = currentProject.createSpecification(specName);
        for (TableRow rows : steps.getTableRows()) {
            spec.addContextSteps(rows.getCell("step text"));
            if (implement) {
                if (steps.getColumnNames().size() < 2)
                    throw new RuntimeException("Expected minimum two columns for table");
                currentProject.implementStep(rows.getCell("step text"),
                        rows.getCell("implementation"),
                        Boolean.parseBoolean(rows.getCell("continue")),
                        false);
            }
            spec.save();
        }
    }

    private void createTeardownSteps(String specName, Table steps, boolean implement) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        for (TableRow rows : steps.getTableRows()) {
            spec.addTeardownSteps(rows.getCell("step text"));
            if (implement) {
                if (steps.getColumnNames().size() < 2)
                    throw new RuntimeException("Expected minimum two columns for table");
                currentProject.implementStep(rows.getCell("step text"),
                        rows.getCell("implementation"),
                        Boolean.parseBoolean(rows.getCell("continue")),
                        false);
            }
            spec.save();
        }
    }

    @Step("Create a scenario <scenario name> in specification <spec name> with the following steps with implementation <steps table>")
    public void createScenarioWithImpl(String scenarioName, String specName, Table steps) throws Exception {
        new GaugeProjectBuilder().withScenarioName(scenarioName)
                .withSpecName(specName)
                .withSteps(steps)
                .withImplement(true)
                .createScenarioAndSteps();
    }

    @Step("Create a scenario <scenario name> in specification <spec name> with the following steps <steps table>")
    public void createScenario(String scenarioName, String specName, Table steps) throws Exception {
        new GaugeProjectBuilder().withScenarioName(scenarioName)
                .withSpecName(specName)
                .withSteps(steps)
                .createScenarioAndSteps();
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