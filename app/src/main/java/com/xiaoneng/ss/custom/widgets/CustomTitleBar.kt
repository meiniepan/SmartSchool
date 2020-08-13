package com.xiaoneng.ss.custom.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.xiaoneng.ss.R
import kotlinx.android.synthetic.main.layout_title_bar.view.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 7:38 PM
 */
class CustomTitleBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var title: String?
    private var isWhiteIcon: Boolean = false

    init {
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomTitleBar)
        title = typedArray.getString(R.styleable.CustomTitleBar_title)
        isWhiteIcon = typedArray.getBoolean(R.styleable.CustomTitleBar_white_icon, false)
        typedArray.recycle()
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.layout_title_bar, this)
        tv_title_custom.text = title
        
    }
}