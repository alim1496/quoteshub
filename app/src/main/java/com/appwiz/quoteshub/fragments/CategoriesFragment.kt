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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.activities.ActivityQuotes
import com.appwiz.quoteshub.adapters.CategoriesAdapter
import com.appwiz.quoteshub.room.AppDB
import com.appwiz.quoteshub.room.entity.CatEntity
import com.appwiz.quoteshub.services.Injection
import com.appwiz.quoteshub.viewmodels.BaseViewModelFactory
import com.appwiz.quoteshub.viewmodels.CategoriesVM


class CategoriesFragment : Fragment() {
    lateinit var adapter : CategoriesAdapter
    lateinit var viewModel: CategoriesVM
    lateinit var progressBar: ProgressBar
    lateinit var categories: RecyclerView
    lateinit var errorLayout: RelativeLayout
    lateinit var tryBtn: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        progressBar = view.findViewById(R.id.progress)
        categories = view.findViewById(R.id.cat_recyclerview)
        errorLayout = view.findViewById(R.id.cat_net_err)
        tryBtn = view.findViewById(R.id.try_again_btn)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        viewModel.fetchFromApi()
    }

    private fun setupUI() {
        adapter = CategoriesAdapter(viewModel.categories.value?: emptyList()) { item : CatEntity, position: Int ->
            val intent = Intent(context, ActivityQuotes::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("name", item.name)
            intent.putExtra("type", "category")
            startActivity(intent)
        }
        categories.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        categories.adapter = adapter
        categories.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun setupViewModel() {
        val db = context?.let { AppDB(it) }
        if (db != null) {
            viewModel = ViewModelProviders.of(this, BaseViewModelFactory{CategoriesVM(Injection.getCategoriesRepo(db.catDao()))}).get(CategoriesVM::class.java)
        }
        viewModel.categories.observe(viewLifecycleOwner, renderCategories)
        viewModel.onMessageError.observe(viewLifecycleOwner, renderError)
    }

    private val renderCategories = Observer<List<CatEntity>> {
        categories.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        adapter.updateData(it)
    }

    private val renderError = Observer<Any> {
        progressBar.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
        tryBtn.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            errorLayout.visibility = View.GONE
            viewModel.fetchFromApi()
        }
    }

}
