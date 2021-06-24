package com.xiaoneng.ss.custom.widgets

import android.app.Activity
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import kotlinx.android.synthetic.main.custom_text.view.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:
 * @date :2021/06/24 7:38 PM
 */
class ViewText @JvmOverloads constructor(
    val context: Activity,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    data: ArrayList<PropertyTypeBean>,
    position: Int
) : FrameLayout(context, attrs, defStyleAttr) {
    private var title: String? = ""
    var timeStart: String? = null
    var timeEnd: String? = null

    init {
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomJumpItem)

        typedArray.recycle()
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.custom_text, this)
        tvJumpTitleKey.text = "情况说明"

        tvJumpTitle.addTextChangedListener{
            context.toast(it.toString())
        }
    }


}