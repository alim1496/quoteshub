package com.appwiz.quoteshub.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Author
import com.squareup.picasso.Picasso

class AuthorViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
    private val authName: TextView = itemView.findViewById(R.id.author_name)
    private val authImg: ImageView = itemView.findViewById(R.id.author_img)
    private val authCount: TextView = itemView.findViewById(R.id.author_quotes)

    fun setData(author: Author) {
        authName.text = author.name
        Picasso.get().load(author.image).placeholder(R.drawable.avatar_placeholder)
            .into(authImg)
        authCount.text = author.quotes.toString()
    }
}