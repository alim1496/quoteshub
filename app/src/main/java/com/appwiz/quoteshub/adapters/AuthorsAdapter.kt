package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Author
import com.squareup.picasso.Picasso

class AuthorsAdapter(var authors: List<Author>, val clcikListener: (Author, Int) -> Unit) :
    RecyclerView.Adapter<AuthorsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.author_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val author = authors[position]
        holder.itemView.setOnClickListener { clcikListener(author, position) }
        holder.setData(author)
    }

    fun emptyAdapter() {
        authors = emptyList()
        notifyDataSetChanged()
    }

    fun addItems(newAuthors: List<Author>) {
        authors += newAuthors
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(author: Author) {

            val authName: TextView = itemView.findViewById(R.id.author_name)
            val authImg: ImageView = itemView.findViewById(R.id.author_img)
            val authCount: TextView = itemView.findViewById(R.id.author_quotes)
            authName.text = author.name
            Picasso.get().load(author.image).placeholder(R.drawable.avatar_placeholder)
                .into(authImg)
            authCount.text = author.quotes.toString()

        }
    }
}