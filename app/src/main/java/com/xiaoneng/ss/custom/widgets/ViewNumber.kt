package com.xiaoneng.ss.custom.widgets

import android.app.Activity
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.toIntSafe
import com.xiaoneng.ss.module.school.model.QuantizeBody
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import kotlinx.android.synthetic.main.custom_input_number.view.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:
 * @date :2021/06/28 7:38 PM
 */
class ViewNumber @JvmOverloads constructor(
    val context: Activity,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val data: QuantizeTemplateBean, val commit: QuantizeBody
) : FrameLayout(context, attrs, defStyleAttr) {
    var mNumber = data.value.toIntSafe()

    init {
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomJumpItem)
        typedArray.recycle()
        init()
    }

    private fun init() {

        View.inflate(context, R.layout.custom_input_number, this)
        if (data.rules?.required?.required == true) {
            tvRequired.visibility = View.VISIBLE
        } else {
            tvRequired.visibility = View.INVISIBLE
        }

        tvJumpTitleKey.text = data.label
        etNumber.setText(mNumber.toString())
        commit.score = data.value
        etNumber.addTextChangedListener {
            var ss = it.toString()
            if (ss.toIntSafe() < data.setting?.min ?: 0) {
                ss = (data.setting?.min ?: 0).toString()
                etNumber.setText(ss)
                context.toast("注意最小值~")
            } else if (ss.toIntSafe() > data.setting?.max ?: 9999) {
                ss = (data.setting?.max ?: 9999).toString()
                etNumber.setText(ss)
                context.toast("注意最大值~")
            }
            data.value = ss
            data.rules?.required?.hasValue = !ss.isNullOrEmpty()
            commit.score = ss
            mNumber = ss.toIntSafe()
        }

        ivMinus.setOnClickListener {
            etNumber.clearFocus()
            if (mNumber > data.setting?.min ?: 0) {
                mNumber -= data.setting?.step ?: 1
                if (mNumber < data.setting?.min ?: 0) {
                    mNumber = data.setting?.min ?: 0
                }
                etNumber.setText(mNumber.toString())
                data.value = mNumber.toString()
                data.rules?.required?.hasValue = !mNumber.toString().isNullOrEmpty()
                commit.score = mNumber.toString()
            } else {
                context.toast("不能再减了~")
            }
        }
        ivPlus.setOnClickListener {
            etNumber.clearFocus()
            if (mNumber < data.setting?.max ?: 9999) {
                mNumber += data.setting?.step ?: 1
                if (mNumber > data.setting?.max ?: 9999) {
                    mNumber = data.setting?.max ?: 9999
                }
                etNumber.setText(mNumber.toString())
                data.value = mNumber.toString()
                data.rules?.required?.hasValue = !mNumber.toString().isNullOrEmpty()
                commit.score = mNumber.toString()
            } else {
                context.toast("不能再加了~")
            }
        }
    }


}