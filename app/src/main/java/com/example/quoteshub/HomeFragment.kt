package com.example.quoteshub


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var quotes: MutableList<Quote> = mutableListOf<Quote>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        var adapter : QuotesAdapter? = null

        db
            .collection("quotes")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("success", "${document.id} => ${document.data}")
                    quotes.add(Quote(document.get("quote").toString(), document.get("source").toString()))
                }
                recyclerView.layoutManager = layoutManager
                adapter = QuotesAdapter(activity, quotes)
                recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w("failure", "Error getting documents: ", exception)
            }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


}
