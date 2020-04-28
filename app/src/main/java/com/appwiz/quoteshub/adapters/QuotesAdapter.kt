package com.appwiz.quoteshub.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Quote
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.action_buttons.view.*
import kotlinx.android.synthetic.main.author_quote_item.view.*
import com.appwiz.quoteshub.activities.SingleTag
import com.appwiz.quoteshub.models.Tag
import com.appwiz.quoteshub.utils.CommonUtils
import com.appwiz.quoteshub.utils.NetworkState
import com.appwiz.quoteshub.utils.NetworkStateViewHolder
import java.lang.IllegalArgumentException


class QuotesAdapter(val context: Context, var quotes: List<Quote>,
                    val showQuotes: Boolean = true, val listener: (Quote) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        when (viewType) {
            R.layout.author_quote_item -> {
                val view = LayoutInflater.from(context).inflate(R.layout.author_quote_item, parent, false)
                return MyViewHolder(view)
            }
            R.layout.network_state_layout -> {
                return NetworkStateViewHolder.create(parent)
            }
        }
        throw IllegalArgumentException("unknown view type $viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.author_quote_item -> {
                val quote = quotes[position]
                val holder1 = holder as MyViewHolder
                holder1.setData(quote)

                val tagsManager = FlexboxLayoutManager(context)
                tagsManager.flexDirection = FlexDirection.ROW
                tagsManager.justifyContent = JustifyContent.CENTER

                if (showQuotes) {
                    holder1.itemView.author_quote_tags.apply {
                        layoutManager = tagsManager
                        adapter = TagsAdapter(quote.tags) { item: Tag, position: Int ->
                            val intent = Intent(context, SingleTag::class.java)
                            intent.putExtra("tagID", item.id)
                            intent.putExtra("tagName", item.name)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            this.context.startActivity(intent)
                        }
                        setRecycledViewPool(viewPool)
                    }
                } else {
                    holder1.itemView.author_quote_tags.visibility = View.GONE
                }

                holder1.itemView.setOnClickListener {
                    listener(quote)
                }
            }
            R.layout.network_state_layout -> {
                val holder2 = holder as NetworkStateViewHolder
                networkState?.let { holder2.bindTo(it) }
            }
        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(quotes.size)
            } else {
                notifyItemInserted(quotes.size)
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemCount(): Int {
        var extra = 0
        if (hasExtraRow()) extra = 1
        return quotes.size + extra
    }

    private fun hasExtraRow(): Boolean {
        return networkState == NetworkState.LOADING
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1)
            R.layout.network_state_layout
        else
            R.layout.author_quote_item
    }

    fun addItems(newQuotes: List<Quote>) {
        quotes += newQuotes
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(quote : Quote?) {
            val quoteText = itemView.findViewById(R.id.auth_quote_text) as TextView
            val quoteSrc = itemView.findViewById(R.id.auth_quote_src_full) as TextView
            quoteText.text = quote?.title
            if (quote?.source == null) {
                quoteSrc.visibility = View.GONE
            } else {
                quoteSrc.text = quote.source.name
            }
        }
    }
}