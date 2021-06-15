package com.example.ceta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ceta.R
import com.example.ceta.model.Imagecategory
import java.text.NumberFormat
import java.util.*

class ImageRecycleAdapter(
    private var context: Context?,
    private var ImageItem: MutableList<Imagecategory>,
    private val listener: MainRecycleAdapter.OnItemClickListener
    ): RecyclerView.Adapter<ImageRecycleAdapter.ImageRecycleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRecycleAdapter.ImageRecycleViewHolder {
        return ImageRecycleViewHolder(LayoutInflater.from(context).inflate(R.layout.image_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: ImageRecycleAdapter.ImageRecycleViewHolder, position: Int) {
        val price = NumberFormat.getNumberInstance(Locale.US).format(ImageItem[position].price)
        holder.Imageitem.setImageBitmap(ImageItem[position].image)
        holder.Price.setText("฿" + price +"/ day")
        holder.itemView.setOnClickListener{
            holder.click(ImageItem[position].city.toString(), ImageItem[position].name.toString())
        }
    }

    override fun getItemCount(): Int {
        return  ImageItem.size
    }
    inner class ImageRecycleViewHolder(ItemView: View):
        RecyclerView.ViewHolder(ItemView),View.OnClickListener {
        var Imageitem: ImageView
        var Price: TextView
        init {
            Imageitem = ItemView.findViewById(R.id.image_item)
            Price = ItemView.findViewById(R.id.price)
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        fun click(city: String, name: String){
            listener.clickToDetail(city, name)
        }

    }
}
