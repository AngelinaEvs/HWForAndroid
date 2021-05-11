package ru.itis.navigationhw.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.itis.navigationhw.R

class SignInFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: SignInFragmentArgs by navArgs()
        val email = view.findViewById<EditText>(R.id.emailEdit)
        val password = view.findViewById<EditText>(R.id.passwordEdit)
        email.setText(args.userEmail)
        password.setText(args.userPassword)
        view.findViewById<Button>(R.id.ok).setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_mainActivity3)
        }
    }

}