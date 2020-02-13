package com.appwiz.quoteshub.utils

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AutoFitGLM(context: FragmentActivity, columnWidth: Int) : GridLayoutManager(context, 1) {
    private var columnWidth:Int = 0
    private var columnWidthChanged:Boolean = true

    init {
        setColumnWidth(columnWidth)
    }

    private fun setColumnWidth(newColumnWidth: Int) {
        if (newColumnWidth > 0 && newColumnWidth != columnWidth) {
            columnWidth = newColumnWidth
            columnWidthChanged = true
        }
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        if (columnWidthChanged && columnWidth > 0) {
            val totalSpace: Int
            if (orientation == RecyclerView.VERTICAL) {
                totalSpace = width - paddingRight - paddingLeft
            } else {
                totalSpace = height - paddingTop - paddingBottom
            }
            val spanCount = Math.max(1, totalSpace / columnWidth)
            setSpanCount(spanCount)
            columnWidthChanged = false
        }
        super.onLayoutChildren(recycler, state)
    }
}