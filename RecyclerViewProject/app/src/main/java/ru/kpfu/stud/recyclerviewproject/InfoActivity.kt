package ru.kpfu.stud.recyclerviewproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
        val id = intent?.extras?.getInt("ID", -1) ?: 0
        val planet = PlanetsObject.planetsObject.find { it.id == id }
        image_planet.setImageResource(planet!!.photo)
        val info = "Название: " + planet.name + "\nПозиция: " + planet.id.toString() + "\nСпутники:\n" + planet.satellites + "\n" + planet.structure + "\nОбщая информация:\n" + planet.info
        planet_info.text = info
    }
}
