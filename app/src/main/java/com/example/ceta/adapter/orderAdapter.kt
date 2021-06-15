package com.example.ceta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ceta.R
import com.example.ceta.model.orderData
import java.text.NumberFormat
import java.util.*

class orderAdapter(
    private val orderList: MutableList<orderData>,
    private val listener: OnItemClickListener,
) : RecyclerView.Adapter<orderAdapter.OrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.order_row,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val price = NumberFormat.getNumberInstance(Locale.US).format(orderList[position].price)
        val deposit = NumberFormat.getNumberInstance(Locale.US).format(orderList[position].deposit)
        holder.name.setText(orderList[position].name)
        holder.date.setText("Rent:"+orderList[position].date.toString()+" day")
        holder.price.setText("Price / day: ฿"+price+".00")
        holder.deposit.setText("Deposit: "+deposit+".00")
        holder.image.setImageBitmap(orderList[position].image)
        holder.check.setOnClickListener {
            if (holder.check.isChecked) {
                val price: Int = orderList[position].price
                val deposit: Int = orderList[position].deposit
                val date: Int = orderList[position].date
                holder.Oncheck(date, price, deposit,position)
            } else {
                val price: Int = orderList[position].price
                val deposit: Int = orderList[position].deposit
                val date: Int = orderList[position].date
                holder.Oncheck(date, -price, -deposit,position)
                holder.OnNotcheck(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    inner class OrderViewHolder(item: View) :
        RecyclerView.ViewHolder(item), View.OnClickListener {
        val name: TextView
        val image: ImageView
        val price: TextView
        val deposit: TextView
        val date: TextView
        val check: CheckBox

        init {
            name = item.findViewById(R.id.name)
            image = item.findViewById(R.id.image)
            price = item.findViewById(R.id.price)
            deposit = item.findViewById(R.id.deposit)
            date = item.findViewById(R.id.date)
            check = item.findViewById(R.id.checkBox)
        }

        fun Oncheck(date: Int, price: Int, deposit: Int, position: Int) {
            listener.check_item(date, price, deposit, position)
        }
        fun OnNotcheck(position: Int) {
            listener.item_notcheck(position)
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }
    }

    interface OnItemClickListener {
        fun check_item(date: Int, price: Int, deposit: Int, position: Int)
        fun item_notcheck(position: Int)
    }

}