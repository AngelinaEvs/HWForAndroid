package ru.itis.navigationhw.presentation.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_auth.*
import ru.itis.navigationhw.R
import ru.itis.navigationhw.presentation.ApplicationDelegate
import ru.itis.navigationhw.presentation.Screens
import ru.itis.navigationhw.presentation.main.cicerone.FirstFragment
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class HomeFragment : Fragment() {
    private val cicerone = Cicerone.create()
    val mrouter get() = cicerone.router
    val navigatorHolder get() = cicerone.navigatorHolder
    lateinit var router: Router

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tv = view.findViewById<TextView>(R.id.toFirst)
        val toFourth = view.findViewById<TextView>(R.id.toFourth)
        router = mrouter
        tv.setOnClickListener { router.navigateTo(Screens.FirstFragment) }
        toFourth.setOnClickListener { router.navigateTo(Screens.FourthFragment) }
    }

    private val navigator = object : Navigator {

        override fun applyCommands(commands: Array<out Command>?) {
            commands?.forEach {
                when (it) {
                    is Forward -> {
                        onForward(it.screen)
                    }
                    else -> { }
                }
            }
        }

        private fun onForward(screen: Screen) {
            when (screen as SupportAppScreen) {
                is Screens.FirstFragment -> {
                    findNavController().navigate(R.id.action_homeFragment_to_firstFragment)
                }
                is Screens.FourthFragment -> {
                    findNavController().navigate(R.id.action_homeFragment_to_fourthFragment)
                }
            }
        }
    }

}