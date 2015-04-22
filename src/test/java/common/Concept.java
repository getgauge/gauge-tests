package common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Concept {

    private String name;
    private File conceptFile = null;
    private List<String> steps;

    public Concept(String name) {
        this.name = name;
        steps = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void addSteps(String... newSteps) {
        for (String step : newSteps) {
            steps.add(step);
        }
    }

    @Override
    public String toString() {
        StringBuilder conceptText = new StringBuilder();
        conceptText.append("# ").append(name).append("\n");
        for (String contextStep : steps) {
            conceptText.append("* ").append(contextStep).append("\n");
        }
        conceptText.append("\n");
        return conceptText.toString();
    }

    public void saveAs(File file) throws IOException {
        Util.writeToFile(file.getAbsolutePath(), this.toString());
        this.conceptFile = file;
    }

    public File getConceptFile() {
        return conceptFile;
    }
}
