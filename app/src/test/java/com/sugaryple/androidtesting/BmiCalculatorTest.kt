package com.sugaryple.androidtesting

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.sugaryple.androidtesting.bmicalculator.BmiCalculator
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.Spy

class BmiCalculatorTest {

    //kotlinで spyを使うと色々エラーが出ます。
    //そのエラーを解決ために一つ対応が必要です。(http://bit.ly/2OXGUJ6)
    @Spy
    private val calculator = BmiCalculator()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun 単純な値を渡してBMIが計算する() {
        val result = calculator.calculate(1f, 1f)
        assertNotNull(result)
        verify(calculator, times(1)).createValueObj(1f)
    }
}