import com.thoughtworks.gauge.Step;
import common.GaugeProject;

import static common.GaugeProject.currentProject;


public class ExecutionHooksCreation {

    @Step("Create <hook level> level <hook type> hook with implementation <implementation>")
    public void methodName(String hookLevel, String hookType, String implementation) throws Exception {
        currentProject.createHook(hookLevel, hookType, implementation);
    }
}
