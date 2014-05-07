package step_implementations;

import com.thoughtworks.twist2.Step;
import com.thoughtworks.twist2.Table;
import common.GaugeProject;
import common.Scenario;
import common.Specification;

import java.util.List;

/**
 * Created by gurushan on 5/6/14.
 */
public class ContextExecution {

    private final GaugeProject currentProject;
    private Specification spec;

    public ContextExecution() {
        this.currentProject = GaugeProject.getCurrent();
    }

    @Step("Create a specification <spec name> with the following contexts <steps table>")
    public void createContextsInSpec(String specName , Table steps)throws Exception{

        if (steps.getColumnNames().size() != 2) {
            throw new RuntimeException("Expected two columns for table");
        }

        spec = currentProject.createSpecification(specName);

        for (List<String> rows : steps.getRows()) {
            spec.addContextSteps(rows.get(0));
            currentProject.implementStep(rows.get(0),rows.get(1));
            spec.save();
        }
    }


    @Step("Create a scenario <scenario name> in specification <spec name> with the following steps <steps table>")
    public void createScenario(String scenarioName , String specName , Table steps) throws Exception{
        spec = currentProject.findSpecification(specName);
        if (spec == null){
            spec = currentProject.createSpecification(specName);
        }

        Scenario scenario = new Scenario(scenarioName);
        for (List<String> rows : steps.getRows()) {
            scenario.addSteps(rows.get(0));
            currentProject.implementStep(rows.get(0), rows.get(1));
        }
        spec.addScenarios(scenario);
        spec.save();
    }

    @Step("Execution should execute the spec <spec name> and ensure success")
    public void executeSpec(String specName) throws Exception{
        boolean passed;
        spec = currentProject.findSpecification(specName);
        if(spec == null){
            throw new RuntimeException("Specified spec is not present");
        }
        passed = currentProject.executeSpec(specName);
        if (!passed) {
            System.out.println(currentProject.getStdOut());
            System.out.println(currentProject.getStdErr());
        }
    }
}


