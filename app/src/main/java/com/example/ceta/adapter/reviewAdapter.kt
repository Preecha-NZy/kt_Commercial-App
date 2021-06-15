package com.example.ceta.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ceta.R
import com.example.ceta.model.reviewData

class reviewAdapter(private val item: MutableList<reviewData>):
    RecyclerView.Adapter<reviewAdapter.reviewViewHolder>() {
    class reviewViewHolder(item: View):RecyclerView.ViewHolder(item) {

        val fname:TextView
        val text:TextView
        val score:RatingBar
        init {
            fname = item.findViewById(R.id.fname)
            text = item.findViewById(R.id.text)
            score = item.findViewById(R.id.score)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int, ): reviewViewHolder {
        return reviewViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_review_row, parent, false))
    }

    override fun onBindViewHolder(holder:reviewViewHolder, position: Int) {
        holder.fname.setText(item[position].fname)
        holder.text.setText(item[position].text)
        holder.score.rating = item[position].score.toFloat()
    }

    override fun getItemCount(): Int {
        return item.size
    }
}