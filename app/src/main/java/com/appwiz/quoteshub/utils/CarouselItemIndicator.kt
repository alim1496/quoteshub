package com.appwiz.quoteshub.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.appwiz.quoteshub.R
import android.view.View
import android.view.LayoutInflater


class CarouselItemIndicator : RelativeLayout {
    private var height: Float = 0.toFloat()
    private var itemCount: Int = 0
    var selectedItem: Int = 0
    private val autoFlingDelay: Long = 3000
    private var scaleUpFactor: Float = 0.toFloat()
    var scaleDownFactor: Float = 0.toFloat()
    private lateinit var autoFlingByIndex: (Int) -> Unit

    val ANIMATION_DURATION: Long = 100

    constructor(context:Context, attrs: AttributeSet?, defStyleAttr:Int) : super(context, attrs, defStyleAttr) { init() }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private fun init() {
        height = context.resources.getDimension(R.dimen.carousel_indicator_height)
        setPadding(30, 0, 0, 0)

        scaleUpFactor = 3f
        scaleDownFactor = 1f
    }

    private fun resetCallback() {
        removeCallbacks(autoFling)
        visibility = View.VISIBLE
        postDelayed(autoFling, autoFlingDelay)
    }

    private val autoFling = Runnable {
        if (visibility == View.VISIBLE) {
            autoFlingByIndex((selectedItem + 1) % itemCount)
        }
        resetCallback()
    }

    fun initialize(itemCount: Int, selectedItem: Int, autoFlingByIndex: (Int) -> Unit) {
        this.itemCount = itemCount

        removeAllViews()
        this.autoFlingByIndex = autoFlingByIndex

        for (i in 0 until itemCount) {
            val view =
                LayoutInflater.from(context).inflate(R.layout.item_carousel_indicator, this, false)
            val params = RelativeLayout.LayoutParams(height.toInt(), height.toInt())

            view.setId(i + 1)
            if (i > 0) {
                params.addRule(RelativeLayout.RIGHT_OF, i)
            }
            params.addRule(RelativeLayout.CENTER_VERTICAL)
            params.setMargins(0, 0, height.toInt(), 0)

            addView(view, params)
        }

        val view =
            LayoutInflater.from(context).inflate(R.layout.item_carousel_indicator_long, this, false)
        val params = RelativeLayout.LayoutParams(height.toInt(), height.toInt())

        view.setId(itemCount + 2)
        params.addRule(RelativeLayout.ALIGN_LEFT, selectedItem)
        params.addRule(RelativeLayout.CENTER_VERTICAL)
        addView(view, params)

        resetCallback()

        this.selectedItem = selectedItem


    }

    fun changeSelection(index: Int) {
        if (index == selectedItem) return

        if (index < 0 || index >= itemCount) return

        val view1 = findViewById<View>(itemCount + 2)
        val view2 = findViewById<View>(index + 1)
        val view3 = findViewById<View>(selectedItem + 1)

        val toTranslate = (view2.left - view3.left).toFloat()
        view1.animate().translationXBy(toTranslate)
            .setDuration(ANIMATION_DURATION)
            .start()

        selectedItem = index
    }
}