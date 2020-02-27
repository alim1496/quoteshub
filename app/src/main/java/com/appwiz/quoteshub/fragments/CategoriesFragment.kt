package com.appwiz.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.activities.SingleCategory
import com.appwiz.quoteshub.adapters.CategoriesAdapter
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.room.entity.CatEntity
import com.appwiz.quoteshub.services.Injection
import com.appwiz.quoteshub.utils.AutoFitGLM
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory
import com.appwiz.quoteshub.viewmodels.CategoriesVM
import kotlinx.android.synthetic.main.common_error_container.*
import kotlinx.android.synthetic.main.fragment_categories.*


class CategoriesFragment : Fragment() {
    lateinit var adapter : CategoriesAdapter
    lateinit var viewModel: CategoriesVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        viewModel.fetchFromApi()
    }

    private fun setupUI() {
        categories_screen_loader.startShimmer()
        adapter = CategoriesAdapter(viewModel.categories.value?: emptyList()) { item : CatEntity, position: Int ->
            val intent = Intent(context, SingleCategory::class.java)
            intent.putExtra("catID", item.id)
            intent.putExtra("catName", item.name)
            startActivity(intent)
        }
        cat_recyclerview.layoutManager = activity?.let { AutoFitGLM(it, 250) }
        cat_recyclerview.adapter = adapter
    }

    private fun setupViewModel() {
        val db = context?.let { AppDB(it) }
        if (db != null) {
            viewModel = ViewModelProviders.of(this, BaseViewModelFactory{CategoriesVM(Injection.getCategoriesRepo(db.catDao()))}).get(CategoriesVM::class.java)
        }
        viewModel.categories.observe(this, renderCategories)
        viewModel.onMessageError.observe(this, renderError)
    }

    private val renderCategories = Observer<List<CatEntity>> {
        cat_recyclerview.visibility = View.VISIBLE
        categories_screen_loader.stopShimmer()
        categories_screen_loader.visibility = View.GONE
        adapter.updateData(it)
    }

    private val renderError = Observer<Any> {
        categories_screen_loader.visibility = View.GONE
        cat_net_err.visibility = View.VISIBLE
        try_again_btn.setOnClickListener {
            categories_screen_loader.visibility = View.VISIBLE
            cat_net_err.visibility = View.GONE
            viewModel.fetchFromApi()
        }
    }

}
