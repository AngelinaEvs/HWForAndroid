package ru.kpfu.stud.recyclerviewproject

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PlanetAdapter(
    private val list: List<Planet>,
    private val action: (Int) -> Unit
) : RecyclerView.Adapter<PlanetsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetsHolder =
        PlanetsHolder.create(parent, action)

    override fun onBindViewHolder(holder: PlanetsHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size
}