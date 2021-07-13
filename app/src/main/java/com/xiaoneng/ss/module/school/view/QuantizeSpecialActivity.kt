package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.model.ClassBean
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.adapter.DialogMultiCheckAdapter
import com.xiaoneng.ss.module.school.adapter.PropertyTypeAdapter
import com.xiaoneng.ss.module.school.interfaces.IChooseStudent
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_quantize_type_special.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:
 * @date :2020/10/23 3:17 PM
 */
class QuantizeSpecialActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: PropertyTypeAdapter
    var mBean: QuantizeTypeBean? = null
    var mData: ArrayList<QuantizeTemplateBean> = ArrayList()
    var mDataType: ArrayList<QuantizeTypeBean>? = null
    var mDataClasses: ArrayList<ClassBean>? = ArrayList()
    var commit = QuantizeBody()
    var arr1 = ArrayList<String>()
    var classes: ArrayList<ClassBean>? = null
    var involves: ArrayList<UserBeanSimple> = ArrayList()
    var mClass = ArrayList<DepartmentBean>()
    var receiveList: ArrayList<UserBeanSimple> = ArrayList()
    var isFirst = true
    private val dialogSingleClass: Dialog by lazy { initDialogSingleClass() }
    private val dialogSingleAct: Dialog by lazy { initDialogSingleAct() }
    private val dialogMulti: Dialog by lazy { initDialogMulti() }

    override fun getLayoutId(): Int {
        return R.layout.activity_quantize_type_special
    }

    override fun initView() {
        super.initView()
        mBean = intent.getParcelableExtra(Constant.DATA)
        mDataType = intent.getParcelableArrayListExtra(Constant.DATA2)
        tvConfirmQuantize.setOnClickListener {
            if (involves.size == 0 || commit.stime.isNullOrEmpty() || commit.actname.isNullOrEmpty() || commit.rulename.isNullOrEmpty() || commit.remark.isNullOrEmpty()) {
                toast(R.string.lack_info)
                return@setOnClickListener
            }
            mViewModel.addMoralScoreSpecial(commit)
        }
        initAdapter()
    }

    private fun initUI(mDataClasses: ArrayList<ClassBean>?) {
        classes = mDataClasses
        arr1.add("病假")
        arr1.add("事假")
        arr1.add("外出考试")
        arr1.add("学校活动")
        arr1.add("其他")

//        llClass.setOnClickListener {
//            dialogSingleClass.show()
//        }

        llStudent.setOnClickListener {
            mStartForResult<AddInvolveActivity>(this, Constant.REQUEST_CODE_COURSE) {
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

        llTimeSec.setOnClickListener {
            showTimeSection(tvTimeSec,
                { commit.stime = this },
                {
                    commit.etime = this
                }
            )
        }

        llAct.setOnClickListener {
            dialogSingleAct.show()
        }

        llRule.setOnClickListener {
            dialogMulti.show()
        }
        tvRemark.addTextChangedListener {
            commit.remark = it.toString()
        }
    }


    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        showLoading()
        mViewModel.getClassesByTea()
    }

    private fun initAdapter() {


    }

    private fun initDialogSingleClass(): Dialog {
        var titles = ArrayList<String>()
        classes?.forEach {
            titles.add(it.levelclass ?: "")
        }

        // 底部弹出对话框
        var dialogType =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        dialogType.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
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

            commit.classid = classes!![position].id

//            tvClass.text = titles[position]
            dialogType.dismiss()
        }
        return dialogType
    }

    private fun initDialogSingleAct(): Dialog {
        var titles = ArrayList<String>()
        arr1?.forEach {
            titles.add(it)
        }

        // 底部弹出对话框
        var dialogType =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        dialogType.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
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

            commit.actname = titles[position]

            tvAct.text = titles[position]
            dialogType.dismiss()
        }
        return dialogType
    }

    private fun initDialogMulti(): Dialog {
        var titles = ArrayList<MultiCheckBean>()
        mDataType?.let {
            it.forEach {
                titles.add(MultiCheckBean(id = it.id, name = it.typename))
            }
        }
        // 底部弹出对话框
        var dialogType =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_check_multi, null)
        dialogType.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
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
            var res2 = ""
            titles.forEach {
                if (it.isChecked) {
                    res = res + it.id + ","
                    res2 = res2 + it.name + ","
                }
            }
            if (res.isNotEmpty()) {
                res = res.substring(0, res.length - 1)
                res2 = res2.substring(0, res2.length - 1)
            }
            commit.rulename = res2
            commit.typeid = res
            tvRule.text = res2
            dialogType.dismiss()
        }
        return dialogType
    }

    fun addInvolve(data: Intent) {
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
        commit.involve = Gson().toJson(involves)
        tvStudent.text = str
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

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_COURSE) {
                if (data != null) {
                    addInvolve(data)
                }
            }
        }
    }


    override fun initDataObserver() {
        mViewModel.mAddMoralScoreData.observe(this, Observer {
            it?.let {
                toast(R.string.deal_done)
                finish()
            }
        })

        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                netResponseFormat<ArrayList<ClassesResponse>>(it)?.let {
                    mDataClasses?.clear()
                    var classes = ArrayList<String>()
                    it.forEach {
                        it.list.forEach {

                            mDataClasses?.add(it)
                        }

                    }
                    initUI(mDataClasses)
                }
            }
        })
    }
}