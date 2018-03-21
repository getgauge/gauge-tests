package com.thoughtworks.gauge.test.common;

import com.thoughtworks.gauge.AfterScenario;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;

public class Hooks {
    @AfterScenario
    public void tearDown() {
        if (getCurrentProject().getService() != null)
            getCurrentProject().getService().getGaugeProcess().destroy();
        File dir = GaugeProject.getCurrentProject().getProjectDir();
        try {
            System.out.println("Deleting directory " + dir);
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            System.err.println(String.format("Could not delete project directory %s", dir.getAbsolutePath()));
        }
    }
}
