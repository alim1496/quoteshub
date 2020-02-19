package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.room.entity.AuthorEntity
import com.squareup.picasso.Picasso

class HomeAuthorsAdapter(var authors:List<AuthorEntity>, val clickListener: (AuthorEntity, Int) -> Unit) : RecyclerView.Adapter<HomeAuthorsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_author_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val author = authors[position]
        holder.itemView.setOnClickListener { clickListener(author, position) }
        holder.authName.text = author.name
        Picasso.get().load(author.image).placeholder(R.drawable.avatar_placeholder).into(holder.authImg)
        holder.authCount.text = author.quotes.toString()
    }

    fun addData(data:List<AuthorEntity>) {
        authors = data
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authName: TextView = itemView.findViewById(R.id.home_author_name)
        val authImg: ImageView = itemView.findViewById(R.id.home_author_img)
        val authCount: TextView = itemView.findViewById(R.id.home_author_quotes)
    }
}