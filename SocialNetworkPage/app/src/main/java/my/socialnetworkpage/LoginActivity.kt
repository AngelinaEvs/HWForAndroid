package my.socialnetworkpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.name
import my.socialnetworkpage.ListOfUsers.users

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        go.setOnClickListener { goToPage() }
        register.setOnClickListener { toRegister() }
    }

    private fun goToPage() {
        val nameEnter = name.text.toString()
        val emailEnter = email_or_phone.text.toString()
        val passwordEnter = password.text.toString()
        val userEnter = User(nameEnter, emailEnter, passwordEnter, R.drawable.no_photo, null, null, null, "0 друзей", "0 подписчиков")
        var isHasUser = false
        users.forEach {
            if (userEnter == it) {
                isHasUser = true
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("name", it.name)
                    putExtra("photo", it.photo)
                    putExtra("idPage", it.idPage)
                    putExtra("education", it.education)
                    putExtra("city", it.city)
                    putExtra("friends", it.friends)
                    putExtra("followers", it.followers)
                }
                startActivity(intent)
                finish()
            }
        }
        if (!isHasUser) Toast.makeText(this, "Пользователь не найден: перепроверьте введённый данные или зарегестрируйтесь", Toast.LENGTH_LONG).show()
    }

    private fun toRegister() {
        val nameEnter = name.text.toString()
        val emailEnter = email_or_phone.text.toString()
        val passwordEnter = password.text.toString()
        val userEnter = User(nameEnter, emailEnter, passwordEnter, R.drawable.no_photo, null, null, null, "0 друзей", "0 подписчиков")
        val regexEmail = Regex(pattern = "^([a-zA-Z][a-zA-Z0-9-]{0,61}[a-zA-Z0-9].)*[a-zA-Z][a-zA-Z0-9-]{0,61}[a-zA-Z0-9]@([a-zA-Z][a-zA-Z0-9-]{0,61}[a-zA-Z0-9].)+[a-zA-Z]{2,6}$")
        val regexPassword = Regex(pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{6,}$")
        var isHasUser = false
        if (nameEnter.isNotEmpty() && emailEnter.isNotEmpty() && passwordEnter.isNotEmpty()) {
            if (regexEmail.containsMatchIn(emailEnter) && regexPassword.containsMatchIn(passwordEnter)) {
                users.forEach {
                    if (userEnter == it) isHasUser = true
                }
                if (!isHasUser) {
                    users.add(userEnter)
                    Toast.makeText(this, "Вы успешно зарегестрировались!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("name", nameEnter)
                        putExtra("photo", userEnter.photo)
                        putExtra("idPage", userEnter.idPage)
                        putExtra("education", userEnter.education)
                        putExtra("city", userEnter.city)
                        putExtra("friends", userEnter.friends)
                        putExtra("followers", userEnter.followers)
                    }
                    startActivity(intent)
                    finish()
                } else Toast.makeText(this, "Такой пользователь уже зарегестрирован", Toast.LENGTH_LONG).show()
            } else Toast.makeText(this, "Заполните все поля верно, пожалуйста", Toast.LENGTH_LONG).show()
        } else Toast.makeText(this, "Заполните все поля, пожалуйста", Toast.LENGTH_LONG).show()
    }
}
