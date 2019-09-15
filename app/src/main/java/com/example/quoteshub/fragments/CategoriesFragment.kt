package com.example.quoteshub.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quoteshub.R
import com.example.quoteshub.adapters.CategoryAdapter
import com.example.quoteshub.models.Category
import com.example.quoteshub.models.CategoryModel
import com.example.quoteshub.services.DestinationServices
import com.example.quoteshub.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CategoriesFragment : Fragment() {
    var adapter : CategoryAdapter ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layoutManager = GridLayoutManager(activity, 2)
        loadData(layoutManager)
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    private fun loadData(layoutManager: GridLayoutManager) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<CategoryModel> = destinationServices.getCategories()
        requestCall.enqueue(object: Callback<CategoryModel> {

            override fun onResponse(call: Call<CategoryModel>, response: Response<CategoryModel>) {
                if (response.isSuccessful) {
                    val categoryList : CategoryModel = response.body()!!
                    cat_recyclerview.layoutManager = layoutManager
                    adapter = CategoryAdapter(activity, categoryList.results)
                    cat_recyclerview.adapter = adapter
                }
            }

            override fun onFailure(call: Call<CategoryModel>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }


}
