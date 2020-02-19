package com.appwiz.quoteshub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.room.entity.FavEntity

class FavAdapter(val context: Context) : RecyclerView.Adapter<FavAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var quotes = emptyList<FavEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavAdapter.MyViewHolder {
        val view = inflater.inflate(R.layout.quote_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = quotes.size

    override fun onBindViewHolder(holder: FavAdapter.MyViewHolder, position: Int) {
        val fav = quotes[position]
        holder.favText.text = fav.text
        holder.favSrc.text = fav.src
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val favText = itemView.findViewById<RecyclerView>(R.id.quote_text) as TextView
            val favSrc = itemView.findViewById<RecyclerView>(R.id.quote_src) as TextView
    }

    internal fun setFav(favs: List<FavEntity>) {
        this.quotes = favs
        notifyDataSetChanged()
    }
}