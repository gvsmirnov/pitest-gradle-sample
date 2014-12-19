package ru.gvsmirnov.sample.groovy

import groovy.transform.CompileStatic
import ru.gvsmirnov.sample.SampleJavaClass

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */

@CompileStatic
public class SampleStaticCompiledGroovyClass {
    public static int doSomeMath(int a, int b) {
        a + b + SampleJavaClass.doSomeMath(a, b) - a - b
    }
}