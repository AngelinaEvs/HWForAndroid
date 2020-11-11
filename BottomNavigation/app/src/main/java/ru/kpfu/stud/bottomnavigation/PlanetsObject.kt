package ru.kpfu.stud.bottomnavigation

import java.util.*

object PlanetsObject {

    var planetsObject: LinkedList<Planet> = LinkedList()

    init {
        planetsObject.add(Planet("Меркурий", "1", R.drawable.mercur))
        planetsObject.add(Planet("Венера", "2", R.drawable.venera))
        planetsObject.add(Planet("Земля", "3", R.drawable.earth))
        planetsObject.add(Planet("Марс", "4", R.drawable.mars))
        planetsObject.add(Planet("Юпитер", "5", R.drawable.yupiter))
        planetsObject.add(Planet("Сатурн", "6", R.drawable.saturn))
        planetsObject.add(Planet("Уран", "7", R.drawable.uran))
        planetsObject.add(Planet("Нептун", "8", R.drawable.neptune))
    }

    fun addElement(planet: Planet, index: Int) {
        planetsObject.add(index - 1, planet)
    }

    fun getElements(): Collection<Planet> = planetsObject

}