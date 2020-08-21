package com.xiaoneng.ss.custom.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.xiaoneng.ss.R
import kotlinx.android.synthetic.main.custom_jump_item.view.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 7:38 PM
 */
class CustomJumpItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var title: String?
    private var iconRes: Int = 0

    init {
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomJumpItem)
        title = typedArray.getString(R.styleable.CustomJumpItem_jumpTitle)
        iconRes = typedArray.getResourceId(R.styleable.CustomJumpItem_icon, 0)
        typedArray.recycle()
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.custom_jump_item, this)
        title?.let {
            tvJumpTitle.text = title
        }
        if (iconRes!=0) {
            ivJumpIcon.setImageResource(iconRes)
        }


    }
}