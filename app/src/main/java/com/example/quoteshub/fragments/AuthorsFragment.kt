package com.example.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quoteshub.AutoFitGLM
import com.example.quoteshub.R
import com.example.quoteshub.activities.SingleAuthor
import com.example.quoteshub.activities.SingleCategory
import com.example.quoteshub.adapters.AuthorsAdapter
import com.example.quoteshub.adapters.CategoriesAdapter
import com.example.quoteshub.models.Author
import com.example.quoteshub.models.AuthorModel
import com.example.quoteshub.models.Category
import com.example.quoteshub.models.CategoryModel
import com.example.quoteshub.services.DestinationServices
import com.example.quoteshub.services.ServiceBuilder
import kotlinx.android.synthetic.main.fragment_authors.*
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
class AuthorsFragment : Fragment() {
    var adapter: AuthorsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layoutManager = AutoFitGLM(activity, 300)
        loadData(layoutManager)
        return inflater.inflate(R.layout.fragment_authors, container, false)
    }

    private fun loadData(layoutManager: GridLayoutManager) {
        val destinationServices : DestinationServices = ServiceBuilder.buildService(DestinationServices::class.java)
        val requestCall : Call<AuthorModel> = destinationServices.getAuthors()
        requestCall.enqueue(object: Callback<AuthorModel> {

            override fun onResponse(call: Call<AuthorModel>, response: Response<AuthorModel>) {
                if (response.isSuccessful) {
                    val authors : AuthorModel = response.body()!!
                    author_recyclerview.layoutManager = layoutManager
                    adapter = activity?.let { AuthorsAdapter(it, authors.results) { author: Author, position: Int ->
                        val intent = Intent(context, SingleAuthor::class.java)
                        intent.putExtra("authorID", author.id)
                        intent.putExtra("authorQuotes", author.quotes)
                        startActivity(intent)
                    } }
                    author_recyclerview.adapter = adapter
                }
            }

            override fun onFailure(call: Call<AuthorModel>, t: Throwable) {
                Log.e("alim", "${t}")
            }
        })
    }

}
