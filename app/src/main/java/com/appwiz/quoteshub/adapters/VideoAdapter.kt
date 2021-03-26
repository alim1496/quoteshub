package com.appwiz.quoteshub.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Video

class VideoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var videos:MutableList<Video> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item_layout, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val video = videos.get(position)
        (holder as VideoViewHolder).bindView(video)
    }

    fun setData(videos:MutableList<Video>) {
        this.videos = videos
        notifyDataSetChanged()
    }
}