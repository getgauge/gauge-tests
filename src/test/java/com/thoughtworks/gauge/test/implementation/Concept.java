package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.builders.ConceptBuilder;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;


public class Concept {

    @Step("Create concept <concept name> with following steps <steps table>")
    public void createConcept(String conceptName, Table steps) throws Exception {
        new ConceptBuilder()
                .withName(conceptName)
                .withSteps(steps)
                .build();
    }
}
