package com.sugaryple.androidtesting.bmicalculator

import android.support.annotation.VisibleForTesting

/**
 * Created by shoma2da on 2015/12/23.
 */
class BmiCalculator {

    /**
     * BMI値を計算して返還する
     * @param heightInMeter BMI計算で使う身長
     * @param weightInKg BMI計算で使う体重
     * @return BMI値
     */
    fun calculate(heightInMeter: Float, weightInKg: Float): BmiValue {
        if (heightInMeter < 0 || weightInKg < 0) {
            throw RuntimeException("背と体重は整数で指定してください")
        }
        val bmiValue = weightInKg / (heightInMeter * heightInMeter)
        return createValueObj(bmiValue)
    }

    @VisibleForTesting
    fun createValueObj(bmiValue: Float): BmiValue {
        return BmiValue(bmiValue)
    }

}
