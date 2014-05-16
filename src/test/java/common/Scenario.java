package common;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private String name;
    ArrayList<String> steps = new ArrayList<String>();

    public Scenario(String name) {
        this.name = name;
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

}
