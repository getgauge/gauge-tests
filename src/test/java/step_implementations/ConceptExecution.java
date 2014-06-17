package step_implementations;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import common.Concept;
import common.GaugeProject;
import common.Specification;

import java.io.IOException;


public class ConceptExecution {

    private final GaugeProject currentProject;
    private Specification spec;

    public ConceptExecution() {
        this.currentProject = GaugeProject.getCurrentProject();
    }

    @Step("Create concept <concept name> with following steps <steps table>")
    public void createConcept(String conceptName,Table steps) throws Exception {

        if(steps.getRows().size() == 0 ){
            throw new RuntimeException("Expected rows empty in table");
        }
        Concept concept = currentProject.createConcept(conceptName,steps);
        currentProject.addConcepts(concept);
    }
}
