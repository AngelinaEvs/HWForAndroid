package com.itis.template.recycler

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.template.R
import com.itis.template.WeatherResponse
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.city_pattern.*

class CityHolder(
    override val containerView: View,
    private val action: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(city: WeatherResponse) {
        with(city) {
            name_city.text = name
            temp.text = main.temp.toInt().toString() + '°'
            var t = main.temp.toInt()
            if (t < -25) temp.setTextColor(Color.parseColor("#200b3a"))
            if (t >= -25 && t <= -15) temp.setTextColor(Color.BLUE)
            if (t >= -14 && t <= -5) temp.setTextColor(Color.CYAN)
            if (t >= -4 && t <= 5) temp.setTextColor(Color.YELLOW)
            if (t >= 6) temp.setTextColor(Color.GREEN)
            itemView.setOnClickListener { action(id) }
        }
    }

    fun updateFromBundle(bundle: Bundle) {
        if (bundle.containsKey("ARG_NAME")) {
            bundle.getString("ARG_NAME").also {
                name_city.text = it
            }
        }
        if (bundle.containsKey("ARG_INFO")) {
            bundle.getString("ARG_INFO").also {
                temp.text = it
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, action: (Int) -> Unit): CityHolder =
            CityHolder(LayoutInflater.from(parent.context).inflate(R.layout.city_pattern, parent, false), action)
    }


}