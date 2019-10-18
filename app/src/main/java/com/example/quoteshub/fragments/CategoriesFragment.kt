package com.example.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quoteshub.AutoFitGLM
import com.example.quoteshub.R
import com.example.quoteshub.activities.SingleCategory
import com.example.quoteshub.adapters.CategoriesAdapter
import com.example.quoteshub.models.Category
import com.example.quoteshub.models.CategoryModel
import com.example.quoteshub.services.DestinationServices
import com.example.quoteshub.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_categories.*
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
    var adapter : CategoriesAdapter ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layoutManager = AutoFitGLM(activity, 500)
        loadData(layoutManager)
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    private fun loadData(layoutManager: GridLayoutManager) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<CategoryModel> = destinationServices.getCategories()
        requestCall.enqueue(object: Callback<CategoryModel> {

            override fun onResponse(call: Call<CategoryModel>, response: Response<CategoryModel>) {
                if (response.isSuccessful) {
                    categories_screen_loader.visibility = View.GONE
                    cat_recyclerview.visibility = View.VISIBLE
                    val categoryList : CategoryModel = response.body()!!
                    cat_recyclerview.layoutManager = layoutManager
                    adapter = CategoriesAdapter(activity, categoryList.results) { item : Category, position: Int ->
                        val intent = Intent(context, SingleCategory::class.java)
                        intent.putExtra("catID", item.id)
                        intent.putExtra("catName", item.name)
                        startActivity(intent)
                    }
                    cat_recyclerview.adapter = adapter
                }
            }

            override fun onFailure(call: Call<CategoryModel>, t: Throwable) {
                Log.e("alim", "oh ho")
            }
        })
    }


}
