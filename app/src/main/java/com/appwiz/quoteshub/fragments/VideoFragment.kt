package com.appwiz.quoteshub.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.AuthorsAdapter
import com.appwiz.quoteshub.adapters.VideoAdapter
import com.appwiz.quoteshub.models.Video
import com.appwiz.quoteshub.utils.NetworkState

class VideoFragment : Fragment() {

    lateinit var adapter: VideoAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var loader: ProgressBar
    lateinit var error: RelativeLayout
    private lateinit var networkState: MutableLiveData<NetworkState>
    private var pageCount = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_authors, container, false)
        recyclerView = view.findViewById(R.id.author_recyclerview)
        loader = view.findViewById(R.id.authors_screen_loader)
        error = view.findViewById(R.id.auth_net_err)
        networkState = MutableLiveData()

        adapter = VideoAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        loader.visibility = View.GONE
        val videos:MutableList<Video> = mutableListOf()
        videos.add(Video(1, "This is a cool video", "6JYIGclVQdw"))
        videos.add(Video(2, "This is a hot video", "LvetJ9U_tVY"))
        videos.add(Video(3, "This is a good video", "8T6rsI6WfGs"))
        videos.add(Video(4, "This is a bad video", "lYhgQufAFZ4"))
        videos.add(Video(5, "This is a fine video", "XtO_p9SrW1s"))
        adapter.setData(videos)
        return view
    }
}