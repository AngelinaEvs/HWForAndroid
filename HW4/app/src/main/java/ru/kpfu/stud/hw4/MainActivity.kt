package ru.kpfu.stud.hw4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        plus.setOnClickListener { giveAction(plus) }
        minus.setOnClickListener { giveAction(minus) }
        multi.setOnClickListener { giveAction(multi) }
        division.setOnClickListener { giveAction(division) }

    }

    fun giveAction(button: Button) {
        if (a_coef != null && b_coef != null) {
            val a = a_coef.text.toString().toDouble()
            val b = b_coef.text.toString().toDouble()
            val sign = button!!.text.toString()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra("a_double", a)
            intent.putExtra("b_double", b)
            intent.putExtra("sign", sign)
            startActivityForResult(intent, 123)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, data?.getStringExtra("string"), Toast.LENGTH_LONG).show()
        }
    }
}
