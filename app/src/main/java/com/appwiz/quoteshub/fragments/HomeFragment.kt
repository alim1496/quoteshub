package com.appwiz.quoteshub.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.adapters.TabAdapter
import com.appwiz.quoteshub.repositories.HomeRepository
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory
import com.appwiz.quoteshub.viewmodels.HomeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)

        val db = context?.let { AppDB(it) }
        if (db != null) {
            viewModel = ViewModelProviders.of(this, BaseViewModelFactory{HomeViewModel(HomeRepository(db))}).get(HomeViewModel::class.java)
        }
        viewPager.adapter = TabAdapter(requireActivity(), viewModel)
        val mediator = TabLayoutMediator(tabLayout, viewPager, object : TabLayoutMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                if (position == 0) {
                    tab.text = "Latest"
                } else {
                    tab.text = "Featured"
                }
            }

        })
        mediator.attach()

        return view
    }

}
