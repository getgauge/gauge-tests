package com.thoughtworks.gauge.test.common.builders;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.test.common.GaugeProject;

public class ImplementationBuilder {
    private Table steps;

    public ImplementationBuilder withSteps(Table steps) {
        this.steps = steps;
        return this;
    }


    public void build() throws Exception {
        for (TableRow row : steps.getTableRows()) {
            GaugeProject.implement(steps, row, false);
        }
    }
}
