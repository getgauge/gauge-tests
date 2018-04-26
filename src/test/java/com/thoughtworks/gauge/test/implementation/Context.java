package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.builders.SpecificationBuilder;

public class Context {

    @Step({"Create a specification <spec name> with the following contexts <steps table>",
            "Create a specification <spec name> with the following unimplemented contexts <steps table>"})
    public void createContextsInSpec(String specName, Table steps) throws Exception {
        new SpecificationBuilder()
                .withSpecName(specName)
                .withContextSteps(steps)
                .buildAndAddToProject(false);
    }

    @Step({"Add the following teardown steps in specification <spec name> <steps table>",
            "Add the following unimplemented teardown steps in specification <spec name> <steps table>"})
    public void createTeardownInSpec(String specName, Table steps) throws Exception {
        new SpecificationBuilder()
                .withSpecName(specName)
                .withTeardownSteps(steps)
                .buildAndAddToProject(false);
    }
}