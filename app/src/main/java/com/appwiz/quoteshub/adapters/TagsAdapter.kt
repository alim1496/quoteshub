package com.appwiz.quoteshub.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Tag

class TagsAdapter(var tags:List<Tag>, val clickListener: (Tag, Int) -> Unit) : RecyclerView.Adapter<TagsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagsAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tag_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: TagsAdapter.MyViewHolder, position: Int) {
        val tag = tags[position]
        holder.itemView.setOnClickListener { clickListener(tag, position) }
        holder.setData(tag)
    }

    fun updateData(data: List<Tag>) {
        tags = data
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(tag : Tag?) {
            val tagName = itemView.findViewById<RecyclerView>(R.id.tag_name) as TextView
            tagName.text = tag?.name
        }
    }
}
