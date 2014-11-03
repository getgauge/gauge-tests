package step_implementations;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import common.Concept;
import common.GaugeProject;
import common.Specification;

import java.io.IOException;

import static common.GaugeProject.getCurrentProject;


public class ConceptExecution {

    private Specification spec;

    @Step("Create concept <concept name> with following steps <steps table>")
    public void createConcept(String conceptName, Table steps) throws Exception {
        Concept concept = getCurrentProject().createConcept(conceptName, steps);
        getCurrentProject().addConcepts(concept);
    }

}
