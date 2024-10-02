package com.thoughtworks.gauge.test.common;

import java.util.ArrayList;
import java.util.List;

public class StepValueExtractor {
    public List<String> getValueFor(String[] values) {
        List<String> stepValues = new ArrayList<>();
        for (String value : values) {
            stepValues.add(getFor(value).value);
        }
        return stepValues;
    }

    public StepValue getFor(String value) {
        StringBuilder extractedValue = new StringBuilder();
        char[] chars = value.toCharArray();
        boolean inEscape = false;
        boolean inQuotes = false;
        boolean inBracket = false;
        int count = 0;
        for (char aChar : chars) {
            count++;
            if (inEscape) {
                inEscape = false;
                if (!inQuotes && !inBracket)
                    extractedValue.append(aChar);
            } else if (aChar == '"') {
                if (!inQuotes) {
                    inQuotes = true;
                } else {
                    extractedValue.append("<param").append(count).append(">");
                    inQuotes = false;
                }
            } else if (aChar == '<' && !inBracket) {
                inBracket = true;
            } else if (aChar == '>' && inBracket) {
                extractedValue.append("<param").append(count).append(">");
                inBracket = false;
            } else if (aChar == '\\') {
                inEscape = true;
            } else if (!inQuotes && !inBracket) {
                extractedValue.append(aChar);
            }
        }
        StepValue v = new StepValue();
        v.value = extractedValue.toString();
        v.paramCount = extractedValue.toString().split("<").length - 1;
        return v;
    }

    public static class StepValue {
        public int paramCount;
        public String value;
    }
}
