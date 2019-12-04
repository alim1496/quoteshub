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

class HomeAuthorsAdapter(val context: FragmentActivity, val authors: List<Author>, val clcikListener: (Author, Int) -> Unit)
    : RecyclerView.Adapter<HomeAuthorsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAuthorsAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_author_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    override fun onBindViewHolder(holder: HomeAuthorsAdapter.MyViewHolder, position: Int) {
        val result = authors[position]
        holder.itemView.setOnClickListener { clcikListener(result, position) }
        return holder.setData(result)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(author: Author) {
            val authName = itemView.findViewById<RecyclerView>(R.id.home_author_name) as TextView
            val authImg = itemView.findViewById<RecyclerView>(R.id.home_author_img) as ImageView

            authName.text = author.name
            Picasso.get().load(author.image).placeholder(R.drawable.avatar_placeholder).into(authImg)
        }
    }
}