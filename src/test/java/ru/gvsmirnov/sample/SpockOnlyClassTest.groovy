package ru.gvsmirnov.sample

import static ru.gvsmirnov.sample.SpockOnlyClassTest.doSomeMath

/**
 * @author Gleb Smirnov <me@gvsmirnov.ru>
 */
class SpockOnlyClassTest {

    def "Math is solid"() {
        expect:
          doSomeMath(a, b) == 0

        where:
          a << (-5..5)
          b << (-5..5)
    }

}
