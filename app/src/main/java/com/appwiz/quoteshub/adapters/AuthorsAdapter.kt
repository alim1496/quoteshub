package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.utils.NetworkState

class AuthorsAdapter(private val click: (Author) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var networkState: NetworkState = NetworkState(NetworkState.Status.SUCCESS, "")
    private var authors:MutableList<Author> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == R.layout.author_item) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.author_item, parent, false)
            return AuthorViewHolder(view)
        }
        if (viewType == R.layout.network_state_layout) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.network_state_layout, parent, false)
            return NetworkStateHolder(view)
        }
        throw IllegalArgumentException("unknown view type \$viewType")
    }

    override fun getItemCount(): Int = authors.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == R.layout.author_item) {
            val author = authors.get(position)
            (holder as AuthorViewHolder).setData(author)
            holder.itemView.setOnClickListener { click(author) }
        } else if (getItemViewType(position) == R.layout.network_state_layout) {
            (holder as NetworkStateHolder).bindTo(networkState)
        } else {
            throw IllegalArgumentException("unknown view type \$viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) R.layout.network_state_layout
        else R.layout.author_item
    }

    fun setNetworkState(_networkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = _networkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) notifyItemRemoved(authors.size)
            else notifyItemInserted(authors.size)
        } else if (hasExtraRow && previousState != _networkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() : Boolean = networkState != NetworkState.LOADED

    fun reload(authors:MutableList<Author>) {
        this.authors = authors
        notifyDataSetChanged()
    }

    fun append(authors:MutableList<Author>) {
        this.authors.addAll(authors)
        notifyItemRangeInserted(this.authors.size - authors.size, authors.size)
    }
}