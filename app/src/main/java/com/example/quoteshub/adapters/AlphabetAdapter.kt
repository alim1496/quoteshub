package com.example.quoteshub.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.quoteshub.R

class AlphabetAdapter(val context: FragmentActivity, var letters: Array<Char>, val clcikListener: (String, Int) -> Unit)
    : RecyclerView.Adapter<AlphabetAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetAdapter.MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.letter_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return letters.size
    }

    override fun onBindViewHolder(holder: AlphabetAdapter.MyViewHolder, position: Int) {
        val result = letters[position].toString()
        holder.itemView.setOnClickListener {
            clcikListener(result, position)
        }
        holder.setData(result)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(letter:String) {
            val letterName = itemView.findViewById<RecyclerView>(R.id.tv_letter_item) as TextView
            letterName.text = letter
        }
    }
}