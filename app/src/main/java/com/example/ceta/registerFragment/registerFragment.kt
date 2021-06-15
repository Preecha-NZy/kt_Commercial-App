package com.example.ceta.registerFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ceta.R
import com.example.ceta.databinding.RegisterFragmentBinding
import com.example.ceta.query.Login
import com.example.ceta.query.register


class registerFragment : Fragment() {
    private lateinit var binding: RegisterFragmentBinding

    companion object {
        fun newInstance() = registerFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.register_fragment,
            container,
            false)
        val args by navArgs<registerFragmentArgs>()
        binding.email.addTextChangedListener(textWatcher)
        binding.password.addTextChangedListener(textWatcher)
        binding.confirmPassword.addTextChangedListener(textWatcher)
        binding.fname.addTextChangedListener(textWatcher)
        binding.lname.addTextChangedListener(textWatcher)
        binding.phone.addTextChangedListener(textWatcher)
        binding.passCheck.isChecked = false

        binding.email.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                binding.backbotton.isVisible = true
                val conn = Login()
                conn.Register_check(binding.email.text.toString())
                if (conn.email != null) {
                    binding.emailLayout.error = "That email is taken. Try another."
                } else {
                    binding.emailLayout.error = null
                }
            }
        }

        binding.passCheck.setOnClickListener {
            if (binding.passCheck.isChecked) {
                binding.password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.confirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.confirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        binding.backbotton.setOnClickListener {
            if (args.from == "login") {
                val s = registerFragmentDirections.regisTologin("detail",
                    "${args.carName}",
                    "${args.city}",
                    "${args.search}")
                findNavController().navigate(s)
            } else {
                findNavController().navigate(R.id.profileFragment)
            }
        }

        binding.btnNext1.setOnClickListener {
            if (binding.email.text.toString().isNotEmpty() &&
                binding.password.text.toString().isNotEmpty()
            ) {
                if (binding.password.text.toString() == binding.confirmPassword.text.toString() &&
                    binding.emailLayout.error == null
                ) {
                    binding.register1.isVisible = false
                    binding.register2.isVisible = true
                } else if (binding.emailLayout.error != null) {
                    binding.email.requestFocus()
                    binding.confrimLayout.error = "Those passwords didn't match. Try again."
                } else if (binding.password.text.toString() != binding.confirmPassword.text.toString()) {
                    binding.confrimLayout.error = "Those passwords didn't match. Try again."
                    binding.confirmPassword.requestFocus()
                    binding.confirmPassword.setText("")
                }
            } else {
                Log.i("aaa", "aa")
            }
        }

        binding.btnNext2.setOnClickListener {
            if (binding.fname.text.toString().isNotEmpty() &&
                binding.lname.text.toString().isNotEmpty() &&
                binding.phone.text.toString().isNotEmpty()
            ) {
                val txt_email: String = binding.email.text.toString()
                val txt_pass: String = binding.password.text.toString()
                val txt_fname: String = binding.fname.text.toString()
                val txt_lname: String = binding.lname.text.toString()
                val txt_phone: String = binding.phone.text.toString()
                val r = register()
                r.insert_register(txt_email, txt_pass, txt_fname, txt_lname, txt_phone)
                if (args.from == "login") {
                    val s = registerFragmentDirections.regisTologin("detail",
                        "${args.carName}",
                        "${args.city}",
                        "${args.search}")
                    findNavController().navigate(s)
                } else {
                    findNavController().navigate(R.id.profileFragment)
                }
            }
        }

        binding.btnBack.setOnClickListener {
            binding.passCheck.isChecked = false
            binding.password.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.confirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.register2.isVisible = false
            binding.register1.isVisible = true
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email: String = binding.email.text.toString()
            val password: String = binding.password.text.toString()
            val password_con: String = binding.confirmPassword.text.toString()
            val fname: String = binding.fname.text.toString()
            val lname: String = binding.lname.text.toString()
            val phone: String = binding.phone.text.toString()
        }

        override fun afterTextChanged(s: Editable?) {
        }

    }

}