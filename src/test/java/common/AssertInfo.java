package common;

public interface AssertInfo<E> {
    E[] getExpected();
    E[] getActual();
    int getWaitTime();
    int getInterval();
}
