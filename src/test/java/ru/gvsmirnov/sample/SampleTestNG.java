package ru.gvsmirnov.sample;

import org.testng.Assert;
import org.testng.annotations.Test;

import static ru.gvsmirnov.sample.SampleSmallClass.doSomeMath;

public class SampleTestNg {

    @Test(groups = {"alpha"})
    public void dummyAssertionAlpha() {
        final int expected = 0;

        for(int a = -5; a <= 5; a ++) {
            for(int b = -5; b <= 5; b ++) {
                Assert.assertEquals(expected, doSomeMath(a, b));
            }
        }
    }

    @Test(groups = {"beta"})
    public void dummyAssertionBeta() {
        throw new RuntimeException("Group beta should be excluded");
    }

}
