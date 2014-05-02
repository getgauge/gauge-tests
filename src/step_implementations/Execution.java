package step_implementations;

import com.thoughtworks.twist2.Step;
import common.GaugeProject;

import static org.junit.Assert.assertTrue;

public class Execution {

    private final GaugeProject currentProject;

    public Execution() {
        this.currentProject = GaugeProject.getCurrent();
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
