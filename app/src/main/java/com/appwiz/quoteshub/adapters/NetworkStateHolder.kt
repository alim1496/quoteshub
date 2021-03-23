package com.appwiz.quoteshub.adapters

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.appwiz.quoteshub.R
import com.appwiz.quoteshub.utils.NetworkState

class NetworkStateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val progressBar:ProgressBar = itemView.findViewById(R.id.progress)

    fun bindTo(networkState: NetworkState) {
        itemView.visibility = setConstraint(networkState.status == NetworkState.Status.RUNNING)
        progressBar.visibility = setConstraint(networkState.status == NetworkState.Status.RUNNING)
    }

    private fun setConstraint(constraint:Boolean) : Int {
        return if (constraint) View.VISIBLE else View.GONE
    }
}