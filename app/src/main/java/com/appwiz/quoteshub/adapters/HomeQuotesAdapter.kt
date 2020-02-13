package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Quote

class HomeQuotesAdapter(var quotes:List<Quote>) : RecyclerView.Adapter<HomeQuotesAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val quote = quotes[position]
        holder.quoteText.text = quote.title
        holder.quoteSrc.text = quote.source.name
    }

    fun updateData(data: List<Quote>) {
        quotes = data
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val quoteText: TextView = itemView.findViewById(R.id.quote_text)
        val quoteSrc: TextView = itemView.findViewById(R.id.quote_src)
    }
}