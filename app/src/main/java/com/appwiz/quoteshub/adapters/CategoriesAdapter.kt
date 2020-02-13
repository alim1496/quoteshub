package com.appwiz.quoteshub.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.room.CatEntity

class CategoriesAdapter(var results: List<CatEntity>, val clickListener: (CatEntity, Int) -> Unit)
    : RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {

    val COLORS = arrayOf("#91EAE4", "#FBD786", "#C6FFDD", "#ffc0cb")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = results[position]
        holder.itemView.setBackgroundColor(Color.parseColor(COLORS[position % 4]))
        holder.itemView.setOnClickListener { clickListener(result, position) }
        holder.catName.text = result.name
        holder.catCount.text = result.quotes.toString()
    }

    fun updateData(data: List<CatEntity>) {
        results = data
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catName: TextView = itemView.findViewById(R.id.category_name)
        val catCount: TextView = itemView.findViewById(R.id.category_quotes)
    }
}