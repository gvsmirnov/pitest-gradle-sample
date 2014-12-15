package ru.gvsmirnov.sample

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
class SpockOnlyClass {

    public static int doSomeMath(int a, int b) {
        a + b + SampleJavaClass.doSomeMath(a, b) - a - b
    }

}
