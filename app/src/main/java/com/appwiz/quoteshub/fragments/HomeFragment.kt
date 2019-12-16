package com.appwiz.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.adapters.HomeQuotesAdapter
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.activities.SingleAuthor
import com.appwiz.quoteshub.activities.SingleCategory
import com.appwiz.quoteshub.activities.SingleTag
import com.appwiz.quoteshub.adapters.AuthorsAdapter
import com.appwiz.quoteshub.adapters.TagsAdapter
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.models.FeedModel
import com.appwiz.quoteshub.models.Tag
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.appwiz.quoteshub.utils.CommonUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.action_buttons_bar.*
import kotlinx.android.synthetic.main.common_error_container.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.appwiz.quoteshub.models.Response as _response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {
    var adapter : HomeQuotesAdapter? = null
    var adapter2 : AuthorsAdapter? = null
    var adapter3 : TagsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val featuredManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        val authorsManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val tagsManager = FlexboxLayoutManager(activity)
        tagsManager.flexDirection = FlexDirection.ROW
        tagsManager.justifyContent = JustifyContent.FLEX_START

        loadFeed(layoutManager, featuredManager, authorsManager, tagsManager)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun loadFeed(layoutManager : LinearLayoutManager, featuredManager: LinearLayoutManager,
                         authorsManager: LinearLayoutManager, tagsManager: FlexboxLayoutManager) {
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
                    adapter = HomeQuotesAdapter(activity, recentQuotes.data)
                    recyclerView.adapter = adapter

                    val featuredQuotes = feedResponse.FeaturedQuotes
                    featured_quotes_title.text = featuredQuotes.title
                    featured_recyclerView.layoutManager = featuredManager
                    adapter = HomeQuotesAdapter(activity, featuredQuotes.data)
                    featured_recyclerView.adapter = adapter

                    val featuredAuthors = feedResponse.FeaturedAuthors
                    featured_authors_title.text = featuredAuthors.title
                    authors_recyclerview.layoutManager = authorsManager
                    adapter2 = activity?.let { AuthorsAdapter(it, featuredAuthors.data) { author: Author, position: Int ->
                        val intent = Intent(context, SingleAuthor::class.java)
                        intent.putExtra("authorID", author.id)
                        intent.putExtra("authorname", author.name)
                        intent.putExtra("authorQuotes", author.quotes)
                        startActivity(intent)
                    } }
                    authors_recyclerview.adapter = adapter2

                    val quoteDay = feedResponse.DayQuote
                    quote_day_title.text = quoteDay.title
                    day_quote_title.text = quoteDay.data.title
                    day_quote_src.text = quoteDay.data.source.name
                    day_tag_recycler.layoutManager = tagsManager
                    adapter3 = activity?.let {
                        TagsAdapter(it, quoteDay.data.tags) { item: Tag, position: Int ->
                            val intent = Intent(context, SingleTag::class.java)
                            intent.putExtra("tagID", item.id)
                            intent.putExtra("tagName", item.name)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    }
                    day_tag_recycler.adapter = adapter3
                    action_copy.setOnClickListener(View.OnClickListener {
                        context?.let { it1 -> CommonUtils().copyQuote(it1, quoteDay.data.title) }
                    })
                    action_share.setOnClickListener(View.OnClickListener {
                        context?.let { it1 -> CommonUtils().shareQuote(it1, quoteDay.data.title) }
                    })
                    recent_see_all.setOnClickListener(View.OnClickListener {
                        val intent = Intent(context, SingleCategory::class.java)
                        intent.putExtra("catName", "Recent Quotes")
                        intent.putExtra("moreName", "recent=true")
                        startActivity(intent)
                    })
                    featured_see_all.setOnClickListener(View.OnClickListener {
                        val intent = Intent(context, SingleCategory::class.java)
                        intent.putExtra("catName", "Featured Quotes")
                        intent.putExtra("moreName", "featured=true")
                        startActivity(intent)
                    })
                }
            }

            override fun onFailure(call: Call<FeedModel>, t: Throwable) {
                home_screen_loader.visibility = View.GONE
                net_err_holder.visibility = View.VISIBLE
                try_again_btn.setOnClickListener(View.OnClickListener {
                    home_screen_loader.visibility = View.VISIBLE
                    net_err_holder.visibility = View.GONE
                    loadFeed(layoutManager, featuredManager, authorsManager, tagsManager)
                })
            }
        })
    }

}
