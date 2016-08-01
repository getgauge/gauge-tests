package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;


public class Concept {

    @Step("Create concept <concept name> with following steps <steps table>")
    public void createConcept(String conceptName, Table steps) throws Exception {
        com.thoughtworks.gauge.test.common.Concept concept = getCurrentProject().createConcept(conceptName, steps);
        getCurrentProject().addConcepts(concept);
    }
}
