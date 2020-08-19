package com.xiaoneng.ss.custom.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.xiaoneng.ss.R
import kotlinx.android.synthetic.main.custom_tab_view.view.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 7:38 PM
 */
class CustomTabView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var text: String?
    private var isCheck: Boolean = false

    init {
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomTabView)
        text = typedArray.getString(R.styleable.CustomTabView_text)
        isCheck = typedArray.getBoolean(R.styleable.CustomTabView_check,false)
        typedArray.recycle()
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.custom_tab_view, this)

        text?.let {
            tvCustomTab.text = text
            check()
        }


    }

    fun setChecked(isCheck: Boolean) {
        this.isCheck = isCheck
        check()
    }

    private fun check() {
        if (this.isCheck) {
            doCheck()
        } else {
            doCheckNot()
        }
    }

    private fun doCheckNot() {
        tvCustomTab.textSize = 16f
        tvCustomTab.setTextColor(resources.getColor(R.color.colorGray999))
        vCustomTab.visibility = View.GONE
    }

    private fun doCheck() {
        tvCustomTab.textSize = 24f
        tvCustomTab.setTextColor(resources.getColor(R.color.titleBlack))
        vCustomTab.visibility = View.VISIBLE
    }
}