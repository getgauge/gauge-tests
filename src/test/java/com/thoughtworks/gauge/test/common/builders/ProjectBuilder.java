package com.thoughtworks.gauge.test.common.builders;

import com.thoughtworks.gauge.test.common.GaugeProject;
import com.thoughtworks.gauge.test.common.Util;

import static com.thoughtworks.gauge.test.common.GaugeProject.getCurrentProject;

public class ProjectBuilder {

    private String language;
    private String projName;
    private boolean deleteExampleSpec;

    public ProjectBuilder withLangauge(String language) {
        this.language = language;
        return this;
    }

    public ProjectBuilder withProjectName(String projName) {
        this.projName = projName;
        return this;
    }

    public GaugeProject build(boolean expectFailure) throws Exception {
        GaugeProject currentProject = GaugeProject.createProject(language, projName);
        if(!currentProject.initialize() && !expectFailure)
            throw new Exception("Unable to initialize gauge project.\nSTDERR:\n\n"
                + currentProject.getLastProcessStderr() + "\n\nSTDOUT:\n\n"
                + currentProject.getLastProcessStdout());

        if(this.deleteExampleSpec)
            currentProject.deleteSpec(Util.combinePath("specs","example"));
        return currentProject;
    }

    public ProjectBuilder withoutExampleSpec() {
        this.deleteExampleSpec = true;
        return this;
    }
}
