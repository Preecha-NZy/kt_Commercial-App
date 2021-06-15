package com.example.ceta.detailFragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ceta.R
import com.example.ceta.adapter.reviewAdapter
import com.example.ceta.databinding.DetailFragmentBinding
import com.example.ceta.model.reviewData
import com.example.ceta.query.insert_order
import com.example.ceta.query.queryDetail
import com.example.ceta.query.queryReview
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class detailFragment : Fragment() {
    private lateinit var binding: DetailFragmentBinding
    var day = 0
    var month = 0
    var year = 0
    var betweenDate: Long = 0

    val ft = SimpleDateFormat("yyyy-MM-dd")
    var pick: String? = null
    var back: String? = null

    companion object {
        fun newInstance() = detailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.detail_fragment,
            container,
            false
        )
        val fm =
            fragmentManager // or 'getSupportFragmentManager();'
        val count = fm!!.backStackEntryCount
        Log.i("aaa", count.toString())
        val motionLayout: MotionLayout = binding.constraintLayout8
        //MaterialDatePicker
        val constraintsBuilder =
            CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now())
        val materialDatePicker =
            MaterialDatePicker.Builder.dateRangePicker().setTheme(R.style.Theme_App)
                .setCalendarConstraints(constraintsBuilder.build()).build()
        val args by navArgs<detailFragmentArgs>()
        val PickDate = binding.PickDate
        val BackDate = binding.BackDate
        val name: String = args.carName.toString()
        val city: String = args.city.toString()
        var date: Date? = null
        var date1: Date? = null
        var addFinished: Boolean = false
        val conn = queryDetail(name, city)
        val review = queryReview(args.city, args.carName)
        val reviewdata: MutableList<reviewData> = ArrayList()
        var i = 0
        while (i < review.id.size) {
            reviewdata.add(reviewData(review.fname[i], review.text[i], review.score[i]))
            i++
        }
        setReview(reviewdata)

        if (onLoginFinished()) {
            binding.addOrder.isVisible = true
        }

        binding.backbotton.setOnClickListener {
            if (args.search == "Not Search") {
                findNavController().navigate(R.id.detailTohome)
            } else {
                val a = detailFragmentDirections.detailToSearch("${args.search}", "yes")
                findNavController(this).navigate(a)
            }
            onUnsetdate()
        }

        binding.addOrder.setOnClickListener {
            if (binding.PickDate.text.toString().isNotEmpty()) {
                if (onLoginFinished()) {
                    if (!addFinished) {
                        val ft = SimpleDateFormat("yyyy-MM-dd")
                        val betweenDates = TimeUnit.MILLISECONDS.toDays(betweenDate).toInt()
                        val insert = insert_order()
                        insert.count_check()
                        val id: String = "R" + "%04d".format(insert.count!! + 1)
                        val email = getEmail()
                        val name: String? = conn.name
                        val city: String? = conn.city_data
                        val pickDate: String = ft.format(date)
                        val backDate: String = ft.format(date1)
                        val price: Int? = conn.price
                        val deposit: Int? = conn.deposit
                        val totalPrice: Int = price!! * betweenDates + deposit!!
                        insert.insert_order(
                            id, email, name,
                            city, pickDate, backDate,
                            price, deposit, totalPrice
                        )
                        binding.addMotionOrder.alpha = 1F
                        motionLayout.transitionToEnd()
                    }
                    addFinished = true
                } else {
                    Log.i("apptest", "please login first")
                }
            } else {
                materialDatePicker.show(parentFragmentManager, "aaa")
            }
        }
        binding.payment.setOnClickListener {
            if (binding.PickDate.text.toString().isNotEmpty()) {
                if (onLoginFinished()) {
                    val betweenDates = TimeUnit.MILLISECONDS.toDays(betweenDate).toInt()
                    val insert = insert_order()
                    insert.count_check()
                    val id: String = "R" + "%04d".format(insert.count!! + 1)
                    val email = getEmail()
                    val name: String? = conn.name
                    val city: String? = conn.city_data
                    var pickDate: String? = null
                    var backDate: String? = null
                    pickDate = pick
                    backDate = back
                    val price: Int? = conn.price
                    val deposit: Int? = conn.deposit
                    val totalPrice: Int = price!! * betweenDates + deposit!!
                    onSetOrder(
                        id, name!!,
                        city!!, pickDate!!, backDate!!,
                        price, deposit, totalPrice, betweenDates
                    )
                    val s = detailFragmentDirections.detailTopayment(arrayOf(id),
                        "Detail",
                        "${args.carName}",
                        "${args.city}",
                        "${args.search}")
                    findNavController().navigate(s)
                } else {
//                    onSetdate(binding.PickDate.text.toString(),binding.BackDate.text.toString())
                    val s = detailFragmentDirections.detailTologin("detail",
                        "${args.carName}",
                        "${args.city}",
                        "${args.search}")
                    findNavController().navigate(s)
                }
            } else {
                materialDatePicker.show(parentFragmentManager, "aaa")
            }
        }


        PickDate.setOnClickListener {
            getDateCalendar()
            materialDatePicker.show(parentFragmentManager, "aaa")
//            onSetdate()
        }

        BackDate.setOnClickListener {
            materialDatePicker.show(parentFragmentManager, "aaa")
        }

        materialDatePicker.addOnPositiveButtonClickListener {
            var start: Long? = it.first
            var end: Long? = it.second
            betweenDate = end?.minus(start!!)!!
            date = start?.let { it1 -> Date(it1) }
            date1 = end?.let { it1 -> Date(it1) }
            pick = ft.format(date)
            back = ft.format(date1)
            PickDate.setText("Start: $pick")
            BackDate.setText("End: $back")
        }

        val price = NumberFormat.getNumberInstance(Locale.US).format(conn.price)
        binding.carView.setImageBitmap(conn.LImage)
        binding.carname.setText(conn.name)
        binding.price.setText("฿"+price+ "/ day")
        binding.Fuel.setText(conn.fuel)
        binding.Wheel.setText(conn.drive_system)
        binding.Engine.setText(conn.engine)
        binding.Gear.setText(conn.gear)

        return binding.root
    }


    private fun setReview(item: MutableList<reviewData>) {
        binding.reviewRecycler.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.reviewRecycler.adapter = reviewAdapter(item)
    }


    private fun findNavController(detailFragment: detailFragment): NavController {
        return detailFragment.findNavController()
    }

    private fun getDateCalendar() {
        val calendar: Calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun onLoginFinished(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Login", false)
    }

    private fun onSetdate(pick:String, back:String) {
        val sharedPref = requireActivity().getSharedPreferences("onSetdate", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Set", true)
        editor.putString("pickDate", pick)
        editor.putString("bringDate", back)
        editor.apply()
    }

    private fun onUnsetdate() {
        val sharedPref = requireActivity().getSharedPreferences("onSetdate", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("picDate", "")
        editor.putString("bringDate", "")
        editor.putBoolean("Set", false)
        editor.apply()
    }

    private fun onSetdateFinish(): Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onSetdate", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Set", false)
    }

    private fun getPickDate(): String? {
        val sharedPref = requireActivity().getSharedPreferences("onSetdate", Context.MODE_PRIVATE)
        return sharedPref.getString("pickDate", "")
    }

    private fun getBringDate(): String? {
        val sharedPref = requireActivity().getSharedPreferences("onSetdate", Context.MODE_PRIVATE)
        return sharedPref.getString("bringDate", "")
    }

    private fun getEmail(): String? {
        val sharedPref = requireActivity().getSharedPreferences("onLogin", Context.MODE_PRIVATE)
        return sharedPref.getString("Email", "")
    }

    private fun onSetOrder(
        id: String,
        name: String,
        city: String,
        pickDate: String,
        backDate: String,
        price: Int,
        deposit: Int,
        totalPrice: Int,
        betweenDate: Int,
    ) {
        val sharedPref = requireActivity().getSharedPreferences("setOrder", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("id", "$id")
        editor.putString("name", "$name")
        editor.putString("city", "$city")
        editor.putString("pickDate", "$pickDate")
        editor.putString("backDate", "$backDate")
        editor.putInt("price", price)
        editor.putInt("deposit", deposit)
        editor.putInt("totalPrice", totalPrice)
        editor.putInt("date", betweenDate)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        onUnsetdate()
    }
}








