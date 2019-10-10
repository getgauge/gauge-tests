package com.thoughtworks.gauge.test.common;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;

import java.io.File;
import java.io.IOException;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.ExecutionContext;

import org.apache.commons.io.FileUtils;

public class Hooks {
    @AfterScenario
    public void tearDown(ExecutionContext context) throws IOException {
        if(getCurrentProject()==null)
        {
            System.out.println("Current project is unavailable");
            return;
        }
        File dir = getCurrentProject().getProjectDir();
        if(context.getCurrentScenario()!=null && context.getCurrentScenario().getIsFailing())
        {
            String copyFrom = Util.combinePath(dir.getAbsolutePath(), System.getenv("logs_directory"));
            String copyTo = Util.combinePath("./testLogs",context.getCurrentSpecification().getName().replaceAll(" ", "_"),context.getCurrentScenario().getName().replaceAll(" ", "_"));

            File subProjectLog = new File(copyFrom);
            if(subProjectLog.exists())
                FileUtils.copyDirectory(subProjectLog, new File(copyTo));
        }
        else {
            File testLogs = new File("./testLogs");
            if(!testLogs.exists())
                testLogs.mkdir();
        }
        if (getCurrentProject().getService() != null) {
            try {
                getCurrentProject().getService().getGaugeProcess().destroyForcibly().waitFor();
            } catch (InterruptedException e) {
                System.out.println(String.format("Unable to stop gauge process for %s; reason : %s", dir.getAbsolutePath(), e.getMessage()));
            }
        }
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            System.out.println(String.format("Could not delete project directory %s; reason : %s", dir.getAbsolutePath(), e.getMessage()));
        }
    }
}
