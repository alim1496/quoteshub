package com.example.quoteshub.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.R
import com.example.quoteshub.models.Category

class CategoryAdapter(val context: FragmentActivity?, val results: List<Category>)
    : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val result = results[position]
        return holder.setData(result)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(category : Category) {
            val catName = itemView.findViewById<RecyclerView>(R.id.category_name) as TextView
            val catCount = itemView.findViewById<RecyclerView>(R.id.category_quotes) as TextView
            catName.text = category?.name
            catCount.text = category?.quotes.toString()
        }
    }
}