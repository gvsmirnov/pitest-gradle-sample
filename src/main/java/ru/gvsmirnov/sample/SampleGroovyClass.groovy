package ru.gvsmirnov.sample

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
class SampleGroovyClass {

    public static int doSomeMath(int a, int b) {
        a + b + SampleJavaClass.doSomeMath(a, b) - a - b
    }

}
