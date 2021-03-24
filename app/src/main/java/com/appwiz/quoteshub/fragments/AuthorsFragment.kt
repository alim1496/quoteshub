package com.appwiz.quoteshub.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.activities.SingleAuthor
import com.appwiz.quoteshub.adapters.AuthorsAdapter
import com.appwiz.quoteshub.models.Author
import com.appwiz.quoteshub.services.DestinationServices
import com.appwiz.quoteshub.services.ServiceBuilder
import com.appwiz.quoteshub.utils.InfiniteScrollListener
import com.appwiz.quoteshub.utils.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthorsFragment : Fragment() {
    lateinit var adapter: AuthorsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var loader: ProgressBar
    lateinit var error: RelativeLayout
    private lateinit var networkState: MutableLiveData<NetworkState>
    private var pageCount = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_authors, container, false)
        recyclerView = view.findViewById(R.id.author_recyclerview)
        loader = view.findViewById(R.id.authors_screen_loader)
        error = view.findViewById(R.id.auth_net_err)
        networkState = MutableLiveData()

        initUI()
        fetchData(1)

        return view
    }

    private fun initUI() {
        adapter = AuthorsAdapter { author: Author ->
            val intent = Intent(context, SingleAuthor::class.java)
            intent.putExtra("authorID", author.id)
            intent.putExtra("authorname", author.name)
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        val glm = GridLayoutManager(context, 2)
        recyclerView.layoutManager = glm

        networkState.observe(viewLifecycleOwner, Observer {
            (adapter::setNetworkState)(it)
        })

        val listener = object: InfiniteScrollListener(glm) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                pageCount += 1
                if (networkState.value == NetworkState.LOADED) {
                    fetchData(pageCount)
                }
            }

        }
        recyclerView.addOnScrollListener(listener)

    }

    private fun fetchData(page:Int) {
        val call = ServiceBuilder.buildService(DestinationServices::class.java)
        if (page > 1) {
            networkState.postValue(NetworkState.LOADING)
        } else {
            networkState.postValue(NetworkState.LOADED)
        }
        call.getAuthors(page).enqueue(object : Callback<List<Author>> {
            override fun onFailure(call: Call<List<Author>>, t: Throwable) {
                loader.visibility = View.GONE
                if (page > 1) {
                    networkState.postValue(NetworkState.error("something went wrong"))
                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call<List<Author>>, response: Response<List<Author>>) {
                if (response.isSuccessful) {
                    val authors = response.body()!!
                    if (page == 1) adapter.reload(authors.toMutableList())
                    else {
                        adapter.append(authors.toMutableList())
                        networkState.postValue(NetworkState.LOADED)
                    }
                    loader.visibility = View.GONE
                }
            }

        })
    }
}
