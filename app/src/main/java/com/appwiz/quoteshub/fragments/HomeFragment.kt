package com.appwiz.quoteshub.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.TabAdapter
import com.appwiz.quoteshub.models.Category
import com.appwiz.quoteshub.models.LatestFeed
import com.appwiz.quoteshub.repositories.HomeRepository
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory
import com.appwiz.quoteshub.viewmodels.HomeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var adapter: TabAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: HomeViewModel
    private lateinit var topics:LiveData<List<Category>>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        adapter = TabAdapter(requireActivity(), emptyList())
        viewPager.adapter = adapter
        viewModel.loadInitialData()

        viewModel.getCategories().observe(viewLifecycleOwner, Observer { adapter.setCategories(
            viewModel.getCategories().value!!
        ) })

        val mediator = TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                if (position == 0) {
                    tab.text = "Featured"
                } else {
                    tab.text = viewModel.getCategories().value!!
                        .get(position-1).name.split(" ")[0]
                }
            })
        mediator.attach()

        return view
    }

}
