package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.utils.NetworkState

class QuotesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var networkState: NetworkState = NetworkState(NetworkState.Status.SUCCESS, "")
    private var quotes:MutableList<Quote> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == R.layout.common_quote_item) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.common_quote_item, parent, false)
            return QuoteViewHolder(view)
        }
        if (viewType == R.layout.network_state_layout) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.network_state_layout, parent, false)
            return NetworkStateHolder(view)
        }
        throw IllegalArgumentException("unknown view type \$viewType")
    }

    override fun getItemCount(): Int = quotes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == R.layout.common_quote_item) {
            val quote = quotes.get(position)
            (holder as QuoteViewHolder).bindView(quote)
        } else if (getItemViewType(position) == R.layout.network_state_layout) {
            (holder as NetworkStateHolder).bindTo(networkState)
        } else {
            throw IllegalArgumentException("unknown view type \$viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) R.layout.network_state_layout
            else R.layout.common_quote_item
    }

    fun setNetworkState(_networkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = _networkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) notifyItemRemoved(quotes.size)
            else notifyItemInserted(quotes.size)
        } else if (hasExtraRow && previousState != _networkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() : Boolean = networkState != NetworkState.LOADED

    fun reloadData(quotes:MutableList<Quote>) {
        this.quotes = quotes
        notifyDataSetChanged()
    }

    fun appendData(quotes: MutableList<Quote>) {
        this.quotes.addAll(quotes)
        notifyItemRangeInserted(this.quotes.size - quotes.size, quotes.size)
    }
}