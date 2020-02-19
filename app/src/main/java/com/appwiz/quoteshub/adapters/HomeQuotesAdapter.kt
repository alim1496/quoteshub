package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.room.entity.HomeEntity
import com.appwiz.quoteshub.room.entity.RecentEntity

class HomeQuotesAdapter(var quotes:List<HomeEntity>) : RecyclerView.Adapter<HomeQuotesAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val quote = quotes[position]
        holder.quoteText.text = quote.text
        holder.quoteSrc.text = quote.author
    }

    fun updateData(data: List<HomeEntity>) {
        quotes = data
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val quoteText: TextView = itemView.findViewById(R.id.quote_text)
        val quoteSrc: TextView = itemView.findViewById(R.id.quote_src)
    }
}