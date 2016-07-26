package com.thoughtworks.gauge.test.common.builders;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Specification;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;

public class SpecificationBuilder {
    private ScenarioBuilder scenarioBuilder;
    private TagsBuilder tagsBuilder;

    private String scenarioName;
    private String specName;
    private boolean appendCode;
    private String subDirPath;
    private String specsDirPath;
    private Table contextSteps;
    private Table tearDownSteps;

    public SpecificationBuilder(){
        scenarioBuilder = new ScenarioBuilder();
        tagsBuilder = new TagsBuilder();
    }

    public SpecificationBuilder withContextSteps(Table contextSteps) {
        this.contextSteps = contextSteps;
        return this;
    }

    public SpecificationBuilder withScenarioName(String scenarioName){
        this.tagsBuilder.withScenarioName(scenarioName);
        this.scenarioBuilder.withScenarioName(scenarioName);
        this.scenarioName = scenarioName;
        return this;
    }

    public SpecificationBuilder withSpecsDirPath(String specsDirPath){
        this.specsDirPath = specsDirPath;
        return this;
    }

    public SpecificationBuilder withSubDirPath(String subDirPath){
        this.subDirPath = subDirPath;
        return this;
    }

    public SpecificationBuilder withSpecName(String specName){
        this.specName = specName;
        return this;
    }

    public SpecificationBuilder withSteps(Table steps){
        this.scenarioBuilder.withSteps(steps);
        return this;
    }

    public SpecificationBuilder withAppendCode(boolean appendCode){
        this.scenarioBuilder.withAppendCode(appendCode);
        return this;
    }

    public SpecificationBuilder withTeardownSteps(Table tearDownSteps) {
        this.tearDownSteps = tearDownSteps;
        return this;
    }

    public void buildAndAddToProject() throws Exception {
        Specification spec = GaugeProject.currentProject.findSpecification(specName);
        if (spec == null) {
            spec = GaugeProject.currentProject.createSpecification(subDirPath,specName);
        }

        if(contextSteps!=null) {
            for (TableRow row : contextSteps.getTableRows()) {
                spec.addContextSteps(row.getCell("step text"));
                implement(contextSteps, row);
            }
        }

        if(scenarioBuilder.canBuild())
            spec.addScenarios(scenarioBuilder.buildScenario());

        if(tagsBuilder.canBuild())
            tagsBuilder.withScenario(currentProject.findScenario(scenarioName, spec.getScenarios()))
                .withSpecification(spec)
                .build();

        if(tearDownSteps!=null)
        {
            for (TableRow row : tearDownSteps.getTableRows()) {
                spec.addTeardownSteps(row.getCell("step text"));
                implement(tearDownSteps, row);
            }
        }

        spec.save();
    }

    private void implement(Table impl, TableRow row) throws Exception {
        if(impl.getColumnNames().contains("implementation")) {
            GaugeProject.currentProject.implementStep(row.getCell("step text"),
                    row.getCell("implementation"),
                    Boolean.parseBoolean(row.getCell("continue on failure")),
                    this.appendCode);
        }
    }

    public SpecificationBuilder withTags(String tags) {
        this.tagsBuilder.withTags(tags);
        return this;
    }
}