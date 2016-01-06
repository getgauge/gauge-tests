package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.test.common.Concept;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;


public class ConceptExecution {

    @Step("Create concept <concept name> with following steps <steps table>")
    public void createConcept(String conceptName, Table steps) throws Exception {
        Concept concept = getCurrentProject().createConcept(conceptName, steps);
        getCurrentProject().addConcepts(concept);
    }

}
