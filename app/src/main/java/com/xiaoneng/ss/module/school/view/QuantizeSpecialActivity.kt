package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.custom.widgets.ViewJump
import com.xiaoneng.ss.custom.widgets.ViewTextAera
import com.xiaoneng.ss.model.ClassBean
import com.xiaoneng.ss.module.school.adapter.PropertyTypeAdapter
import com.xiaoneng.ss.module.school.interfaces.IChooseStudent
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_quantize_type.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:报修报送
 * @date :2020/10/23 3:17 PM
 */
class QuantizeSpecialActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: PropertyTypeAdapter
    var mBean: QuantizeTypeBean? = null
    var mData: ArrayList<QuantizeTemplateBean> = ArrayList()
    var mDataClasses: ArrayList<ClassBean>? = ArrayList()
    var mDataCommit= QuantizeBody()
    lateinit var mListener: IChooseStudent

    override fun getLayoutId(): Int {
        return R.layout.activity_quantize_type
    }

    override fun initView() {
        super.initView()
        mBean = intent.getParcelableExtra(Constant.DATA)
        tvConfirmQuantize.setOnClickListener {
            if (mDataCommit.checktime.isNullOrEmpty()){
                toast(R.string.lack_info)
                return@setOnClickListener
            }
            mDataCommit.templatedata = Gson().toJson(mData)
            mDataCommit.typeid = mBean?.id
            mViewModel.addMoralScoreSpecial(mDataCommit)
        }
        initAdapter()
    }

    private fun initUI(mDataClasses: ArrayList<ClassBean>?) {
        llRoot.addView(ViewJump(this,data = QuantizeTemplateBean(name = "CascaderClass",label = "选择班级",placeholder = "请选择班级",classes = mDataClasses),commit = mDataCommit))
        llRoot.addView(ViewJump(this,data = QuantizeTemplateBean(name = "ChoseStudents",label = "选择学生",placeholder = "请选择学生"),commit = mDataCommit))
        llRoot.addView(ViewJump(this,data = QuantizeTemplateBean(name = "DatePickerMultiple",label = "选择时间段",placeholder = "请选择时间段"),commit = mDataCommit))
        var arr1 = ArrayList<String>()
        arr1.add("病假")
        arr1.add("事假")
        arr1.add("外出考试")
        arr1.add("学校活动")
        arr1.add("其他")

        var arr2 = ArrayList<String>()
        arr2.add("全选")
        arr2.add("入校")
        arr2.add("离校")
        arr2.add("出操")
        llRoot.addView(ViewJump(this,data = QuantizeTemplateBean(name = "Checkbox",label = "情况类型",placeholder = "请选择情况类型",selections = arr1),commit = mDataCommit))
        llRoot.addView(ViewJump(this,data = QuantizeTemplateBean(name = "Checkbox",label = "影响项目",placeholder = "请选择影响项目",selections = arr2),commit = mDataCommit))
        llRoot.addView(ViewTextAera(this,data = QuantizeTemplateBean(name = "Textarea",label = "情况说明",placeholder = "最多20个字"),commit = mDataCommit))
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

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_COURSE) {
                if (data != null) {
                    mListener.addInvolve(data)
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