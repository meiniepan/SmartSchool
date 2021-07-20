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
    val data: QuantizeTemplateBean,val commit: QuantizeBody
) : FrameLayout(context, attrs, defStyleAttr) {
    var mNumber = 0

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
        data.value = "0"
        etNumber.setText(mNumber.toString())
        etNumber.addTextChangedListener {
            data.value = it.toString()
            data.rules?.required?.hasValue = !it.toString().isNullOrEmpty()
            commit.score = it.toString()
            mNumber = it.toString().toIntSafe()
        }

        ivMinus.setOnClickListener {
            etNumber.clearFocus()
            if (mNumber > 0) {
                mNumber -= 1
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
            mNumber += 1
            etNumber.setText(mNumber.toString())
            data.value = mNumber.toString()
            data.rules?.required?.hasValue = !mNumber.toString().isNullOrEmpty()
            commit.score = mNumber.toString()
        }
    }


}