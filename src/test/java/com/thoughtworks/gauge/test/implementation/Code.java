package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.builders.ImplementationBuilder;

public class Code {
    @Step("Create step implementations <table>")
    public void createStepImplementations(Table steps) throws Exception {
        new ImplementationBuilder().withSteps(steps).build();
    }
}
