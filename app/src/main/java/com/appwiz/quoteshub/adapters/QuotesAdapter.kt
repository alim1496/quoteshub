package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Quote

class QuotesAdapter : RecyclerView.Adapter<QuotesAdapter.MyViewHolder>() {

    private var quotes:MutableList<Quote> = arrayListOf()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title:TextView = itemView.findViewById(R.id.quote_title)
        private val author:TextView = itemView.findViewById(R.id.quote_author)

        fun bindView(quote:Quote) {
            title.text = quote.title
            author.text = quote.source.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.common_quote_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = quotes.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val quote = quotes.get(position)
        holder.bindView(quote)
    }

    fun reloadData(quotes:MutableList<Quote>) {
        this.quotes = quotes
        notifyDataSetChanged()
    }
}