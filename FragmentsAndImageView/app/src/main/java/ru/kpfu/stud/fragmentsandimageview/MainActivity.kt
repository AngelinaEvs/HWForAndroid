package ru.kpfu.stud.fragmentsandimageview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView.setOnClickListener {
            changeColor()
            supportFragmentManager.beginTransaction().replace(R.id.fragment, Fragment_1.newInstance("1", "1")).commit()
            it.setSelected(true)
        }
        imageView2.setOnClickListener {
            changeColor()
            supportFragmentManager.beginTransaction().replace(R.id.fragment, Fragment_2.newInstance("1", "1")).commit()
            it.setSelected(true)
        }
        imageView3.setOnClickListener {
            changeColor()
            supportFragmentManager.beginTransaction().replace(R.id.fragment, Fragment_3.newInstance("1", "1")).commit()
            it.setSelected(true)
        }
        imageView4.setOnClickListener {
            changeColor()
            supportFragmentManager.beginTransaction().replace(R.id.fragment, Fragment_4.newInstance("1", "1")).commit()
            it.setSelected(true)
        }
        imageView5.setOnClickListener {
            changeColor()
            supportFragmentManager.beginTransaction().replace(R.id.fragment, Fragment_5.newInstance("1", "1")).commit()
            it.setSelected(true)
        }
    }

    fun changeColor() {
        imageView.setSelected(false)
        imageView2.setSelected(false)
        imageView3.setSelected(false)
        imageView4.setSelected(false)
        imageView5.setSelected(false)
    }
}
