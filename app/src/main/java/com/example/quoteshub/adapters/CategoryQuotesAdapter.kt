package com.example.quoteshub.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.R
import com.example.quoteshub.models.Quote
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.quote_item_full.view.*

class CategoryQuotesAdapter(val context: Context, var quotes: List<Quote>) : RecyclerView.Adapter<CategoryQuotesAdapter.MyViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryQuotesAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.quote_item_full, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: CategoryQuotesAdapter.MyViewHolder, position: Int) {
        val quote = quotes[position]
        holder.setData(quote)

        val tagsManager = FlexboxLayoutManager(context)
        tagsManager.flexDirection = FlexDirection.ROW
        tagsManager.justifyContent = JustifyContent.FLEX_START

        holder.itemView.quote_tags.apply {
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
            val quoteText = itemView.findViewById<RecyclerView>(R.id.quote_text_full) as TextView
            val quoteSrc = itemView.findViewById<RecyclerView>(R.id.quote_src_full) as TextView
            quoteText.text = quote?.title
            quoteSrc.text = quote?.source?.name
        }
    }
}