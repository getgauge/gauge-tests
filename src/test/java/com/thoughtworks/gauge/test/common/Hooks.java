package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.ExecutionContext;
import com.thoughtworks.gauge.datastore.ScenarioDataStore;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;

public class Hooks {
    @AfterScenario
    public void tearDown(ExecutionContext context) {
        GaugeProject currentProject = getCurrentProject();
        if (currentProject == null) {
            System.out.println("Current project is unavailable");
            return;
        }
        if (context.getCurrentScenario().getIsFailing()){
            System.out.println(String.format("Scenario %s failed against %s",context.getCurrentScenario().getName(), currentProject.getProjectDir().getAbsolutePath()));
            return;
        }
        currentProject.deleteFromFileSystem();
    }

    @BeforeScenario
    public void setProjectName(ExecutionContext context) {
        String folderName = Util.combinePath(context.getCurrentSpecification().getName().replaceAll(" ", "_"),
                context.getCurrentScenario().getName().replaceAll(" ", "_"));
        ScenarioDataStore.put("log_proj_name", folderName);
    }
}
