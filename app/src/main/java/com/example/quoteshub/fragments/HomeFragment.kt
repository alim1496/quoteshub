package com.example.quoteshub.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.adapters.QuotesAdapter
import com.example.quoteshub.R
import com.example.quoteshub.services.DestinationServices
import com.example.quoteshub.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.quoteshub.models.Response as _response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {
    var adapter : QuotesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = RecyclerView.VERTICAL

        loadFeed(layoutManager)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun loadFeed(layoutManager : LinearLayoutManager) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<_response> = destinationServices.getFeed()
        requestCall.enqueue(object: Callback<_response> {

            override fun onResponse(call: Call<_response>, response: Response<_response>) {

                if (response.isSuccessful) {
                    val quoteList : _response = response.body()!!
                    recyclerView.layoutManager = layoutManager
                    adapter = QuotesAdapter(activity, quoteList.results)
                    recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<_response>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }

}
