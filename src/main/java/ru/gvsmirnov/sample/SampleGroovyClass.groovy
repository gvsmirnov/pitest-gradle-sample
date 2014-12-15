package ru.gvsmirnov.sample

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
class SampleGroovyClass {

    def doSomeMath(a, b) {
        SampleSmallClass.doSomeMath(a, b) + a + b
    }

}
