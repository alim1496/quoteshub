package com.appwiz.quoteshub.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Quote

class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title: TextView = itemView.findViewById(R.id.quote_title)
    val author: TextView = itemView.findViewById(R.id.quote_author)

    fun bindView(quote: Quote, showAuthor: Boolean) {
        title.text = quote.title
        if (showAuthor) {
            author.visibility = View.VISIBLE
            author.text = quote.source.name
        }
        else author.visibility = View.GONE
    }
}