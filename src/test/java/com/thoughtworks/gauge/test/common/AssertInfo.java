package com.thoughtworks.gauge.test.common;

public interface AssertInfo<E> {
    void setExpected(String[] expected);

    E[] getExpected();

    E[] getActual();

    int getWaitTime();

    int getInterval();
}
