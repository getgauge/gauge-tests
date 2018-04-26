package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.test.common.builders.SpecificationBuilder;

public class TagsExecution {
    @Step("Add tags <tags> to scenario <scenario name> in specification <specification name>")
    public void addTagsToScenario(String tags, String scenarioName, String specName) throws Exception {
        new SpecificationBuilder().withScenarioName(scenarioName)
                .withSpecName(specName)
                .withTags(tags)
                .buildAndAddToProject(false);
    }

    @Step("Add tags <tags> to specification <specification name>")
    public void addTagsToSpec(String tags, String specName) throws Exception {
        new SpecificationBuilder()
                .withSpecName(specName)
                .withTags(tags)
                .buildAndAddToProject(false);
    }
}
