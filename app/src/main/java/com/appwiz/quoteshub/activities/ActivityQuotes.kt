package com.appwiz.quoteshub.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.QuotesPagedAdapter
import com.appwiz.quoteshub.services.NetworkState
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory
import com.appwiz.quoteshub.viewmodels.QuotesViewModel

class ActivityQuotes : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    val adapter = QuotesPagedAdapter(this)
    lateinit var viewModel: QuotesViewModel
    private lateinit var loader: ProgressBar
    private lateinit var recyclerView: RecyclerView
    lateinit var error: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quotes_holder_common)

        toolbar = findViewById(R.id.toolbar)
        loader = findViewById(R.id.loader)
        recyclerView = findViewById(R.id.quotesRV)
        error = findViewById(R.id.auth_net_err)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.extras?.getInt("id")!!
        val name = intent.extras?.getString("name")!!
        val type = intent.extras?.getString("type")!!

        if (type.equals("source")) title = "$name Quotes"
        else title = name

        viewModel = ViewModelProviders.of(this, BaseViewModelFactory{QuotesViewModel(id, type)}).get(QuotesViewModel::class.java)
        viewModel.getInitialState().observe(this, Observer {
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
        viewModel.getQuoteList().observe(this, Observer { adapter.submitList(it) })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
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
}