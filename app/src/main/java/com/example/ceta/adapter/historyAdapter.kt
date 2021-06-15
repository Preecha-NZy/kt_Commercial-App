package com.example.ceta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ceta.R
import com.example.ceta.model.historyData
import java.text.NumberFormat
import java.util.*

class historyAdapter(
    private val list: MutableList<historyData>,
    private val listener: OnItemClickListener,
) :
    RecyclerView.Adapter<historyAdapter.historyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): historyViewHolder {
        return historyViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_row,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: historyViewHolder, position: Int) {
        val price = NumberFormat.getNumberInstance(Locale.US).format(list[position].price)
        holder.name.setText("${list[position].name}" + "\n${list[position].city}")
        holder.price.setText("฿"+price+".00")
        holder.date.setText("Qty:${list[position].date}"+" day")
        holder.image.setImageBitmap(list[position].image)
        holder.review.setOnClickListener {
            holder.click(list[position].name,
                list[position].city,
                list[position].date)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class historyViewHolder(item: View) : RecyclerView.ViewHolder(item),
        View.OnClickListener {

        val name: TextView
        val image: ImageView
        val price: TextView
        val date: TextView
        val review: Button

        init {
            name = item.findViewById(R.id.name)
            image = item.findViewById(R.id.imageView)
            price = item.findViewById(R.id.price)
            date = item.findViewById(R.id.date)
            review = item.findViewById(R.id.btn_review)
        }

        override fun onClick(v: View?) {
        }

        fun click(name: String?, city: String?, date: Int?) {
            listener.Toreview(name, city, date)
        }
    }

    interface OnItemClickListener {
        fun Toreview(name: String?, city: String?, date: Int?)
    }

}