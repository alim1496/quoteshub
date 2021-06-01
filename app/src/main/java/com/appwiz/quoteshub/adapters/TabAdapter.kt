package com.appwiz.quoteshub.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.appwiz.quoteshub.fragments.CategoryQuotes
import com.appwiz.quoteshub.fragments.FeaturedQuotes
import com.appwiz.quoteshub.models.Category

class TabAdapter(fragmentActivity: FragmentActivity, private var categories: List<Category>)
    : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        if (categories.isNullOrEmpty()) return 1;
        return categories.size + 1
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) FeaturedQuotes() else getCategoryQuotes(position)
    }

    fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    fun getCategoryQuotes(position: Int) : Fragment {
        val category = categories[position - 1]
        val bundle = Bundle()
        bundle.putInt("category_id", category.id)
        val fragment = CategoryQuotes()
        fragment.arguments = bundle
        return fragment
    }
}