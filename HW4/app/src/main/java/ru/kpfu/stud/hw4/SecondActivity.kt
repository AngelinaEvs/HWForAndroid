package ru.kpfu.stud.hw4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val a = intent.getDoubleExtra("a_double", 0.0)
        val b = intent.getDoubleExtra("b_double", 0.0)
        val sign = intent.getStringExtra("sign")
        if (sign == "+") res.text = (a + b).toString()
        if (sign == "-") res.text = (a - b).toString()
        if (sign == "*") res.text = (a * b).toString()
        if (sign == "/") res.text = String.format("%.3f", a / b)
        back.setOnClickListener { back() }
    }

    private fun back() {
        setResult(Activity.RESULT_OK, Intent().apply { putExtra("string", "Вы вернулись назад ;)") })
        finish()
    }

}

