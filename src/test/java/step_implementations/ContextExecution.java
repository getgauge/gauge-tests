package step_implementations;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import common.Scenario;
import common.Specification;

import java.util.List;

import static common.GaugeProject.currentProject;

public class ContextExecution {

    private Specification spec;


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

    @Step("Execute the spec <spec name> and ensure success")
    public void executeSpec(String specName) throws Exception{
        boolean passed;
        spec = currentProject.findSpecification(specName);
        if(spec == null){
            throw new RuntimeException("Specified spec is not present : "+specName);
        }
        passed = currentProject.executeSpec(specName);
        if (!passed) {
            System.out.println(currentProject.getStdOut());
            System.out.println(currentProject.getStdErr());
        }
    }
}


