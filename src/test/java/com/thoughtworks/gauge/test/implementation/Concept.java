package com.thoughtworks.gauge.test.implementation;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.datastore.DataStoreFactory;
import com.thoughtworks.gauge.test.common.builders.ConceptBuilder;

import java.util.ArrayList;
import java.util.List;


public class Concept {

    @Step("Create concept <concept name> with following steps <steps table>")
    public void createConcept(String conceptName, Table steps) throws Exception {
        com.thoughtworks.gauge.test.common.Concept concept = new ConceptBuilder()
                .withName(conceptName)
                .withSteps(steps)
                .build();

        List<String> value = (List<String>) DataStoreFactory.getScenarioDataStore().get(conceptName);

        List<String> conceptNameList = value !=null ? value : new ArrayList<>();
        conceptNameList.add(concept.getConceptFile().getAbsolutePath());

        DataStoreFactory.getScenarioDataStore().put(conceptName,conceptNameList);

    }
}
