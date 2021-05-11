package ru.itis.navigationhw.presentation.main.cicerone

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_home.*
import ru.itis.navigationhw.R
import ru.itis.navigationhw.presentation.Screens
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

class FirstFragment : Fragment() {
    private val cicerone = Cicerone.create()
    val mrouter get() = cicerone.router
    val navigatorHolder get() = cicerone.navigatorHolder
    lateinit var router: Router

    private val navigator = object : Navigator {

        override fun applyCommands(commands: Array<out Command>?) {
            commands?.forEach {
                when (it) {
                    is Forward -> {
                        onForward(it.screen)
                    }
                    is Replace -> {
                        onReplace(it.screen)
                    }
                    else -> { }
                }
            }
        }

        private fun onReplace(screen: Screen) {
            when (screen as SupportAppScreen) {
                is Screens.ThirdFragment -> {
                    findNavController().navigate(R.id.action_firstFragment_to_thirdFragment)
                }
            }
        }

        private fun onForward(screen: Screen) {
            when (screen as SupportAppScreen) {
                is Screens.SecondFragment -> {
                    findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
                }
            }
        }
    }

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
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        router = mrouter
        val toSecond = view.findViewById<TextView>(R.id.toSecond)
        val toThird = view.findViewById<TextView>(R.id.toThird)
        toSecond.setOnClickListener {
            Log.e("QQQQQQ", "QQQQQq")
            router.navigateTo(Screens.SecondFragment) }
        toThird.setOnClickListener { router.replaceScreen(Screens.ThirdFragment) }
    }

}