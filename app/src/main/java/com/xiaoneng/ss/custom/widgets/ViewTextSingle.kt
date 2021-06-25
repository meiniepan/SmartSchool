package com.xiaoneng.ss.custom.widgets

import android.app.Activity
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import kotlinx.android.synthetic.main.custom_text.view.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:
 * @date :2021/06/25 7:38 PM
 */
class ViewTextSingle @JvmOverloads constructor(
    val context: Activity,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val data: QuantizeTemplateBean
) : FrameLayout(context, attrs, defStyleAttr) {

    init {

        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomJumpItem)

        typedArray.recycle()
        init()
    }

    private fun init() {

        View.inflate(context, R.layout.custom_text_single, this)
        tvJumpTitleKey.text = data.label
        tvJumpTitle.hint = data.placeholder
        tvJumpTitle.addTextChangedListener {
            data.value = it.toString()
        }
    }


}