package com.itis.template.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itis.template.R
import kotlinx.android.synthetic.main.activity_check_details.*
import kotlinx.android.synthetic.main.city_pattern.*

class CheckDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_details)
        if (intent != null) {
            temp_view.text = intent.getIntExtra("TEMP", 0).toString() + '°'
            temp_view.text = intent.getStringExtra("FEEL") + '°'
            descr.text = intent.getStringExtra("DESCR")
            city_view.text = intent.getStringExtra("NAME")
            wind_view.text = intent.getStringExtra("WIND_SPEED") + " м/с, " + intent.getStringExtra("WIND_DEG")
            feel_view.text = intent.getStringExtra("FEEL") + '°'
        }
    }
}