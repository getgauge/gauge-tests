package step_implementations;

import com.thoughtworks.gauge.Step;

import static common.GaugeProject.currentProject;
import static org.junit.Assert.assertTrue;

public class Execution {


    public Execution() {
    }

    @Step("Execute the current project and ensure success")
    public void executeCurrentProjectAndEnsureSuccess() throws Exception {
        boolean passed = currentProject.execute();
        if (!passed) {
            System.out.println(currentProject.getStdOut());
            System.out.println(currentProject.getStdErr());
        }
        assertTrue(passed);
    }
}
