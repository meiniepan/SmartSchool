package com.xiaoneng.ss.custom.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.xiaoneng.ss.R
import kotlinx.android.synthetic.main.custom_badge_textview.view.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 7:38 PM
 */
class CustomBadgeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var text: String?
    private var isShow: Boolean = false

    init {
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomBadgeTextView)
        text = typedArray.getString(R.styleable.CustomBadgeTextView_text_badge)
        isShow = typedArray.getBoolean(R.styleable.CustomBadgeTextView_show_badge, false)
        typedArray.recycle()
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.custom_badge_textview, this)
        text?.let {
            tvCusText.text = text
        }
        if (isShow) {
            vCusBadge.visibility  = View.VISIBLE
        }else{
            vCusBadge.visibility  = View.GONE
        }


    }

    fun show(isShow:Boolean){
        if (isShow) {
            vCusBadge.visibility  = View.VISIBLE
        }else{
            vCusBadge.visibility  = View.GONE
        }
    }
}