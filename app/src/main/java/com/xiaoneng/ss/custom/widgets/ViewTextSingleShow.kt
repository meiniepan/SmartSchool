package com.xiaoneng.ss.custom.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.mFromJson
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
import kotlinx.android.synthetic.main.custom_text_single_show.view.*

/**
 * @author Burning
 * @description:
 * @date :2021/06/25 7:38 PM
 */
class ViewTextSingleShow @JvmOverloads constructor(
    context: Context,
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

        View.inflate(context, R.layout.custom_text_single_show, this)
        var valueShow = data.value
        if (data.name == "CascaderClass") {

        } else if (data.name == "DatePickerMultiple") {

        } else if (data.name == "DateTimePicker") {
        } else if (data.name == "DatePicker") {

        } else if (data.name == "Radio") {
        } else if (data.name == "Checkbox") {
        } else if (data.name == "ChoseStudents") {
            var involves = mFromJson<ArrayList<UserBeanSimple>>(data.value)
            var str = ""
            involves?.let {
                if (involves.size > 0) {
                    involves.forEach {
                        str = str + it.realname + "、"
                    }
                    str = str.substring(0, str.length - 1)
                }
            }
            valueShow = str
        }

        tvJumpTitleKey.text = data.label
        tvJumpTitle.text = valueShow
    }


}