package com.appwiz.quoteshub.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.appwiz.quoteshub.fragments.FeaturedQuotes
import com.appwiz.quoteshub.fragments.LatestQuotes
import com.appwiz.quoteshub.viewmodels.HomeViewModel

class TabAdapter(fragmentActivity: FragmentActivity, val viewModel: HomeViewModel) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) LatestQuotes(viewModel) else FeaturedQuotes(viewModel)
    }
}