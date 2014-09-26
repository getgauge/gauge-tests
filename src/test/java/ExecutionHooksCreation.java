import com.thoughtworks.gauge.Step;

import static common.GaugeProject.currentProject;


public class ExecutionHooksCreation {

    @Step("Create <hook level> level <hook type> hook with implementation <print string>")
    public void createHookWithImpl(String hookLevel, String hookType, String printString) throws Exception {
        currentProject.createHookWithPrint(hookLevel, hookType, printString);
    }

    @Step("Create <hook level> level <hook type> hook with exception")
    public void createHookWithException(String hookLevel, String hookType) throws Exception {
        currentProject.createHookWithException(hookLevel, hookType);
    }
}
