package ru.kpfu.stud.bottomnavigation

import java.util.*

object CardsObject {

    var cardsObject: LinkedList<Card> = LinkedList()

    init {
        cardsObject.add(Card("Samsung Galaxy A70",
                        listOf(R.drawable.a70_blue, R.drawable.a70_white, R.drawable.a70_black),
                        "Диагональ - 6.7 \", Объем оперативной памяти - 6 Гб, Емкость аккумулятора - 4500 mAh, Объем встроенной памяти 128 Гб"))

        cardsObject.add(Card("Samsung Galaxy S10",
                        listOf(R.drawable.s10_acva, R.drawable.s10_red, R.drawable.s10_white, R.drawable.s10_black),
                            "Диагональ - 6.1 \", Объем оперативной памяти - 8 Гб, Емкость аккумулятора - 3400 mAh, Объем встроенной памяти 128 Гб")
                        )
    }

    fun getCards(): Collection<Card> = cardsObject
}