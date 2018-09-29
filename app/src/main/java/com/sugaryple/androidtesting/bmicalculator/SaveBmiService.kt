package com.sugaryple.androidtesting.bmicalculator

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.support.annotation.VisibleForTesting
import android.support.v4.content.LocalBroadcastManager

import java.io.Serializable
import java.util.Random

class SaveBmiService : IntentService(SaveBmiService::class.java.name) {

    lateinit var mLocalBroadcastManager: LocalBroadcastManager

    override fun onCreate() {
        super.onCreate()
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(applicationContext)
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            return
        }

        val extra = intent.getSerializableExtra(PARAM_KEY_BMI_VALUE)
        if (extra == null || extra !is BmiValue) {
            return
        }

        val result = saveToRemoteServer(extra)
        sendLocalBroadcast(result)
    }

    @VisibleForTesting
    private fun saveToRemoteServer(bmiValue: BmiValue): Boolean {
        try {
            Thread.sleep((3000 + Random().nextInt(2000)).toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        //--------------
        //実はここでサーバーに保存する処理をする
        //--------------

        return Random().nextBoolean()
    }

    @VisibleForTesting
    private fun sendLocalBroadcast(result: Boolean) {
        val resultIntent = Intent(ACTION_RESULT)
        resultIntent.putExtra(PARAM_RESULT, result)
        mLocalBroadcastManager.sendBroadcast(resultIntent)
    }

    @VisibleForTesting
    private fun setLocalBroadcastManager(manager: LocalBroadcastManager) {
        mLocalBroadcastManager = manager
    }

    companion object {

        val ACTION_RESULT = SaveBmiService::class.java.name + ".ACTION_RESULT"
        const val PARAM_RESULT = "param_result"

        private const val PARAM_KEY_BMI_VALUE = "bmi_value"

        fun start(context: Context, bmiValue: BmiValue) {
            val intent = Intent(context, SaveBmiService::class.java)
            intent.putExtra(PARAM_KEY_BMI_VALUE, bmiValue)
            context.startService(intent)
        }
    }

}
