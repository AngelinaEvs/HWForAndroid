package ru.kpfu.stud.decepticons.baseproject

class HeirClass (var score: Int? = null) : ParentClass(), MyInterface {

    override fun methodFirst(): String {
        return "Score is " + ((score?.times(coefficient!!))?:"ERROR the operation cannot be performed")
    }
}