package com.appwiz.quoteshub.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.models.Video
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class VideoViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var youTubePlayer:YouTubePlayer
    private val playerView: YouTubePlayerView = itemView.findViewById(R.id.youtube_player_view)
    private val desc: TextView = itemView.findViewById(R.id.video_desc)

    fun bindView(video:Video) {
        desc.text = video.video_desc
        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(initializedYouTubePlayer: YouTubePlayer) {
                youTubePlayer = initializedYouTubePlayer
                youTubePlayer.cueVideo(video.video_id, 0f)
            }
        })
    }
}