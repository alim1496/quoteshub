package com.appwiz.quoteshub.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Quote

class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.quote_title)
    private val author: TextView = itemView.findViewById(R.id.quote_author)

    fun bindView(quote: Quote) {
        title.text = quote.title
        author.text = quote.source.name
    }
}