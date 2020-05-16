package com.appwiz.quoteshub.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.PagerSnapHelper


class SliderLayoutManager(context: Context, callback: (Int) -> Unit) :
    LinearLayoutManager(context) {

    private val snapHelper: PagerSnapHelper

    private val callback: (Int) -> Unit
    private var recyclerView: RecyclerView? = null

    private val recyclerViewCenterX: Int
        get() = if (recyclerView != null)
            (recyclerView!!.right - recyclerView!!.left) / 2 + recyclerView!!.left
        else
            0

    init {
        init()
        this.callback = callback
        snapHelper = PagerSnapHelper()
    }

    private fun init() {
        orientation = LinearLayoutManager.HORIZONTAL
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        recyclerView = view

        // Smart snapping
        snapHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDetachedFromWindow(view: RecyclerView, recycler: RecyclerView.Recycler?) {
        super.onDetachedFromWindow(view, recycler)
        snapHelper.attachToRecyclerView(null)
    }

    override fun scrollHorizontallyBy(
        dx: Int, recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return if (orientation == LinearLayoutManager.HORIZONTAL) {
            super.scrollHorizontallyBy(dx, recycler, state)
        } else {
            0
        }
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)

        // When scroll stops we notify on the selected item
        if (state == RecyclerView.SCROLL_STATE_IDLE) {

            if (recyclerView == null) return
            // Find the closest child to the recyclerView center --> this is the selected item.
            val recyclerViewCenterX = recyclerViewCenterX
            var minDistance = recyclerView!!.width
            var position = -1
            for (i in 0 until recyclerView!!.childCount) {
                val child = recyclerView!!.getChildAt(i)
                val childCenterX =
                    getDecoratedLeft(child) + (getDecoratedRight(child) - getDecoratedLeft(child)) / 2
                val newDistance = Math.abs(childCenterX - recyclerViewCenterX)
                if (newDistance < minDistance) {
                    minDistance = newDistance
                    position = recyclerView!!.getChildLayoutPosition(child)
                }
            }

            // Notify on item selection
            callback(position)
        }
    }
}