package common;

import java.util.Arrays;

import static org.junit.Assert.fail;

public class Assert {
    public static void till(AssertInfo info) {
        long t= System.currentTimeMillis();
        long end = t+info.getWaitTime();
        while(System.currentTimeMillis() < end) {
            Object[] expected = info.getExpected();
            Object[] actual = info.getActual();
            Arrays.sort(expected);
            Arrays.sort(actual);
            if (Arrays.equals(expected, actual))
                return;
            try {
                Thread.sleep(info.getInterval());
            } catch (InterruptedException e) {
                //ignore
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
        Object[] expected = info.getExpected();
        Object[] actual = info.getActual();
        Arrays.sort(expected);
        Arrays.sort(actual);
        System.out.println(Arrays.asList(expected));
        System.out.println(Arrays.asList(actual));
        System.out.println();
        System.out.println();
        fail();
    }
}
