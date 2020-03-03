package com.appwiz.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.activities.SingleAuthor
import com.appwiz.quoteshub.adapters.AlphabetAdapter
import com.appwiz.quoteshub.adapters.AuthorsAdapter
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.models.AuthorModel
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.appwiz.quoteshub.utils.AutoFitGLM
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.common_error_container.*
import kotlinx.android.synthetic.main.fragment_authors.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthorsFragment : Fragment() {
    var adapter: AuthorsAdapter? = null
    var scrolling: Boolean = false
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var pastVisiblesItems: Int = 0
    var pageRequested: Int = 1
    var letterSelected: String = "A"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_authors, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val title: TextView = activity!!.findViewById(R.id.app_tool_bar_title)
        title.text = "Authors"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val letterManager = FlexboxLayoutManager(context)
        letterManager.flexDirection = FlexDirection.ROW
        letterManager.justifyContent = JustifyContent.CENTER

        val layoutManager = activity?.let { AutoFitGLM(it, 200) }

        val letters = arrayOf('A','B','C','D','E','F','G','H','I','J','K','L','M',
            'N','O','P','Q','R','S','T','U','V','W','X','Y','Z')
        alphabet_recycler.layoutManager = letterManager
        alphabet_recycler.adapter = activity?.let { AlphabetAdapter(it, letters) { letter: String, positon: Int ->
            pageRequested = 1
            letterSelected = letter
            authors_screen_loader.visibility = View.VISIBLE
            authors_screen_loader.startShimmer()
            author_recyclerview.visibility = View.GONE
            tv_empty_author.visibility = View.GONE
            if (layoutManager != null) {
                loadData(layoutManager, pageRequested, letterSelected)
            }
        }}

        author_recyclerview?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    scrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    if (layoutManager != null) {
                        visibleItemCount = layoutManager.childCount
                        totalItemCount = layoutManager.itemCount
                        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    }
                    if (scrolling) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            pageRequested += 1
                            loadMore(pageRequested, letterSelected)
                        }
                    }
                }
            }
        })

        if (layoutManager != null) {
            loadData(layoutManager, pageRequested, letterSelected)
        }
    }

    private fun loadMore(page: Int, letter: String) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<AuthorModel> = destinationServices.getAuthors(page, letter)
        requestCall.enqueue(object: Callback<AuthorModel> {
            override fun onResponse(call: Call<AuthorModel>, response: retrofit2.Response<AuthorModel>) {

                if (response.isSuccessful) {
                    val authors : AuthorModel = response.body()!!
                    adapter?.addItems(authors.results)
                }
            }

            override fun onFailure(call: Call<AuthorModel>, t: Throwable) {
                // single_author_loader_more.visibility = View.GONE
            }
        })
    }

    private fun loadData(layoutManager: AutoFitGLM, page: Int, letter: String) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<AuthorModel> = destinationServices.getAuthors(page, letter)
        requestCall.enqueue(object: Callback<AuthorModel> {

            override fun onResponse(call: Call<AuthorModel>, response: Response<AuthorModel>) {
                if (response.isSuccessful) {
                    authors_screen_loader.stopShimmer()
                    authors_screen_loader.visibility = View.GONE
                    author_recyclerview.visibility = View.VISIBLE
                    val authors : AuthorModel = response.body()!!
                    author_recyclerview.layoutManager = layoutManager
                    adapter = AuthorsAdapter(authors.results, false) { author: Author, position: Int ->
                        val intent = Intent(context, SingleAuthor::class.java)
                        intent.putExtra("authorID", author.id)
                        intent.putExtra("authorname", author.name)
                        startActivity(intent)
                    }
                    author_recyclerview.adapter = adapter
                    if (authors.results.size == 0) {
                        tv_empty_author.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<AuthorModel>, t: Throwable) {
                authors_screen_loader.visibility = View.GONE
                auth_net_err.visibility = View.VISIBLE
                try_again_btn.setOnClickListener {
                    authors_screen_loader.visibility = View.VISIBLE
                    auth_net_err.visibility = View.GONE
                    loadData(layoutManager, pageRequested, letterSelected)
                }
            }
        })
    }

}
