package com.sugaryple.androidtesting

import junit.framework.TestCase.assertEquals
import org.junit.Test

class CalculatorTest {

    @Test
    fun evaluatesExpression() {
        val calculator = Calculator()
        val sum = calculator.evaluate("1+2+3")
        assertEquals(sum, 6)
    }

}