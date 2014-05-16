package common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Specification {

    private String name;
    private ArrayList<Scenario> scenarios = new ArrayList<Scenario>();
    private ArrayList<String> contextSteps = new ArrayList<String>();
    private File specFile = null;

    public Specification(String name) {
        this.name = name;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public String getName() {
        return name;
    }

    public void addScenarios(Scenario... newScenarios) {
        for (Scenario scenario : newScenarios) {
            scenarios.add(scenario);
        }
    }



    public void addContextSteps(String... newContextSteps) {
        for (String contextStep : newContextSteps) {
            contextSteps.add(contextStep);
        }
    }

    @Override
    public String toString() {
        StringBuilder specText = new StringBuilder();
        specText.append("# ").append(name).append("\n\n");
        for (String contextStep : contextSteps) {
            specText.append("* ").append(contextStep).append("\n");
        }
        specText.append("\n");

        for (Scenario scenario : scenarios) {
            specText.append("## ").append(scenario.getName()).append("\n\n");
            for (String step : scenario.getSteps()) {
                specText.append("* ").append(step).append("\n");
            }
            specText.append("\n");
        }

        specText.append("\n");
        return specText.toString();
    }

    public void saveAs(File file) throws IOException {
        Util.writeToFile(file.getAbsolutePath(), this.toString());
        this.specFile = file;
    }

    public void save() throws IOException {
        if (specFile == null) {
            throw new RuntimeException("Don't know where to save the spec to");
        }
        saveAs(specFile);
    }

    public File getSpecFile() {
        return specFile;
    }
}
