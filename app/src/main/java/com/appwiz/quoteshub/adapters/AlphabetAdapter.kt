package com.appwiz.quoteshub.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import kotlinx.android.synthetic.main.letter_item.view.*

class AlphabetAdapter(val context: FragmentActivity, var letters: Array<Char>, val clcikListener: (String, Int) -> Unit)
    : RecyclerView.Adapter<AlphabetAdapter.MyViewHolder>() {
    private var rowIndex: Int = 0

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
            rowIndex = position
            notifyDataSetChanged()
        }

        holder.setData(result)

        if (rowIndex == position) {
            holder.itemView.setBackgroundColor(Color.parseColor("#48C77C"))
            holder.itemView.tv_letter_item.setTextColor(Color.parseColor("#f2f2f2"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#f2f2f2"))
            holder.itemView.tv_letter_item.setTextColor(Color.parseColor("#00574B"))
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(letter:String) {
            val letterName = itemView.findViewById(R.id.tv_letter_item) as TextView
            letterName.text = letter
        }
    }
}