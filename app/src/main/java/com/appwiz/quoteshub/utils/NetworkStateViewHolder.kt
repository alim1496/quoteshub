package com.appwiz.quoteshub.utils

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.appwiz.quoteshub.R


class NetworkStateViewHolder private constructor(
    private val view: View
) : RecyclerView.ViewHolder(view) {
    private val progressBar: ProgressBar

    init {
        progressBar = view.findViewById(R.id.progress)
    }

    fun bindTo(networkState: NetworkState) {
        view.setVisibility(toVisibility(networkState.msg == null || networkState.msg != "-1"))
        progressBar.setVisibility(toVisibility(networkState.status === NetworkState.Status.RUNNING))
    }

    companion object {

        fun create(
            parent: ViewGroup
        ): NetworkStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.network_state_layout, parent, false)
            return NetworkStateViewHolder(view)
        }

        fun toVisibility(constraint: Boolean): Int {
            return if (constraint) View.VISIBLE else View.GONE
        }
    }
}