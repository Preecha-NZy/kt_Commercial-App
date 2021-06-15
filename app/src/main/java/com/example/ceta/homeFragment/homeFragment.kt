package com.example.ceta.homeFragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ceta.R
import com.example.ceta.adapter.MainRecycleAdapter
import com.example.ceta.databinding.HomeFragmentBinding
import com.example.ceta.model.AllCategory
import com.example.ceta.model.Imagecategory
import com.example.ceta.query.queryCarHome
import com.example.ceta.query.queryCity
import com.example.ceta.searchFragment.searchFragment

class homeFragment : Fragment(),MainRecycleAdapter.OnItemClickListener {
    private lateinit var binding: HomeFragmentBinding
    private lateinit var viewModel: HomeViewModel


    companion object {
        fun newInstance() = searchFragment()
        fun listener() = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_fragment,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val city_check = queryCity()
        val city:MutableList<String> = ArrayList()
        val title: MutableList<AllCategory> = ArrayList()
        var i:Int = 0
        while (i < city_check.city.size ) {
            city.add(city_check.city[i].toString())
            val car_home = queryCarHome(city[i])
            val car:MutableList<Imagecategory> = ArrayList()
            var a = 0
            while (a < car_home.name.size) {
                car.add(Imagecategory(car_home.SImage[a],car_home.price[a],car_home.name[a],city[i]))
                a++
            }
            title.add(AllCategory(city[i],car))
            i++
        }
        binding.searchbar.setOnClickListener{
            findNavController().navigate(R.id.homeToSearch)
        }
        setMainCategoryRecycler(title)
        return  binding.root

    }

    private fun setMainCategoryRecycler(allcategory: MutableList<AllCategory>) {
        binding.mainRecycler?.setLayoutManager(LinearLayoutManager(activity))
        binding.mainRecycler?.adapter = MainRecycleAdapter(context,allcategory,this)
    }

    override fun onItemClick(position: Int) {
    }

    override fun clickToDetail(city: String, name: String) {
        val s = homeFragmentDirections.homeToDetail("$name","$city","Not Search")
        findNavController(this).navigate(s)
    }

    override fun clickToSearch(city: String) {
        val s = homeFragmentDirections.homeToSearch("$city","yes")
        findNavController(this).navigate(s)

    }

    override fun findNavController(searchFragment: homeFragment): NavController {
        return searchFragment.findNavController()
    }

    private fun onFirstTime() {
        val sharedPref = requireActivity().getSharedPreferences("onFirstTime", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Set", true)
        editor.apply()
    }

    private fun onFirstTimeFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onFirstTime", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Set", false)
    }

}
//val fm =
//    fragmentManager // or 'getSupportFragmentManager();'
//
//val count = fm!!.backStackEntryCount
//Log.i("apptet",count.toString())