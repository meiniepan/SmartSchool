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
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.adapter.DialogMultiCheckAdapter
import com.xiaoneng.ss.module.school.interfaces.IChooseStudent
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.view.AddInvolveActivity
import com.xiaoneng.ss.module.school.view.QuantizeTypeActivity
import kotlinx.android.synthetic.main.custom_choose_item.view.*

/**
 * @author Burning
 * @description:
 * @date :2021/06/25 7:38 PM
 */
class ViewJump @JvmOverloads constructor(
    val context: Activity,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val data: QuantizeTemplateBean, val commit: QuantizeBody
) : FrameLayout(context, attrs, defStyleAttr), IChooseStudent {
    var involves: ArrayList<UserBeanSimple> = ArrayList()
    var mClass = ArrayList<DepartmentBean>()
    var receiveList: ArrayList<UserBeanSimple> = ArrayList()
    var isFirst = true
    private val dialogSingle: Dialog by lazy { initDialogSingle() }
    private val dialogMulti: Dialog by lazy { initDialogMulti() }

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
            if (context is QuantizeTypeActivity) {
                context.mListener = this
            }
        }
        if (data.rules?.required?.required == true) {
            tvRequired.visibility = View.VISIBLE
        } else {
            tvRequired.visibility = View.INVISIBLE
        }
        setOnClickListener {
            if (data.name == "CascaderClass") {
                dialogSingle.show()
            } else if (data.name == "DatePickerMultiple") {
                context.showTimeSection(tvJumpTitle,
                    { data.stime = this },
                    {
                        data.etime = this
                        data.value = tvJumpTitle.text.toString()
                        commit.stime = data.stime
                        commit.etime = data.etime
                        data.rules?.required?.hasValue = true
                    }
                )
            } else if (data.name == "DateTimePicker") {
                context.showDateDayHourPick(tvJumpTitle) {
                    data.value = tvJumpTitle.text.toString()
                    commit.checktime = this
                    data.rules?.required?.hasValue = true
                }
            } else if (data.name == "DatePicker") {
                context.showDateDayPick(tvJumpTitle) {
                    data.value = tvJumpTitle.text.toString()
                    commit.checktime = this
                    data.rules?.required?.hasValue = true
                }
            } else if (data.name == "Radio") {
                dialogSingle.show()
            } else if (data.name == "Checkbox") {
                dialogMulti.show()
            } else if (data.name == "ChoseStudents") {
                mStartForResult<AddInvolveActivity>(context, Constant.REQUEST_CODE_COURSE) {
                    putExtra(Constant.DATA2, mClass)
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
                data.value = data.classes!![position].levelclass
                commit.classid = data.classes!![position].id
            } else {
                data.value = titles[position]
            }
            data.rules?.required?.hasValue = true
            tvJumpTitle.text = titles[position]
            dialogType.dismiss()
        }
        return dialogType
    }

    private fun initDialogMulti(): Dialog {
        var titles = ArrayList<MultiCheckBean>()

        data.selections?.let {
            it.forEach {
                titles.add(MultiCheckBean(name = it))
            }
        }

        // 底部弹出对话框
        var dialogType =
            Dialog(context, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_check_multi, null)
        dialogType.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(context, 0f).toInt()
        contentView.layoutParams = params
        dialogType.window!!.setGravity(Gravity.BOTTOM)
        dialogType.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogMultiCheckAdapter(R.layout.item_dialog_multi_check, titles)
        var tvReset = contentView.findViewById<TextView>(R.id.tvReset)
        var tvConfirm = contentView.findViewById<TextView>(R.id.tvConfirm)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = GridLayoutManager(context, 3)

            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            titles[position].isChecked = !titles[position].isChecked
            dialogAdapter.notifyDataSetChanged()
        }
        tvReset.setOnClickListener {
            titles.forEach {
                it.isChecked = false
            }
            dialogAdapter.notifyDataSetChanged()
        }
        tvConfirm.setOnClickListener {
            var res = ""
            titles.forEach {
                if (it.isChecked) {
                    res = res + it.name + ","
                }
            }
            if (res.isNotEmpty()) {
                res = res.substring(0, res.length - 1)
            }
            data.value = res
            commit.rulename = res
            tvJumpTitle.text = res
            data.rules?.required?.hasValue = !res.isNullOrEmpty()
            dialogType.dismiss()
        }
        return dialogType
    }

    override fun addInvolve(data: Intent) {
        isFirst = false
        involves.clear()
        receiveList.clear()
        mClass = data.getParcelableArrayListExtra(Constant.DATA2)!!
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
        data.value = str
        commit.involve = Gson().toJson(involves)
        data.rules?.required?.hasValue = involves.size != 0
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