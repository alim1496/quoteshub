package com.appwiz.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.activities.SingleAuthor
import com.appwiz.quoteshub.adapters.AuthorPagedAdapter
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.services.NetworkState
import com.appwiz.quoteshub.viewmodels.AuthorViewModel
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory


class AuthorsFragment : Fragment() {
    lateinit var authorViewModel: AuthorViewModel
    lateinit var adapter: AuthorPagedAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var loader: ProgressBar
    lateinit var error: RelativeLayout
    lateinit var bottom: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_authors, container, false)
        recyclerView = view.findViewById(R.id.author_recyclerview)
        loader = view.findViewById(R.id.authors_screen_loader)
        error = view.findViewById(R.id.auth_net_err)
        bottom = view.findViewById(R.id.btm_loader)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObserver()
        initUI()
    }

    private fun initUI() {
        adapter = AuthorPagedAdapter { author: Author ->
            val intent = Intent(context, SingleAuthor::class.java)
            intent.putExtra("authorID", author.id)
            intent.putExtra("authorname", author.name)
            startActivity(intent)
        }
        authorViewModel.getAuthorList().observe(viewLifecycleOwner, Observer { adapter.submitList(it) })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)
    }

    private fun initObserver() {
        authorViewModel = ViewModelProviders.of(this, BaseViewModelFactory{AuthorViewModel()}).get(AuthorViewModel::class.java)
        authorViewModel.getInitialState().observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.LOADING -> {
                    loader.visibility = View.VISIBLE
                    error.visibility = View.GONE
                }
                NetworkState.LOADED -> {
                    loader.visibility = View.GONE
                }
                else -> {
                    loader.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    error.visibility = View.VISIBLE
                }
            }
        })
        authorViewModel.getMoreState().observe(viewLifecycleOwner, Observer {
            if (it == NetworkState.LOADING) bottom.visibility = View.VISIBLE
            else bottom.visibility = View.GONE
        })
    }

}
