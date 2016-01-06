package com.thoughtworks.gauge.test.common;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private String name;
    ArrayList<String> steps = new ArrayList<String>();
    ArrayList<String> tags = new ArrayList<String>();

    public Scenario(String name) {
        this.name = name;
    }

    public ArrayList<String> getTags() {
        return tags;
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

    public void addTags(String newTags) {
        tags.add(newTags);
    }
}
