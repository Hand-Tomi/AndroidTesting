package com.sugaryple.androidtesting.bmicalculator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.sugaryple.androidtesting.R

class BmiMainActivity : AppCompatActivity() {

    private lateinit var mLocalBroadcastManager: LocalBroadcastManager
    private lateinit var mCalcButton: Button
    private var mReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_activity_main)

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)

        initViews()
    }

    @VisibleForTesting
    private fun initViews() {
        val weightText = findViewById<View>(R.id.text_weight) as EditText
        val heightText = findViewById<View>(R.id.text_height) as EditText
        val resultText = findViewById<View>(R.id.text_result) as TextView

        mCalcButton = findViewById<View>(R.id.button_calculate) as Button

        val buttonListener = createButtonListener(weightText, heightText, resultText)
        mCalcButton.setOnClickListener(buttonListener)
    }

    @VisibleForTesting
    private fun createButtonListener(weightText: TextView,
                                      heightText: TextView,
                                      resultText: TextView): View.OnClickListener {
        return View.OnClickListener {
            //結果取得と表示
            val result = calculateBmiValue(weightText, heightText)
            showCalcResult(resultText, result)

            //Serviceを使って保存処理
            startResultSaveService(result)
            prepareReceiveResultSaveServiceAction()
        }
    }

    @VisibleForTesting
    private fun calculateBmiValue(weightText: TextView, heightText: TextView): BmiValue {
        val weight = weightText.text.toString().toFloat()
        val height = heightText.text.toString().toFloat()
        val calculator = BmiCalculator()
        return calculator.calculate(height, weight)
    }

    @VisibleForTesting
    private fun showCalcResult(resultText: TextView, result: BmiValue) {
        val message = "${result.toFloat()} : ${result.message}体型です"
        resultText.text = message
    }

    @VisibleForTesting
    private fun startResultSaveService(result: BmiValue) {
        mCalcButton.text = "保存中です..."
        mCalcButton.isEnabled = false
        SaveBmiService.start(this@BmiMainActivity, result)
    }

    @VisibleForTesting
    private fun prepareReceiveResultSaveServiceAction() {
        val filter = IntentFilter(SaveBmiService.ACTION_RESULT)
        mReceiver = BmiSaveResultReceiver(mCalcButton).also {
            mLocalBroadcastManager.registerReceiver(it, filter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mReceiver?.let {
            mLocalBroadcastManager.unregisterReceiver(it)
        }
    }

    @VisibleForTesting
    internal class BmiSaveResultReceiver(private val mCalcButton: Button) : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent?) {
            if (intent == null) {
                return
            }

            if (!intent.hasExtra(SaveBmiService.PARAM_RESULT)) {
                return
            }

            val result = intent.getBooleanExtra(SaveBmiService.PARAM_RESULT, false)
            if (!result) {
                Toast.makeText(context, "BMI保存に失敗しました。", Toast.LENGTH_SHORT).show()
            }

            mCalcButton.text = "計算する"
            mCalcButton.isEnabled = true
        }
    }
}
