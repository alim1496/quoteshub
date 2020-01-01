package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Event


class EventsAdapter(val context: FragmentActivity?, val events:List<Event>) : RecyclerView.Adapter<EventsAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventsAdapter.MyViewHolder, position: Int) {
        val event = events[position]
        holder.setData(event)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(event : Event?) {
            val eventText = itemView.findViewById<RecyclerView>(R.id.tv_event_text) as TextView
            eventText.text = event?.text
        }
    }
}