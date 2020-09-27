package my.socialnetworkpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent != null) {
            name.text = intent.getStringExtra("name")
            avatar.setImageResource(intent.getIntExtra("photo", R.drawable.avatar))
            idPage.text = intent.getStringExtra("idPage")
            city_view.text = intent.getStringExtra("city")
            education.text = intent.getStringExtra("education")
            friends_button.text = intent.getStringExtra("friends")
            followers_button.text = intent.getStringExtra("followers")
        }
        edit.setOnClickListener {
            if (city_view.visibility == View.VISIBLE) {
                city_view.visibility = View.INVISIBLE
                city_editText.setText(city_view.text)
                city_editText.visibility = View.VISIBLE
            } else {
                city_editText.visibility = View.INVISIBLE
                city_view.text = city_editText.text
                city_view.visibility = View.VISIBLE
            }
        }
    }
}
