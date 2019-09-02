package com.example.quoteshub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class QuotesAdapter(val context: FragmentActivity?, val quotes:List<Quote>) : RecyclerView.Adapter<QuotesAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quote_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val quote = quotes[position]
        holder.setData(quote)
    }

    inner class MyViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        fun setData(quote : Quote?) {
            val quoteText = itemView.findViewById<RecyclerView>(R.id.quote_text) as TextView
            val quoteSrc = itemView.findViewById<RecyclerView>(R.id.quote_src) as TextView
            quoteText.text = quote?.title
            quoteSrc.text = quote?.source
        }
    }
}