package ru.kpfu.stud.musicplayer2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.song_pattern.*

class SongHolder(
        override val containerView: View,
        private val action: (String) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(song: Song) {
        with(song) {
            author_xml.text = author
            title_xml.text = title
            image_xml.setImageResource(image)
            itemView.setOnClickListener { action(id) }
        }
    }

    companion object {
        fun create(parent: ViewGroup, action: (String) -> Unit): SongHolder =
                SongHolder(LayoutInflater.from(parent.context).inflate(R.layout.song_pattern, parent, false), action)
    }


}