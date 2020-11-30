package ru.kpfu.stud.musicplayer2

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SongAdapter(
    private val list: List<Song>,
    private val action: (String) -> Unit
) : RecyclerView.Adapter<SongHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder =
            SongHolder.create(parent, action)

    override fun onBindViewHolder(holder: SongHolder, position: Int) =
            holder.bind(list[position])

    override fun getItemCount(): Int = list.size
}