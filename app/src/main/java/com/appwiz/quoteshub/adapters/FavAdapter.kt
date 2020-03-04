package com.appwiz.quoteshub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.room.entity.FavEntity

class FavAdapter(var quotes: List<FavEntity>, val listener: (FavEntity) -> Unit) : RecyclerView.Adapter<FavAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_quote_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = quotes.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val fav = quotes[position]
        holder.favText.text = fav.text
        holder.favSrc.text = fav.src
        holder.itemView.setOnClickListener {
            listener(fav)
            notifyDataSetChanged()
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favText = itemView.findViewById(R.id.fav_quote_text) as TextView
        val favSrc = itemView.findViewById(R.id.fav_quote_src) as TextView
    }

    internal fun setFav(favs: List<FavEntity>) {
        quotes = favs
        notifyDataSetChanged()
    }
}