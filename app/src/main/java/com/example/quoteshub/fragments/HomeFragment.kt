package com.example.quoteshub.fragments


import android.content.Intent
import android.opengl.Visibility
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
import com.example.quoteshub.activities.SingleAuthor
import com.example.quoteshub.adapters.HomeAuthorsAdapter
import com.example.quoteshub.models.Author
import com.example.quoteshub.models.FeedModel
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
    var adapter2 : HomeAuthorsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val featuredManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val authorsManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        loadFeed(layoutManager, featuredManager, authorsManager)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun loadFeed(layoutManager : LinearLayoutManager, featuredManager: LinearLayoutManager, authorsManager: LinearLayoutManager) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<FeedModel> = destinationServices.getFeed()
        requestCall.enqueue(object: Callback<FeedModel> {

            override fun onResponse(call: Call<FeedModel>, response: Response<FeedModel>) {

                if (response.isSuccessful) {
                    home_screen_loader.visibility = View.GONE
                    home_screen_container.visibility = View.VISIBLE

                    val feedResponse: FeedModel = response.body()!!

                    val recentQuotes = feedResponse.RecentQuotes
                    recent_quotes_title.text = recentQuotes.title
                    recyclerView.layoutManager = layoutManager
                    adapter = QuotesAdapter(activity, recentQuotes.data)
                    recyclerView.adapter = adapter

                    val featuredQuotes = feedResponse.FeaturedQuotes
                    featured_quotes_title.text = featuredQuotes.title
                    featured_recyclerView.layoutManager = featuredManager
                    adapter = QuotesAdapter(activity, featuredQuotes.data)
                    featured_recyclerView.adapter = adapter

                    val featuredAuthors = feedResponse.FeaturedAuthors
                    featured_authors_title.text = featuredAuthors.title
                    authors_recyclerview.layoutManager = authorsManager
                    adapter2 = activity?.let { HomeAuthorsAdapter(it, featuredAuthors.data) { author: Author, position: Int ->
                        val intent = Intent(context, SingleAuthor::class.java)
                        intent.putExtra("authorID", author.id)
                        intent.putExtra("authorname", author.name)
                        startActivity(intent)
                    } }
                    authors_recyclerview.adapter = adapter2

                    val quoteDay = feedResponse.DayQuote
                    quote_day_title.text = quoteDay.title
                    day_quote_title.text = quoteDay.data.title
                    day_quote_src.text = quoteDay.data.source.name
                }
            }

            override fun onFailure(call: Call<FeedModel>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }

}
