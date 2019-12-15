package com.appwiz.quoteshub.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Quote
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.action_buttons_bar.view.*
import kotlinx.android.synthetic.main.author_quote_item.view.*
import com.appwiz.quoteshub.activities.SingleTag
import com.appwiz.quoteshub.models.Tag
import com.appwiz.quoteshub.utils.CommonUtils


class QuotesAdapter(val context: Context, var quotes: List<Quote>, val showQuotes: Boolean = true) : RecyclerView.Adapter<QuotesAdapter.MyViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotesAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.author_quote_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: QuotesAdapter.MyViewHolder, position: Int) {
        val quote = quotes[position]
        holder.setData(quote)

        val tagsManager = FlexboxLayoutManager(context)
        tagsManager.flexDirection = FlexDirection.ROW
        tagsManager.justifyContent = JustifyContent.FLEX_START

        if (showQuotes) {
            holder.itemView.author_quote_tags.apply {
                layoutManager = tagsManager
                adapter = TagsAdapter(context, quote.tags) { item: Tag, position: Int ->
                    val intent = Intent(context, SingleTag::class.java)
                    intent.putExtra("tagID", item.id)
                    intent.putExtra("tagName", item.name)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    this.context.startActivity(intent)
                }
                setRecycledViewPool(viewPool)
            }
        } else {
            holder.itemView.author_quote_tags.visibility = View.GONE
        }

        holder.itemView.action_copy.setOnClickListener(View.OnClickListener {
            CommonUtils().copyQuote(context, quote.title)
        })

        holder.itemView.action_share.setOnClickListener(View.OnClickListener {
            CommonUtils().shareQuote(context, quote.title)
        })
    }

    fun addItems(newQuotes: List<Quote>) {
        quotes += newQuotes
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(quote : Quote?) {
            val quoteText = itemView.findViewById<RecyclerView>(R.id.auth_quote_text) as TextView
            val quoteSrc = itemView.findViewById<RecyclerView>(R.id.auth_quote_src_full) as TextView
            quoteText.text = quote?.title
            if (quote?.source == null) {
                quoteSrc.visibility = View.GONE
            } else {
                quoteSrc.text = quote.source.name
            }
        }
    }
}