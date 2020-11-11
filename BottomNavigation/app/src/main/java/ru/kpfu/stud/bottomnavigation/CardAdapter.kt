package ru.kpfu.stud.bottomnavigation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CardAdapter(
    private var list: List<Card>
) : RecyclerView.Adapter<CardHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder =
        CardHolder.create(parent)

    override fun onBindViewHolder(holder: CardHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = list.size

}