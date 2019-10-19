package com.example.quoteshub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.R
import com.example.quoteshub.models.Tag

class TagsAdapter(val context: FragmentActivity?, val tags:List<Tag>) : RecyclerView.Adapter<TagsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tag_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: TagsAdapter.MyViewHolder, position: Int) {
        val tag = tags[position]
        holder.setData(tag)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(tag : Tag?) {
            val tagName = itemView.findViewById<RecyclerView>(R.id.tag_name) as TextView
            tagName.text = tag?.name
        }
    }
}