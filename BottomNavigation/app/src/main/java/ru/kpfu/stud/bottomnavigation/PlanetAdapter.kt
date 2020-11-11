package ru.kpfu.stud.bottomnavigation

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class PlanetAdapter(
        private var list: List<Planet>,
        private val click: (Int) -> Unit
) : RecyclerView.Adapter<PlanetsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetsHolder {
        return PlanetsHolder.create(parent, click)
    }

    override fun onBindViewHolder(holder: PlanetsHolder, position: Int) =
        holder.bind(list[position])

    override fun onBindViewHolder(holder: PlanetsHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) super.onBindViewHolder(holder, position, payloads)
        else {
            (payloads[0] as? Bundle)?.also {
                holder.updateFromBundle(it)
            } ?: run { super.onBindViewHolder(holder, position, payloads) }
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<Planet>) {
        val callback = PlanetListDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(callback, true)
        diffResult.dispatchUpdatesTo(this)
        list = mutableListOf<Planet>().apply { addAll(newList) }
    }

}