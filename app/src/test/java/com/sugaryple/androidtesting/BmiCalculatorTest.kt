package com.sugaryple.androidtesting

import android.content.Intent
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.sugaryple.androidtesting.bmicalculator.BmiCalculator
import com.sugaryple.androidtesting.bmicalculator.BmiValue
import com.sugaryple.androidtesting.bmicalculator.SaveBmiService
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
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

    @Test
    fun onHandleIntentに正しくデータが入ったIntentを伝達すればデータ保存とBroadcastができます() {
        //(1)準備：SaveBmiServiceに伝達するIntentを準備
        val bmiValue = mock(BmiValue::class.java)
        val intent = mock(Intent::class.java)
        `when`(intent.getSerializableExtra(SaveBmiService.PARAM_KEY_BMI_VALUE)).thenReturn(bmiValue)

        //(2)準備：SaveBmiServiceの各メソッドは何もしないようにする®
        val service = spy(SaveBmiService())
        doReturn(false).`when`(service).saveToRemoteServer(any())
        doNothing().`when`(service).sendLocalBroadcast(anyBoolean())

        //(3)テストとメソッド呼び出し確認
        service.onHandleIntent(intent)
        verify(service, times(1)).sendLocalBroadcast(anyBoolean())
        verify(service, times(1)).saveToRemoteServer(any())
    }

    /*
        Mockitoのanyを使ったら「must not be null」エラーが出ますので
        対応するように関数を追加する
     */
    private fun <T> any(): T {
        return Mockito.any<T>()
    }
}