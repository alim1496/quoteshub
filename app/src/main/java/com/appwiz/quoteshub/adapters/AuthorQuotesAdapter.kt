package com.appwiz.quoteshub.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.AuthorQuote

class AuthorQuotesAdapter(val quotes:List<AuthorQuote>) : RecyclerView.Adapter<AuthorQuotesAdapter.MyViewHolder>() {
    private val COLOR = arrayOf("#49FFB9", "#EED2EE", "#FFD492", "#F4AAAB", "#E6E4E4", "#FEF2DF", "#FCF18B", "#E6677D", "#C9FFFD", "#72E6DA", "#E6677D")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_author_details_quote, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = quotes.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val quote = quotes.get(position)
        holder.itemView.setBackgroundColor(Color.parseColor(COLOR[position % 5]))
        holder.setData(quote)
    }

    class MyViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun setData(quote:AuthorQuote) {
            val textView:TextView = itemView.findViewById(R.id.author_quote)
            textView.text = quote.title
        }
    }
}