package com.xiaoneng.ss.custom.widgets

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.interfaces.IChooseStudent
import com.xiaoneng.ss.module.school.model.DepartmentBean
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
import com.xiaoneng.ss.module.school.view.AddInvolveActivity
import com.xiaoneng.ss.module.school.view.QuantizeTypeActivity
import kotlinx.android.synthetic.main.custom_choose_item.view.*
import kotlinx.android.synthetic.main.custom_choose_item.view.tvJumpTitle
import kotlinx.android.synthetic.main.custom_jump_item.view.*

/**
 * @author Burning
 * @description:
 * @date :2021/06/25 7:38 PM
 */
class ViewJump @JvmOverloads constructor(
    val context: Activity,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val data: QuantizeTemplateBean
) : FrameLayout(context, attrs, defStyleAttr), IChooseStudent {
    var involves: ArrayList<UserBeanSimple> = ArrayList()
    var mClass = ArrayList<DepartmentBean>()
    var receiveList: ArrayList<UserBeanSimple> = ArrayList()
    var isFirst = true
    private val dialogSingle: Dialog by lazy { initDialogSingle() }

    init {

        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomJumpItem)

        typedArray.recycle()
        init()
    }

    private fun init() {

        View.inflate(context, R.layout.custom_choose_item, this)
        tvJumpTitleKey.text = data.label
        tvJumpTitle.hint = data.placeholder
        if (data.name == "ChoseStudents") {
            (context as QuantizeTypeActivity).mListener = this
        }
        setOnClickListener {
            if (data.name == "CascaderClass") {
                dialogSingle.show()
            } else if (data.name == "DatePickerMultiple") {
                context.showTimeSection(tvJumpTitle,
                    { data.stime = this },
                    {
                        data.etime = this
                        data.value = data.stime + "," + data.etime
                    }
                )
            } else if (data.name == "DateTimePicker") {
                context.showDateDayHourPick(tvJumpTitle) { data.value = this }
            } else if (data.name == "DatePicker") {
                context.showDateDayPick(tvJumpTitle) { data.value = this }
            }else if (data.name == "Radio") {
                dialogSingle.show()
            }else if (data.name == "Checkbox") {
                dialogSingle.show()
            }else if (data.name == "ChoseStudents") {
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

        }
    }

    private fun initDialogSingle(): Dialog {
        var titles = ArrayList<String>()
        if (data.name == "CascaderClass") {
            data.classes?.forEach {
                titles.add(it.levelclass ?: "")
            }
        } else {
            data.selections?.let { titles.addAll(it) }
        }
        // 底部弹出对话框
        var dialogType =
            Dialog(context, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_list, null)
        dialogType.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(context, 0f).toInt()
        contentView.layoutParams = params
        dialogType.window!!.setGravity(Gravity.BOTTOM)
        dialogType.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titles)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            if (data.name == "CascaderClass") {
                data.value = data.classes!![position].id
            } else {
                data.value = titles[position]
            }
            tvJumpTitle.text = titles[position]
            dialogType.dismiss()
        }
        return dialogType
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