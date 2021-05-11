package ru.itis.navigationhw.presentation

import androidx.fragment.app.Fragment
import ru.itis.navigationhw.presentation.main.cicerone.FirstFragment
import ru.itis.navigationhw.presentation.main.cicerone.FourthFragment
import ru.itis.navigationhw.presentation.main.cicerone.SecondFragment
import ru.itis.navigationhw.presentation.main.cicerone.ThirdFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object MainScreen : SupportAppScreen() {
//        override fun getActivityIntent(context: Context): Intent {
//            return Intent(context, LoginActivity::class.java)
//        }
    }

    object ProfileScreen : SupportAppScreen() {

    }

    object FirstFragment : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return FirstFragment()
        }
    }

    object SecondFragment : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SecondFragment()
        }
    }

    object ThirdFragment : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ThirdFragment()
        }
    }

    object FourthFragment : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return FourthFragment()
        }
    }

//    data class SignInScreen(val email: String? = null) : SupportAppScreen() {
//        override fun getFragment(): Fragment = SignInFragment.newInstance(email)
//    }

}
