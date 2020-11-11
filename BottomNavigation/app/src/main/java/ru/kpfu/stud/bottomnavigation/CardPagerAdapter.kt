package ru.kpfu.stud.bottomnavigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.view_pager_item.view.*

class CardPagerAdapter(
    private val photos: List<Int>
) : PagerAdapter() {

    override fun getCount(): Int =
        photos.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.view_pager_item, container, false)
        view.photo_view_pager_item.setImageResource(photos[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object`
}