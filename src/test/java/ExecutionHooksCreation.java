import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;

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

    @Step("Create <spec> level <before> hook with implementation <inside before spec hook> with <aggregation> aggregated tags <table>")
    public void createHooksWithAndTags(String hookLevel, String hookType, String printString, String aggregation, Table tags) throws Exception {
        currentProject.createHooksWithTagsAndPrintMessage(hookLevel, hookType, printString, aggregation, tags);
    }
}
