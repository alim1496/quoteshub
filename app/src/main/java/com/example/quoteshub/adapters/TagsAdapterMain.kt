package com.example.quoteshub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.R
import com.example.quoteshub.models.Tag

class TagsAdapterMain(val context: Context, val tags:List<Tag>) : RecyclerView.Adapter<TagsAdapterMain.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAdapterMain.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tag_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: TagsAdapterMain.MyViewHolder, position: Int) {
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