package com.appwiz.quoteshub.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class InfiniteScrollListener : RecyclerView.OnScrollListener {

    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var visibleThreshold = 1
    private var startingPageIndex = 0
    private var loading = true
    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView)
    private var layoutManager: RecyclerView.LayoutManager

    constructor(layoutManager: LinearLayoutManager) : super() {
        this.layoutManager = layoutManager
        visibleThreshold = 3
    }

    constructor(layoutManager: GridLayoutManager) : super() {
        this.layoutManager = layoutManager
        visibleThreshold = 2
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager.itemCount

        if (layoutManager is LinearLayoutManager) {
            lastVisibleItemPosition =
                (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        } else if (layoutManager is GridLayoutManager) {
            lastVisibleItemPosition =
                (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
        }

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, recyclerView)
            loading = true
        }
    }
}