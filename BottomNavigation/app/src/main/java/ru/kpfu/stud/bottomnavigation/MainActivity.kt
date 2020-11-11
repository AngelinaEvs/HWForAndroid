package ru.kpfu.stud.bottomnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragment, BlankFragment()).commit()
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.star -> {
                    onStarClick()
                    true
                }
                R.id.news -> {
                    onNewsClick()
                    true
                }
                R.id.message -> {
                    onMessageClick()
                    true
                }
                else -> false
            }
        }
    }

    fun onStarClick() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, BlankFragment()).commit()
    }

    fun onNewsClick() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, BlankFragment2()).commit()
    }

    fun onMessageClick() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, BlankFragment3()).commit()
    }

}
