package com.xiaoneng.ss.custom.widgets

import android.app.Activity
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartForResult
import com.xiaoneng.ss.module.school.interfaces.IChooseStudent
import com.xiaoneng.ss.module.school.model.DepartmentBean
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
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
class ViewChooseStudent @JvmOverloads constructor(
    val context: Activity,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    data: ArrayList<PropertyTypeBean>,
    position: Int
) : FrameLayout(context, attrs, defStyleAttr), IChooseStudent {
    private var title: String? = ""
    var involves: ArrayList<UserBeanSimple> = ArrayList()
    var mClass = ArrayList<DepartmentBean>()
    var receiveList: ArrayList<UserBeanSimple> = ArrayList()
    var isFirst = true

    init {
        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomJumpItem)

        typedArray.recycle()
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.custom_choose_item, this)
        tvJumpTitleKey.text = "选择学生"
        title = "请选择学生"
        title?.let {
            tvJumpTitle.text = title
        }

        setOnClickListener {
            mStartForResult<AddInvolveActivity>(context, Constant.REQUEST_CODE_COURSE) {
                putExtra(Constant.DATA, mClass)
                //从草稿箱第一次选择参与人，传入原有参与人数据
                if (isFirst) {
                    if (receiveList.size > 0) {
                        putExtra(Constant.DATA3, receiveList)
                    }
                }
                //从草稿箱第一次选择参与人，传入原有参与人数据
                putExtra(Constant.TYPE, 2)
            }
        }
        (context as QuantizeTypeActivity).mListener = this
    }

    override fun addInvolve(data: Intent) {
        isFirst = false
        involves.clear()
        receiveList.clear()
        mClass = data.getParcelableArrayListExtra(Constant.DATA)!!
        mClass.forEach {
            addPeople(it)
        }
        dealData()
    }

    private fun dealData() {
        var str = ""
        if (receiveList.size > 0) {
            receiveList.forEach {
                str = str + it.realname + "、"
                involves.add(it)
            }
            str = str.substring(0, str.length - 1)
        }
        tvJumpTitle.text = str
    }

    fun addPeople(it: DepartmentBean) {
        if (it.num!!.toInt() > 0) {
            it.list.forEach {
                receiveList.add(
                    UserBeanSimple(
                        uid = it.uid,
                        realname = it.realname,
                        classid = it.classid,
                        usertype = it.usertype
                    )
                )
            }
        }
    }
}