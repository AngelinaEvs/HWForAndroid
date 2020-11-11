package ru.kpfu.stud.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.add_item.*
import kotlinx.android.synthetic.main.planet_pattern.*

class PlanetsHolder(
    override val containerView: View,
    private val onClickItem: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(planet: Planet) {
        with(planet) {
            name_planet.text = name
            position_planet.text = info
            planet_photo.setImageResource(photo)
        }
        delete.setOnClickListener {
            onClickItem(adapterPosition)
        }

    }

    fun updateFromBundle(bundle: Bundle) {
        if (bundle.containsKey("ARG_NAME")) {
            bundle.getString("ARG_NAME").also {
                name_planet.text = it
            }
        }
        if (bundle.containsKey("ARG_INFO")) {
            bundle.getString("ARG_INFO").also {
                position_planet.text = it
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, onClickItem: (Int) -> Unit): PlanetsHolder =
            PlanetsHolder(LayoutInflater.from(parent.context).inflate(R.layout.planet_pattern, parent, false), onClickItem)

    }


}
