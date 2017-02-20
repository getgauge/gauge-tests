package com.thoughtworks.gauge.test.common;

/**
 * Entry point for assertions of different data types. Each method in this class is a static factory for the
 * type-specific assertion objects.
 */
public class Assertions {

    /**
     * Creates a new instance of <code>{@link com.thoughtworks.gauge.test.common.ExecutionSummaryAssert}</code>.
     *
     * @param actual the actual value.
     * @return the created assertion object.
     */
    public static com.thoughtworks.gauge.test.common.ExecutionSummaryAssert assertThat(com.thoughtworks.gauge.test.common.ExecutionSummary actual) {
        return new com.thoughtworks.gauge.test.common.ExecutionSummaryAssert(actual);
    }

    /**
     * Creates a new <code>{@link Assertions}</code>.
     */
    protected Assertions() {
        // empty
    }
}
