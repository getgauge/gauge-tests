package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.builders.HooksBuilder;


public class ExecutionHooks {

    @Step("Create <hook level> level <hook type> hook with implementation <print string>")
    public void createHookWithImpl(String hookLevel, String hookType, String implementation) throws Exception {
        new HooksBuilder().withHookLevel(hookLevel)
                .withHookType(hookType)
                .withImplementation(implementation)
                .build();
    }

    @Step("Create <hook level> level <hook type> hook with exception")
    public void createHookWithException(String hookLevel, String hookType) throws Exception {
        new HooksBuilder().withHookLevel(hookLevel)
                .withHookType(hookType)
                .withException()
                .build();
    }

    @Step("Create <spec> level <before> hook with implementation <inside before spec hook> with <aggregation> aggregated tags <table>")
    public void createHooksWithAndTags(String hookLevel, String hookType, String implementation, String aggregation, Table tags) throws Exception {
        new HooksBuilder().withHookLevel(hookLevel)
                .withHookType(hookType)
                .withImplementation(implementation)
                .withAggregation(aggregation)
                .withTags(tags)
                .build();
    }
}
