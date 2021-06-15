package com.example.ceta.profileFragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ceta.R
import com.example.ceta.adapter.historyAdapter
import com.example.ceta.databinding.ProfileFragmentBinding
import com.example.ceta.model.historyData
import com.example.ceta.query.profile
import com.example.ceta.query.queryHistory

class profileFragment : Fragment(), historyAdapter.OnItemClickListener {
    private lateinit var binding: ProfileFragmentBinding
    private var color = "black"

    companion object {
        fun newInstance() = profileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.profile_fragment,
            container,
            false
        )
        val fm =
            fragmentManager // or 'getSupportFragmentManager();'
        val count = fm!!.backStackEntryCount
        Log.i("apptest", count.toString())
        if (onLoginFinished()) {
            val history_list: MutableList<historyData> = ArrayList()
            val getEmail = getEmail()
            val p = profile()
            val history = queryHistory(getEmail)
            var i = 0
            while (i < history.id.size) {
                history_list.add(historyData(history.image[i],
                    history.name[i],
                    history.city[i],
                    history.price[i],
                    history.date[i],
                    history.id[i]))
                i++
            }
            setHistory(history_list)
            p.get_profile(getEmail.toString())
            binding.email.setText(p.email)
            binding.fname.setText(Capital(p.fname.toString()))
            binding.lname.setText(Capital(p.lname.toString()))
            binding.loginLayout.isVisible = true
            binding.unloginLayout.isVisible = false
        } else {
            binding.loginLayout.isVisible = false
            binding.unloginLayout.isVisible = true
        }

        binding.btnSignin.setOnClickListener {
            val s = profileFragmentDirections.profileTologin("profile", null, null, null)
            findNavController(this).navigate(s)
        }

        binding.btnSignup.setOnClickListener {
            val s = profileFragmentDirections.profileToregister("profile","","","")
            findNavController().navigate(s)
        }
        return binding.root
    }

    private fun findNavController(profileFragment: profileFragment): NavController {
        return profileFragment.findNavController()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        binding.logout.setOnClickListener {
            onLogoutFinished()
            binding.loginLayout.isVisible = false
            binding.unloginLayout.isVisible = true
        }
    }

    private fun onLogoutFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Login", false)
        editor.apply()
    }

    private fun onLoginFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        val a = sharedPref.getString("Email", "")
        return sharedPref.getBoolean("Login", false)
    }

    private fun getEmail(): String? {
        val sharedPref = requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        return sharedPref.getString("Email", "")
    }

    private fun Capital(text: String): String {
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase()
    }

    private fun setHistory(item: MutableList<historyData>) {
        binding.recyclerHistory?.setLayoutManager(LinearLayoutManager(activity))
        binding.recyclerHistory?.adapter = historyAdapter(item,this)
    }

    override fun Toreview(name: String?, city: String?, date: Int?) {
        val a = profileFragmentDirections.profileToreview(name.toString(),city.toString(),date!!.toInt())
        findNavController(this).navigate(a)
    }


}