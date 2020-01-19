package com.appwiz.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.activities.SingleCategory
import com.appwiz.quoteshub.adapters.CategoriesAdapter
import com.appwiz.quoteshub.models.Category
import com.appwiz.quoteshub.models.CategoryModel
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.common_error_container.*
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

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        loadData(layoutManager)
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    private fun loadData(layoutManager: FlexboxLayoutManager) {
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
                categories_screen_loader.visibility = View.GONE
                cat_net_err.visibility = View.VISIBLE
                try_again_btn.setOnClickListener(View.OnClickListener {
                    categories_screen_loader.visibility = View.VISIBLE
                    cat_net_err.visibility = View.GONE
                    loadData(layoutManager)
                })
            }
        })
    }


}
