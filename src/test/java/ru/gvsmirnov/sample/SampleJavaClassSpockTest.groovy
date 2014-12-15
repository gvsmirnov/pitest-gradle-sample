package ru.gvsmirnov.sample

import spock.lang.Specification

import static SampleJavaClass.doSomeMath

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
class SampleJavaClassSpockTest extends Specification {

    def "Math is solid"() {
        expect:
          doSomeMath(a, b) == 0

        where:
          a << (-5..5)
          b << (-5..5)
    }

}
