package ru.gvsmirnov.sample.groovy

import spock.lang.Specification

import static ru.gvsmirnov.sample.groovy.SampleGroovyClass.doSomeMath

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
class SampleGroovyClassSpockTest extends Specification {

    def "Math is solid"() {
        expect:
          doSomeMath(a, b) == 0

        where:
          a << (-5..5)
          b << (-5..5)
    }

}
