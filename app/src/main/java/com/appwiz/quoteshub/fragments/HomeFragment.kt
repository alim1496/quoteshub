package com.appwiz.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.activities.SingleAuthor
import com.appwiz.quoteshub.activities.SingleCategory
import com.appwiz.quoteshub.activities.SingleTag
import com.appwiz.quoteshub.adapters.*
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.models.Quote
import com.appwiz.quoteshub.models.Source
import com.appwiz.quoteshub.models.Tag
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.room.entity.*
import com.appwiz.quoteshub.services.Injection
import com.appwiz.quoteshub.utils.CommonUtils
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory
import com.appwiz.quoteshub.viewmodels.HomeQuotesVM
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.action_buttons.*
import kotlinx.android.synthetic.main.common_error_container.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.lang.Exception

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
