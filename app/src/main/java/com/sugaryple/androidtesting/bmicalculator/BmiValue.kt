package com.sugaryple.androidtesting.bmicalculator

import android.support.annotation.VisibleForTesting

import java.io.Serializable

/**
 * Created by shoma2da on 2015/12/23.
 */
class BmiValue(private val mValue: Float) : Serializable {

    /**
     * BMIによる判定メッセージ返還
     */
    val message: String
        get() = if (mValue < 18.5f) {
            THIN
        } else if (18.5 <= mValue && mValue < 25) {
            NORMAL
        } else if (25 <= mValue && mValue < 30) {
            OBESITY
        } else {
            VERY_OBESITY
        }

    /**
     * 小数点以下２座までの浮動小数点値
     * @return
     */
    fun toFloat(): Float {
        val rounded = Math.round(mValue * 100)
        return rounded / 100f
    }

    companion object {

        private const val serialVersionUID = -4325336659053219895L

        @VisibleForTesting
        val THIN = "痩せ"
        @VisibleForTesting
        val NORMAL = "普通"
        @VisibleForTesting
        val OBESITY = "肥満（１度）"
        @VisibleForTesting
        val VERY_OBESITY = "肥満（２度）"
    }

}
