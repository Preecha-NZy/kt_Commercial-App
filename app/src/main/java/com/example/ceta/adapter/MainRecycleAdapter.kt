package com.example.ceta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ceta.R
import com.example.ceta.homeFragment.homeFragment
import com.example.ceta.model.AllCategory
import com.example.ceta.model.Imagecategory

class MainRecycleAdapter(
    private val context: Context?,
    private val allCategory: MutableList<AllCategory>,
    private val listener: OnItemClickListener
    ): RecyclerView.Adapter<MainRecycleAdapter.MainViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.main_recycle_row, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.categoryTitle?.setText(allCategory[position].imageTitle)
        holder.seemore.setOnClickListener {
            holder.click(holder.categoryTitle.text.toString())
        }
        setImageRecycler(holder.imageRecycler,allCategory[position].image)
    }

    override fun getItemCount(): Int {
        return allCategory.size
    }

    private fun setImageRecycler(recyclerView: RecyclerView, imageItem: MutableList<Imagecategory>) {
        var imageRecyclerAdapter = ImageRecycleAdapter(context, imageItem,listener)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = imageRecyclerAdapter
    }

    inner class MainViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var categoryTitle: TextView
        var imageRecycler: RecyclerView
        var seemore: TextView
        init {
            categoryTitle = itemView.findViewById(R.id.textView)
            imageRecycler = itemView.findViewById(R.id.image_item_recycle)
            seemore = itemView.findViewById(R.id.seemore)
        }

        override fun onClick(v: View?) {
            TODO("Not yet implemented")
        }

        fun click(city: String){
            listener.clickToSearch( city )
        }

    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun clickToDetail(city: String, name: String)
        fun clickToSearch(city: String)
        fun findNavController(searchFragment: homeFragment): NavController
    }
}

