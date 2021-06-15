package com.example.ceta.loginFragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ceta.R
import com.example.ceta.databinding.LoginFragmentBinding
import com.example.ceta.query.Login

class loginFragment : Fragment() {
    private lateinit var binding: LoginFragmentBinding

    companion object {
        fun newInstance() = loginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_fragment,
            container,
            false
        )
        val email_edt = binding.email
        val password_edt = binding.password
        val login_btn = binding.loginBtn
        val args by navArgs<loginFragmentArgs>()
        val from = args.from.toString()

        email_edt.addTextChangedListener(textWatcher)
        password_edt.addTextChangedListener(textWatcher)

        binding.backbotton.setOnClickListener {
            if (from == "detail") {
                val s = loginFragmentDirections.loginTodetail("${args.carName}",
                    "${args.city}",
                    "${args.search}")
                findNavController().navigate(s)
            } else {
                findNavController().navigate(R.id.loginToprofile)
            }
        }
        binding.password.setOnClickListener {
            binding.passwordLayout.error = null
        }

        if (from == "detail") {
            binding.textView4.isVisible = true
            binding.btnRegister.isVisible = true
        }

        binding.btnRegister.setOnClickListener {
            val s = loginFragmentDirections.loginToregister("login","${args.carName}","${args.city}","${args.search}")
            findNavController().navigate(s)
        }
        login_btn.setOnClickListener {
            var login = Login()
            login.Login_check(email_edt.text.toString(), password_edt.text.toString())
            if (login.email != null) {
                if (from == "detail") {
                    val s = loginFragmentDirections.loginTodetail("${args.carName}",
                        "${args.city}",
                        "${args.search}")
                    findNavController().navigate(s)
                    Log.i("apptest",from)
                    onLoginFinished()
                } else {
                    Log.i("aaa",email_edt.text.toString())
                    Log.i("apptest",from+"asdasdsad")
                    findNavController().navigate(R.id.profileFragment)
                    onLoginFinished()
                }
            } else {
                binding.passwordLayout.error = "Wrong email or password. Try again."
                password_edt.requestFocus()
                password_edt.setText("")
            }
        }
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }
    private val textWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email: String = binding.email.text.toString()
            val password: String = binding.password.text.toString()

            if (!email.isEmpty() && !password.isEmpty()) {
                binding.loginBtn.isEnabled = true
            }
        }
    }

    private fun onLoginFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Login", true)
        editor.putString("Email", binding.email.text.toString())
        editor.apply()
    }

}

