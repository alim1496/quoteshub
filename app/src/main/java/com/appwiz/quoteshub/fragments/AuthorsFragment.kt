package com.appwiz.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.activities.SingleAuthor
import com.appwiz.quoteshub.adapters.AlphabetAdapter
import com.appwiz.quoteshub.adapters.AuthorsAdapter
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.models.AuthorModel
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.Injection
import com.appwiz.quoteshub.services.ServiceBuilder
import com.appwiz.quoteshub.utils.AutoFitGLM
import com.appwiz.quoteshub.viewmodels.AuthorsVM
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.common_error_container.*
import kotlinx.android.synthetic.main.fragment_authors.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthorsFragment : Fragment() {
    lateinit var adapter: AuthorsAdapter
    lateinit var viewModel: AuthorsVM
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

        val layoutManager = activity?.let { AutoFitGLM(it, 200) }
        setupViewModel()
        if (layoutManager != null) {
            setupUI(layoutManager)
        }

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
                            viewModel.fetchFromApi(pageRequested, letterSelected)
                        }
                    }
                }
            }
        })

        if (layoutManager != null) {
            viewModel.fetchFromApi(pageRequested, letterSelected)
        }
    }

    private fun setupUI(layoutManager: AutoFitGLM) {
        val letterManager = FlexboxLayoutManager(context)
        letterManager.flexDirection = FlexDirection.ROW
        letterManager.justifyContent = JustifyContent.CENTER

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
            adapter.emptyAdapter()
            viewModel.fetchFromApi(pageRequested, letterSelected)
        }}

        authors_screen_loader.startShimmer()
        author_recyclerview.layoutManager = layoutManager
        adapter = AuthorsAdapter(viewModel.authors.value?: emptyList(), false) { author: Author, position: Int ->
            val intent = Intent(context, SingleAuthor::class.java)
            intent.putExtra("authorID", author.id)
            intent.putExtra("authorname", author.name)
            startActivity(intent)
        }
        author_recyclerview.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, BaseViewModelFactory{AuthorsVM(Injection.getAuthorsRepo())}).get(AuthorsVM::class.java)
        viewModel.authors.observe(this, renderAuthors)
        viewModel.emptyAuthors.observe(this, renderEmpty)
        viewModel.onMessageError.observe(this, renderError)
    }

    private val renderAuthors = Observer<List<Author>> {
        authors_screen_loader.stopShimmer()
        authors_screen_loader.visibility = View.GONE
        author_recyclerview.visibility = View.VISIBLE
        adapter.addItems(it)
    }

    private val renderEmpty = Observer<Boolean> {
        authors_screen_loader.stopShimmer()
        authors_screen_loader.visibility = View.GONE
        var visible = View.GONE
        if (it) visible = View.VISIBLE
        tv_empty_author.visibility = visible
    }

    private val renderError = Observer<Any> {
        authors_screen_loader.visibility = View.GONE
        auth_net_err.visibility = View.VISIBLE
        try_again_btn.setOnClickListener {
            authors_screen_loader.visibility = View.VISIBLE
            auth_net_err.visibility = View.GONE
            viewModel.fetchFromApi(pageRequested, letterSelected)
        }
    }

}
