package com.sugaryple.androidtesting

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculator = Calculator()

        toast(calculator.evaluate("11+12+13"))
    }

    private fun toast(text: Any) {
        Toast.makeText(this, text.toString(), Toast.LENGTH_LONG).show()
    }
}
