package com.xiaoneng.ss.custom.widgets

import android.app.Activity
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartForResult
import com.xiaoneng.ss.common.utils.showBirthDayPick
import com.xiaoneng.ss.common.utils.showTimeSection
import com.xiaoneng.ss.module.school.interfaces.IChooseStudent
import com.xiaoneng.ss.module.school.model.DepartmentBean
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
import com.xiaoneng.ss.module.school.view.AddInvolveActivity
import com.xiaoneng.ss.module.school.view.QuantizeTypeActivity
import kotlinx.android.synthetic.main.activity_folder_setting.*
import kotlinx.android.synthetic.main.custom_choose_item.view.*
import kotlinx.android.synthetic.main.custom_jump_item.view.*
import kotlinx.android.synthetic.main.custom_jump_item.view.tvJumpTitle

/**
 * @author Burning
 * @description:
 * @date :2021/06/23 7:38 PM
 */
class ViewTimeSection @JvmOverloads constructor(
    val context: Activity,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val data: QuantizeTemplateBean
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
        View.inflate(context, R.layout.custom_choose_item, this)
        tvJumpTitleKey.text = "选择时间段"
        title = "请选择时间段"
        title?.let {
            tvJumpTitle.text = title
        }

        setOnClickListener {
            if (data.name == "CascaderClass") {}else{}
            context.showTimeSection(tvJumpTitle,
                { timeStart = this },
                { timeEnd = this }
            )
        }
    }


}