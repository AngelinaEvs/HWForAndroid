package ru.kpfu.stud.decepticons.baseproject

open class ParentClass(var name: String? = "Name", var coefficient: Int? = null) {

    fun printInfo(): String {
        return "$name - name, $coefficient - coefficient. "
    }

}