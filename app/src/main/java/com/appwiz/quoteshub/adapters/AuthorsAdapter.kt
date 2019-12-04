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

class AuthorsAdapter(val context: FragmentActivity, var authors: List<Author>, val clcikListener: (Author, Int) -> Unit)
    : RecyclerView.Adapter<AuthorsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorsAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.author_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    override fun onBindViewHolder(holder: AuthorsAdapter.MyViewHolder, position: Int) {
        val result = authors[position]
        holder.itemView.setOnClickListener { clcikListener(result, position) }
        return holder.setData(result)
    }

    fun addItems(newAuthors: List<Author>) {
        authors += newAuthors
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(author: Author) {
            val authName = itemView.findViewById<RecyclerView>(R.id.author_name) as TextView
            val authImg = itemView.findViewById<RecyclerView>(R.id.author_img) as ImageView
            val authCount = itemView.findViewById<RecyclerView>(R.id.author_quotes) as TextView

            authName.text = author.name
            Picasso.get().load(author.image).placeholder(R.drawable.avatar_placeholder).into(authImg)
            authCount.text = author.quotes.toString()
        }
    }
}