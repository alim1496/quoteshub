package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Event
import com.appwiz.quoteshub.room.entity.EventEntity


class EventsAdapter(var events:List<EventEntity>) : RecyclerView.Adapter<EventsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = events[position]
        holder.eventText.text = event.text
    }

    fun updateData(data: List<EventEntity>) {
        events = data
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventText:TextView = itemView.findViewById(R.id.tv_event_text)
    }
}