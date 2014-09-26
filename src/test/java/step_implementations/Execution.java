package step_implementations;

import com.thoughtworks.gauge.Step;

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
        if (!passed) {
            System.out.println(currentProject.getStdOut());
            System.out.println(currentProject.getStdErr());
        }
        return passed;
    }

    @Step("Execute the spec <spec name> and ensure failure")
    public void executeSpecAndEnsureFailure(String specName) throws Exception {
        assertTrue(!isExecutionPassed());
    }
}
