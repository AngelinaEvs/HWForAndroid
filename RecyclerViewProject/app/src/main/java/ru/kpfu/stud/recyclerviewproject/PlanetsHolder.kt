package ru.kpfu.stud.recyclerviewproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.planet_pattern.*

class PlanetsHolder(
    override val containerView: View,
    private val action: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(planet: Planet) {
        with(planet) {
            name_planet.text = name
            position_planet.text = id.toString()
            planet_photo.setImageResource(photo)
            itemView.setOnClickListener { action(id) }
        }
    }

    companion object {
        fun create(parent: ViewGroup, action: (Int) -> Unit): PlanetsHolder =
            PlanetsHolder(LayoutInflater.from(parent.context).inflate(R.layout.planet_pattern, parent, false), action)
    }


}
