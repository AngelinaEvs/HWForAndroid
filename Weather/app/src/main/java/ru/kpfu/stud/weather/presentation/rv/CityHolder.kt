package ru.kpfu.stud.weather.presentation.rv

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_check_details.*
import kotlinx.android.synthetic.main.city_pattern.*
import ru.kpfu.stud.weather.R
import ru.kpfu.stud.weather.data.model.entity.WeatherEntity

class CityHolder(
    override val containerView: View,
    private val action: (Int) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(city: WeatherEntity) {
        with(city) {
            name_city.text = name
            temp_rv.text = temp.toString() + '°'
            var t = temp
            if (t < -25) temp_rv.setTextColor(Color.parseColor("#200b3a"))
            if (t >= -25 && t <= -15) temp_rv.setTextColor(Color.BLUE)
            if (t >= -14 && t <= -5) temp_rv.setTextColor(Color.CYAN)
            if (t >= -4 && t <= 5) temp_rv.setTextColor(Color.YELLOW)
            if (t >= 6) temp_rv.setTextColor(Color.GREEN)
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
                temp_rv.text = it
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, action: (Int) -> Unit): CityHolder =
            CityHolder(LayoutInflater.from(parent.context).inflate(R.layout.city_pattern, parent, false), action)
    }


}