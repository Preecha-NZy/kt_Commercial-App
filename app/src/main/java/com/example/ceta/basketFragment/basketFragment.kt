package com.example.ceta.basketFragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ceta.R
import com.example.ceta.adapter.orderAdapter
import com.example.ceta.databinding.BasketFragmentBinding
import com.example.ceta.model.orderData
import com.example.ceta.query.queryOrder
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class basketFragment : Fragment(), orderAdapter.OnItemClickListener {


    companion object {
        fun newInstance() = basketFragment()
    }

    private lateinit var binding: BasketFragmentBinding
    private lateinit var viewModel: BasketViewModel
    private var deposit:Int = 0
    private var total_price:Int = 0
    private val id:MutableList<String> = ArrayList()
    private val r_id:MutableList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.basket_fragment,
            container,
            false
        )
        val fm =
            fragmentManager // or 'getSupportFragmentManager();'
        val count = fm!!.backStackEntryCount
        Log.i("apptest", count.toString())

        r_id.clear()
        total_price = 0
        binding.pay.setOnClickListener {

            if (r_id.isNotEmpty()) {
                val s = basketFragmentDirections.orderTopayment(r_id.toTypedArray(),"Order","","","")
                findNavController().navigate(s)
            }
        }
        if (onLoginFinished()) {
            val email: String? = getEmail()
            val order: MutableList<orderData> = ArrayList()
            id.clear()
            val order_list = queryOrder(email)
            var i = 0
            while (i < order_list.name.size) {
                order.add(orderData(order_list.name[i],
                    order_list.date[i],
                    order_list.price[i],
                    order_list.deposit[i],
                    order_list.image[i]))
                id.add(order_list.id[i])
                i++
            }
            setOrder(order)
            binding.payLayout.isVisible = true
        }
        return binding.root
    }

    private fun setOrder(item: MutableList<orderData>) {
        binding.orderRecyclerView?.setLayoutManager(LinearLayoutManager(activity))
        binding.orderRecyclerView?.adapter = orderAdapter(item, this)
    }

    private fun getEmail(): String? {
        val sharedPref = requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        return sharedPref.getString("Email", "")
    }

    private fun onLoginFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Login", false)
    }

    override fun check_item(date: Int, price: Int, deposit: Int, position:Int) {
        total_price = total_price + (date*price + deposit)
        this.deposit = this.deposit + deposit
        val total_price = NumberFormat.getNumberInstance(Locale.US).format(total_price)
        val deposit = NumberFormat.getNumberInstance(Locale.US).format(this.deposit)
        binding.price.setText("฿"+total_price+".00")
        binding.deposit.setText("฿"+deposit+".00")
        var i = 0
        var insert:Boolean = true
        while (i < r_id.size) {
            if(id[position]==r_id[i]) {
                insert = false
            }
            i++
            Log.i("apptest",r_id.size.toString())
        }
        if (insert) {
            r_id.add(id[position])
        }
    }

    override fun item_notcheck(position: Int) {
        var i = 0
        while (i < r_id.size) {
            if (r_id[i] == id[position]) {
                r_id.removeAt(i)
            }
            i++
        }
    }

}

