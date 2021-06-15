package com.example.ceta.paymentFragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ceta.R
import com.example.ceta.adapter.paymentAdapter
import com.example.ceta.databinding.PaymentFragmentBinding
import com.example.ceta.model.paymentData
import com.example.ceta.query.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class paymentFragment : Fragment() {

    companion object {
        fun newInstance() = paymentFragment()
    }

    private lateinit var binding: PaymentFragmentBinding
    private lateinit var viewModel: PaymentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.payment_fragment,
            container,
            false
        )

        val fm =
            fragmentManager // or 'getSupportFragmentManager();'
        val count = fm!!.backStackEntryCount
        Log.i("apptest", count.toString())
        var total: Int = 0
        val args by navArgs<paymentFragmentArgs>()
        val name = mutableListOf<String>()
        val city = mutableListOf<String>()
        val OrderId = getOrderId()
        val OrderName = getOrderName()
        val OrderCity = getOrderCity()
        val OrderPickDate = getOrderPickDate()
        val OrderBackDate = getOrderBackDate()
        val OrderDate = getOrderDate()
        val OrderEmail = getEmail()
        val OrderPrice = getOrderPrice()
        val OrderDeposit = getOrderDeposit()
        val OrderTotal = getOrderTotal()
        val payment_list: MutableList<paymentData> = ArrayList()
        var i = 0

        if (args.from == "Order") {
            while (i < args.rId.size) {
                val list = queryOrderPayment(args.rId[i])
                payment_list.add(paymentData(list.name,
                    list.city,
                    list.pick,
                    list.back,
                    list.price,
                    list.deposit,
                    list.total,
                    list.image,
                    list.date))
                total = total + list.total!!
                name.add(list.name.toString())
                city.add(list.city.toString())
                i++
            }
            val total_price = NumberFormat.getNumberInstance(Locale.US).format(total)
            binding.price.setText("฿" + total_price + ".00")
            setPayment(payment_list)
        } else {
            val list = queryDetail(OrderName!!, OrderCity!!)
            payment_list.add(paymentData(OrderName,
                OrderCity,
                OrderPickDate,
                OrderBackDate,
                OrderPrice,
                OrderDeposit,
                OrderTotal,
                list.MImage,
                OrderDate))
            Log.i("paytest",OrderCity)
            Log.i("paytest",OrderName)
            total = OrderTotal
            val total_price = NumberFormat.getNumberInstance(Locale.US).format(total)
            name.add(OrderName)
            city.add(OrderCity)

            binding.price.setText("฿" + total_price + ".00")
            setPayment(payment_list)
            Log.i("apptest", "pay")
        }

        binding.btnPay.setOnClickListener {
            if (binding.checkCredit.isChecked || binding.checkCash.isChecked) {
                if (args.from == "Detail") {
                    val insert_order = insert_order()
                    insert_order.insert_order(OrderId,
                        OrderEmail,
                        OrderName!!,
                        OrderCity!!,
                        OrderPickDate!!,
                        OrderBackDate!!,
                        OrderPrice,
                        OrderDeposit,
                        OrderTotal)
                }
                val insert = insert_payment()
                var i = 0
                while (i < args.rId.size) {
                    insert.count_check()
                    val id: String = "P" + "%04d".format(insert.count!! + 1)
                    val r_id = args.rId[i]
                    val insert_pickDate = insert_pickup(r_id,id)
                    val insert_backDate = insert_back(r_id, insert_pickDate.id)
                    insert.insert_payment(id, total, r_id)
                    insert.update_car(name[i], city[i])
                    i++
                }
                findNavController().navigate(R.id.homeFragment)
            }
        }

        binding.backbotton.setOnClickListener {
            if (args.from == "Detail") {
                val s = paymentFragmentDirections.payTodetail("${args.carName}",
                    "${args.city}",
                    "${args.search}")
                findNavController().navigate(s)
            } else {
                findNavController().navigate(R.id.basketFragment)
            }

        }
        return binding.root
    }


    private fun setPayment(item: MutableList<paymentData>) {
        binding.paymentList?.setLayoutManager(LinearLayoutManager(activity))
        binding.paymentList?.adapter = paymentAdapter(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun getEmail(): String? {
        val sharedPref = requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        return sharedPref.getString("Email", "")
    }

    private fun getOrderId(): String? {
        val sharedPref = requireActivity().getSharedPreferences("setOrder", Context.MODE_PRIVATE)
        return sharedPref.getString("id", "")
    }

    private fun getOrderName(): String? {
        val sharedPref = requireActivity().getSharedPreferences("setOrder", Context.MODE_PRIVATE)
        return sharedPref.getString("name", "")
    }

    private fun getOrderCity(): String? {
        val sharedPref = requireActivity().getSharedPreferences("setOrder", Context.MODE_PRIVATE)
        return sharedPref.getString("city", "")
    }

    private fun getOrderPickDate(): String? {
        val sharedPref = requireActivity().getSharedPreferences("setOrder", Context.MODE_PRIVATE)
        return sharedPref.getString("pickDate", "")
    }

    private fun getOrderBackDate(): String? {
        val sharedPref = requireActivity().getSharedPreferences("setOrder", Context.MODE_PRIVATE)
        return sharedPref.getString("backDate", "")
    }

    private fun getOrderPrice(): Int {
        val sharedPref = requireActivity().getSharedPreferences("setOrder", Context.MODE_PRIVATE)
        return sharedPref.getInt("price", 0)
    }

    private fun getOrderDeposit(): Int {
        val sharedPref = requireActivity().getSharedPreferences("setOrder", Context.MODE_PRIVATE)
        return sharedPref.getInt("deposit", 0)
    }

    private fun getOrderTotal(): Int {
        val sharedPref = requireActivity().getSharedPreferences("setOrder", Context.MODE_PRIVATE)
        return sharedPref.getInt("totalPrice", 0)
    }

    private fun getOrderDate(): Int {
        val sharedPref = requireActivity().getSharedPreferences("setOrder", Context.MODE_PRIVATE)
        return sharedPref.getInt("date", 0)
    }


    override fun onResume() {
        super.onResume()
        binding.checkCash.setOnClickListener {
            binding.checkCash.isChecked = true
            binding.checkCredit.isChecked = false
        }
        binding.checkCredit.setOnClickListener {
            binding.checkCash.isChecked = false
            binding.checkCredit.isChecked = true
        }
    }


}