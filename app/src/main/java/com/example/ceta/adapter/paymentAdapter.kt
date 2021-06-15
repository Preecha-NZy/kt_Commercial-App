package com.example.ceta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ceta.R
import com.example.ceta.model.paymentData
import java.text.NumberFormat
import java.util.*

class paymentAdapter(private val list: MutableList<paymentData>) :
    RecyclerView.Adapter<paymentAdapter.paymentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): paymentViewHolder {
        return paymentViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.payment_row,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: paymentViewHolder, position: Int) {
        var price: String = NumberFormat.getNumberInstance(Locale.US).format(list[position].price)
        var deposit: String = NumberFormat.getNumberInstance(Locale.US).format(list[position].deposit)
        var total: String = NumberFormat.getNumberInstance(Locale.US).format(list[position].total)
        holder.name.setText("${list[position].name} \n${list[position].city}")
        holder.date.setText("Rent:" + list[position].date.toString() + " day")
        holder.price.setText("฿" + price + ".00")
        holder.deposit.setText("Deposit:฿" + deposit + ".00")
        holder.pick.setText(list[position].pick.toString())
        holder.back.setText(list[position].back.toString())
        holder.total.setText("Total amount: " + total+ ".00")
        holder.image.setImageBitmap(list[position].image)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class paymentViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val name: TextView
        val deposit: TextView
        val price: TextView
        val date: TextView
        val pick: TextView
        val back: TextView
        val image: ImageView
        val total: TextView

        init {
            name = item.findViewById(R.id.name)
            deposit = item.findViewById(R.id.deposit)
            price = item.findViewById(R.id.price)
            date = item.findViewById(R.id.date)
            pick = item.findViewById(R.id.pick)
            back = item.findViewById(R.id.back)
            image = item.findViewById(R.id.image)
            total = item.findViewById(R.id.total)
        }

    }
}