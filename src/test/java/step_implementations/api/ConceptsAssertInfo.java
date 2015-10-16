package step_implementations.api;

import com.thoughtworks.gauge.ConceptInfo;
import common.AssertInfo;

import java.io.IOException;
import java.util.List;

import static common.GaugeProject.currentProject;
import static org.junit.Assert.fail;

public class ConceptsAssertInfo implements AssertInfo<String> {
    private int waitTime;
    private int interval;
    private String[] expected;

    public ConceptsAssertInfo(int waitTime, int interval) {
        this.waitTime = waitTime;
        this.interval = interval;
    }


    public void setExpected(String[] expected) {
        this.expected = expected;
    }

    @Override
    public String[] getExpected() {
        return expected;
    }


    @Override
    public String[] getActual() {
        List<ConceptInfo> values = null;
        try {
            values = currentProject.getService().getGaugeConnection().fetchAllConcepts();
        } catch (IOException e) {
            fail();
        }
        String[] autoCompleteSteps = new String[values.size()];
        for (int i = 0, valuesSize = values.size(); i < valuesSize; i++)
            autoCompleteSteps[i] = values.get(i).getStepValue().getStepText();
        return autoCompleteSteps;
    }

    @Override
    public int getWaitTime() {
        return waitTime;
    }

    @Override
    public int getInterval() {
        return interval;
    }
}
