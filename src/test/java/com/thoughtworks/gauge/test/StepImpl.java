package com.thoughtworks.gauge.test;

import java.util.ArrayList;
import java.util.List;

public class StepImpl {

    private String stepText;
    private String implementation;
    private boolean continueOnFailure;
    private boolean isValidStatement;
    private List<String> errorTypes;

    public StepImpl(String stepText, String implementation, boolean continueOnFailure, boolean isValidStatement, String errorTypes) {
        this.stepText = stepText;
        this.implementation = implementation;
        this.continueOnFailure = continueOnFailure;
        this.isValidStatement = isValidStatement;
        this.errorTypes = new ArrayList<String>();
        this.errorTypes.add(errorTypes);
    }

    public String getStepText() {
        return stepText;
    }

    public String getImplementation() {
        return implementation;
    }

    public boolean isContinueOnFailure() {
        return continueOnFailure;
    }

    public boolean isValidStatement() {
        return isValidStatement;
    }

    public List<String> getErrorTypes(){
        return errorTypes;
    }
}
