package ru.kpfu.stud.bottomnavigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_item.*

class CardHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(card: Card) {
        with(card) {
            view_pager.adapter = CardPagerAdapter(photos)
            name_card.text = name
            descr_card.text = descr
        }
        val circlePageIndicator: CirclePageIndicator = indicator as CirclePageIndicator
        circlePageIndicator.setViewPager(view_pager)
    }

    companion object {
        fun create(parent: ViewGroup): CardHolder =
            CardHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false))

    }

}
