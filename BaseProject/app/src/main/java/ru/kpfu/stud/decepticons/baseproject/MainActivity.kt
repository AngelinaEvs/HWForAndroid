package ru.kpfu.stud.decepticons.baseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val parent = ParentClass("Alina", 5)
        println(parent.printInfo())

        val child1 = HeirClass()
        println(child1.methodFirst())

        val child2 = HeirClass(4)
        child2.name = "NewNameChild2"
        println(child2.methodFirst())
    }
}
