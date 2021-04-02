package com.appwiz.quoteshub.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.AuthorsAdapter
import com.appwiz.quoteshub.adapters.VideoAdapter
import com.appwiz.quoteshub.models.Video
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.appwiz.quoteshub.utils.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        getVideos(1)

        return view
    }

    private fun getVideos(page:Int) {
        val call = ServiceBuilder.buildService(DestinationServices::class.java)
        call.getVideos(page).enqueue(object : Callback<List<Video>> {
            override fun onFailure(call: Call<List<Video>>, t: Throwable) {
                loader.visibility = View.GONE
            }

            override fun onResponse(call: Call<List<Video>>, response: Response<List<Video>>) {
                if (response.isSuccessful) {
                    val videos = response.body()!!
                    loader.visibility = View.GONE
                    if (page == 1) adapter.setData(videos.toMutableList())
                }
            }

        })
    }
}