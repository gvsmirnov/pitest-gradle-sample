package ru.gvsmirnov.sample;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static ru.gvsmirnov.sample.TestNGOnlyClass.doSomeMath;

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
public class TestNGOnlyClassTest {
    @Test
    public void testMath() {
        final int expected = 0;

        for(int a = -5; a <= 5; a ++) {
            for(int b = -5; b <= 5; b ++) {
                assertEquals(expected, doSomeMath(a, b));
            }
        }
    }
}
