package com.example.quoteshub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.R
import com.example.quoteshub.models.Quote
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.author_quote_item.view.*


class AuthorQuotesAdapter(val context: Context, var quotes: List<Quote>) : RecyclerView.Adapter<AuthorQuotesAdapter.MyViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AuthorQuotesAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.author_quote_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: AuthorQuotesAdapter.MyViewHolder, position: Int) {
        val quote = quotes[position]
        holder.setData(quote)

        val tagsManager = FlexboxLayoutManager(context)
        tagsManager.flexDirection = FlexDirection.ROW
        tagsManager.justifyContent = JustifyContent.FLEX_START

        holder.itemView.author_quote_tags.apply {
            layoutManager = tagsManager
            adapter = TagsAdapterMain(context, quote.tags)
            setRecycledViewPool(viewPool)
        }
    }

    fun addItems(newQuotes: List<Quote>) {
        quotes += newQuotes
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(quote : Quote?) {
            val quoteText = itemView.findViewById<RecyclerView>(R.id.auth_quote_text) as TextView
            quoteText.text = quote?.title
        }
    }
}