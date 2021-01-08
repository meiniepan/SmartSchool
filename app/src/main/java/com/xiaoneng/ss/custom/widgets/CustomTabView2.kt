package com.xiaoneng.ss.custom.widgets

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import com.xiaoneng.ss.R
import kotlinx.android.synthetic.main.custom_tab_view.view.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/12 7:38 PM
 */
class CustomTabView2 @JvmOverloads constructor(
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

    fun setText(text: String) {
        text?.let {
            tvCustomTab.text = text
        }
    }

    private fun check() {
        if (this.isCheck) {
            doCheck()
        } else {
            doCheckNot()
        }
    }

    private fun doCheckNot() {
        tvCustomTab.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)
        tvCustomTab.setTextColor(resources.getColor(R.color.colorGray555))
        vCustomTab.visibility = View.GONE
    }

    private fun doCheck() {
        tvCustomTab.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18f)
        tvCustomTab.setTextColor(resources.getColor(R.color.themeColor))
        vCustomTab.visibility = View.VISIBLE
    }
}