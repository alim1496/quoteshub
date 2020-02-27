package com.appwiz.quoteshub.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.QuotesAdapter
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.services.Injection
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory
import com.appwiz.quoteshub.viewmodels.SingleTagVM
import kotlinx.android.synthetic.main.single_tag.*


class SingleTag : AppCompatActivity() {
    lateinit var adapter: QuotesAdapter
    lateinit var viewModel: SingleTagVM
    var scrolling: Boolean = false
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var pastVisiblesItems: Int = 0
    var pageRequested: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.single_tag)
        setSupportActionBar(tag_tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id: Int? = intent.extras?.getInt("tagID")
        val name: String? = intent.extras?.getString("tagName")
        title = name

        val layoutManager = LinearLayoutManager(this)

        setupViewmodel()
        setupUI(layoutManager)
        if (id != null) viewModel.fetchFromApi(id, pageRequested)

        single_tag_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    scrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()
                    if (scrolling && id != null) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            pageRequested += 1
                            viewModel.fetchFromApi(id, pageRequested)
                        }
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupUI(layoutManager: LinearLayoutManager) {
        single_tag_loader.startShimmer()
        single_tag_recycler.layoutManager = layoutManager
        adapter = QuotesAdapter(applicationContext, viewModel.tagQuotes.value?: emptyList(), false)
        single_tag_recycler.adapter = adapter
    }

    private fun setupViewmodel() {
        viewModel = ViewModelProviders.of(this, BaseViewModelFactory{SingleTagVM(Injection.getSingleTagRepo())}).get(SingleTagVM::class.java)
        viewModel.tagQuotes.observe(this, renderTagQuotes)
    }

    private val renderTagQuotes = Observer<List<Quote>> {
        single_tag_loader.stopShimmer()
        single_tag_loader.visibility = View.GONE
        single_tag_recycler.visibility = View.VISIBLE
        adapter.addItems(it)
    }
}