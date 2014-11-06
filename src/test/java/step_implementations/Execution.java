package step_implementations;

import com.thoughtworks.gauge.Step;
import common.Specification;

import java.io.IOException;

import static common.GaugeProject.currentProject;
import static org.junit.Assert.assertTrue;

public class Execution {


    public Execution() {
    }

    @Step("Execute the current project and ensure success")
    public void executeCurrentProjectAndEnsureSuccess() throws Exception {
        assertTrue(isExecutionPassed());
    }

    @Step("Execute the current project and ensure failure")
    public void executeCurrentProjectAndEnsureFailure() throws Exception {
        assertTrue(!isExecutionPassed());
    }

    private boolean isExecutionPassed() throws Exception {
        boolean passed = currentProject.execute();
        printProcessOutput(passed);
        return passed;
    }

    @Step("Execute the spec <spec name> and ensure success")
    public void executeSpecAndEnsureSuccess(String specName) throws Exception{
        boolean passed = executeSpec(specName);
        printProcessOutput(passed);
        assertTrue(passed);
    }


    public void printProcessOutput(boolean passed) {
        if (!passed) {
            System.out.println("*************** Process output start************");
            System.out.println(currentProject.getLastProcessStdout());
            System.out.println(currentProject.getLastProcessStderr());
            System.out.println("*************** Process output end************");
        }
    }

    @Step("Execute the spec <spec name> with scenario index <scenario index> and ensure success")
    public void executeScenarioWithIndex(String specName, int scenarioIndex) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        if(spec == null){
            throw new RuntimeException("Specified spec is not present : "+specName);
        }
        boolean passed = currentProject.executeSpecWithScenarioIndex(specName, scenarioIndex);
        printProcessOutput(passed);
        assertTrue(passed);
    }

    @Step("Execute the spec <spec name> and ensure failure")
    public void executeSpecAndEnsureFailure(String specName) throws Exception {
        assertTrue(!executeSpec(specName));
    }

    public boolean executeSpec(String specName) throws Exception {
        Specification spec = currentProject.findSpecification(specName);
        if(spec == null){
            throw new RuntimeException("Specified spec is not present : "+specName);
        }
        boolean passed = currentProject.executeSpec(specName);
        printProcessOutput(passed);
        return passed;
    }

    @Step("Execute the tags <tags> in spec <spec name> and ensure success")
    public void executeTagsAndEnsureSuccess(String tags, String specName) throws IOException, InterruptedException {
        Specification spec = currentProject.findSpecification(specName);
        if(spec == null){
            throw new RuntimeException("Specified spec is not present : "+specName);
        }
        boolean passed = currentProject.executeTagsInSpec(tags, specName);
        printProcessOutput(passed);
        assertTrue(passed);
    }

}
