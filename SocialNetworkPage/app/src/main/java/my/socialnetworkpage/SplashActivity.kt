package my.socialnetworkpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.socialnetworkpage.ListOfUsers.users

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        users.add(User("Angelina Evsikova", "angelina300102@mail.ru", "123Abc", R.drawable.avatar,
            "evsikovaa", "КФУ (бывш. КГУ им. Ульянова-Ленина) \'23",
            "Казань", "161 друг", "345 подписчиков"))
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
