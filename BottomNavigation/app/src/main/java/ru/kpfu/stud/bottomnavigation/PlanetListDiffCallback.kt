package ru.kpfu.stud.bottomnavigation

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil

class PlanetListDiffCallback(
    private var oldList: List<Planet>,
    private var newList: List<Planet>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].name == newList[newItemPosition].name
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val bundle = Bundle().apply {
            if (oldList[oldItemPosition].name != (newList[newItemPosition].name)) {
                putString("ARG_NAME", newList[newItemPosition].name)
            }
            if (oldList[oldItemPosition].info != (newList[newItemPosition].info)) {
                putString("ARG_INFO", newList[newItemPosition].info)
            }

        }
        return if (bundle.isEmpty) null else bundle
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
