package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Author
import com.squareup.picasso.Picasso

class AuthorPagedAdapter(private val click: (Author) -> Unit) : PagedListAdapter<Author, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.author_item, parent, false)
        return AuthorViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val author = getItem(position)
        author?.let {
            (holder as AuthorViewHolder).setData(it)
            holder.itemView.setOnClickListener{ click(author)}
        }
    }

    companion object {
        private var DIFF_CALLBACK = object : DiffUtil.ItemCallback<Author>() {
            override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean {
                return oldItem.name == newItem.name && oldItem.quotes == newItem.quotes && oldItem.image == newItem.image
            }

        }
    }

    class AuthorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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