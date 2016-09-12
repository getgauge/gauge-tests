package com.thoughtworks.gauge.test.common.builders;

import com.thoughtworks.gauge.Table;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;

public class HooksBuilder {
    private String hookLevel;
    private String hookType;
    private String implementation;
    private boolean hasException;
    private String aggregation;
    private Table tags;

    public HooksBuilder withHookLevel(String hookLevel) {
        this.hookLevel = hookLevel;
        return this;
    }

    public HooksBuilder withHookType(String hookType) {
        this.hookType = hookType;
        return this;
    }

    public HooksBuilder withImplementation(String implementation) {
        this.implementation = implementation;
        return this;
    }

    public void build() throws Exception {
        if(hasException)
            currentProject.createHookWithException(hookLevel, hookType);

        if(tags!=null)
            currentProject.createHooksWithTagsAndPrintMessage(hookLevel, hookType, implementation, aggregation, tags);

        if(!hasException && tags==null)
            currentProject.createHookWithPrint(hookLevel, hookType, implementation);
    }

    public HooksBuilder withException() {
        hasException = true;
        return this;
    }

    public HooksBuilder withAggregation(String aggregation) {
        this.aggregation = aggregation;
        return this;
    }

    public HooksBuilder withTags(Table tags) {
        this.tags = tags;
        return this;
    }
}
