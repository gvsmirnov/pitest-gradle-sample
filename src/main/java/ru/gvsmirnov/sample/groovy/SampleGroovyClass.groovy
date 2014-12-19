package ru.gvsmirnov.sample.groovy

import ru.gvsmirnov.sample.SampleJavaClass

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
class SampleGroovyClass {

    static def doSomeMath(a, b) {
        a + b + SampleJavaClass.doSomeMath(a, b) - a - b
    }

}
