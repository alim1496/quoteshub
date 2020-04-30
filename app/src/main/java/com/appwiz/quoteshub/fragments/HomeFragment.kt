package com.appwiz.quoteshub.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.*
import com.appwiz.quoteshub.viewmodels.HomeQuotesVM

class HomeFragment : Fragment() {
    lateinit var adapter: HomeQuotesAdapter
    lateinit var adapter5: HomeQuotesAdapter
    lateinit var adapter2 : HomeAuthorsAdapter
    lateinit var adapter3 : TagsAdapter
    lateinit var adapter4 : EventsAdapter
    lateinit var viewModel: HomeQuotesVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}
