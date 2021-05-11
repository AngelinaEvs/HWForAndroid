package ru.itis.navigationhw.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import ru.itis.navigationhw.R

class SignUpFragment : Fragment() {
    lateinit var emailData: EditText
    lateinit var passData: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var bundle = Bundle()
        var emailData = view.findViewById<EditText>(R.id.emailUpEdit)
        var passData = view.findViewById<EditText>(R.id.passwordUpEdit)
        view.findViewById<Button>(R.id.ok).setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(emailData.text.toString(), passData.text.toString())
            findNavController().navigate(action)
        }
    }

}