package com.example.ceta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ceta.R
import com.example.ceta.model.carListData
import com.example.ceta.searchFragment.searchFragment
import java.text.NumberFormat
import java.util.*

class CarlistRecycleAdapter(
    private val carList: MutableList<carListData>,
    private val listener: OnItemClickListener
    ): RecyclerView.Adapter<CarlistRecycleAdapter.CarlistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarlistViewHolder {
        return CarlistViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.search_row_item,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: CarlistViewHolder, position: Int) {
        val price = NumberFormat.getNumberInstance(Locale.US).format(carList[position].price)
        holder.carImage.setImageBitmap(carList[position].image)
        holder.carName.setText(carList[position].name)
        holder.carPrice.setText("฿"+price+"/ day ")
        holder.carCity.setText(carList[position].city)
        holder.itemView.setOnClickListener {
            holder.click(holder.carName.text.toString(), holder.carCity.text.toString())
        }

    }

    override fun getItemCount(): Int {
        return carList.size
    }

    inner class CarlistViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),View.OnClickListener{
        val carImage: ImageView
        val carName: TextView
        val carPrice: TextView
        val carCity: TextView

        init {
            carImage = itemView.findViewById(R.id.car_image)
            carName = itemView.findViewById(R.id.car_name)
            carPrice = itemView.findViewById(R.id.car_price)
            carCity = itemView.findViewById(R.id.car_city)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
        }

        fun click(name: String, city: String){
            listener.clickToDetail(name, city)
        }

    }

    interface OnItemClickListener {
         fun clickToDetail(name:String, city: String)
        fun findNavController(searchFragment: searchFragment): NavController
    }

}


