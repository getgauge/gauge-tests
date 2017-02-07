package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.AfterScenario;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static com.thoughtworks.gauge.test.common.GaugeProject.currentProject;

public class Hooks {
    @AfterScenario
    public void delete() {
        File dir = GaugeProject.getCurrentProject().getProjectDir();
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            System.err.println(String.format("Could not delete project directory %s", dir.getAbsolutePath()));
        }
    }

    @AfterScenario
    public void tearDown() {
        if (currentProject.getService() != null)
            currentProject.getService().getGaugeProcess().destroy();
    }

}
