package com.xiaoneng.ss.module.school.view

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.model.StudentBean
import com.xiaoneng.ss.model.StudentResp
import com.xiaoneng.ss.module.school.adapter.InvolvePersonAdapter
import com.xiaoneng.ss.module.school.model.DepartmentPersonBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_involve_person.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class InvolvePersonActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private lateinit var mAdapterInvolve: InvolvePersonAdapter
    var mDataInvolve = ArrayList<DepartmentPersonBean>()
    var mDataReceive: ArrayList<StudentBean>? = null
    lateinit var chosenDay: String
    var id = ""
    var type = ""


    override fun getLayoutId(): Int = R.layout.activity_involve_person


    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.ID)!!
        type = intent.getStringExtra(Constant.TYPE)!!
        mDataReceive = intent.getParcelableArrayListExtra(Constant.DATA)
        initAdapterInvolve()
        tvConfirm.setOnClickListener {
            var data = ArrayList<StudentBean>()
            mDataInvolve.forEach {
                it.data.forEach {
                    if (it.choice == "1") {
                        data.add(it)
                    }
                }
            }
            setResult(Activity.RESULT_OK, intent.putExtra(Constant.DATA, data))
            finish()
        }

    }


    override fun initData() {
        super.initData()
        rvInvolvePerson.showLoadingView()
        if (type == "1") {
            mViewModel.listByDepartment(id.split("_").last())
        } else if (type == "2") {
            mViewModel.getStudentsByClass(classId = id.split("_").last())
        }

    }


    private fun initAdapterInvolve() {
        mAdapterInvolve = InvolvePersonAdapter(R.layout.item_involve, mDataInvolve)
        rvInvolvePerson.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterInvolve)
        }
        mAdapterInvolve.setOnItemClickListener { _, view, position ->

        }
    }

    private fun setChoice(it: StudentBean) {
        mDataReceive?.forEach { receive ->
            if (receive.uid == it.uid) {
                it.choice = "1"
                return
            }
        }
    }


    override fun initDataObserver() {


        mViewModel.mStudentData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<StudentResp>(it)?.let {
                    mDataInvolve.clear()
                    it.data.forEach {
                        it.parentId = id
                        it.topdepartid = "grade0"
                        if (id.contains("_")) {
                            it.secdepartid = id.split("_")[1]
                        }
                        setChoice(it)
                    }
                    var bean: DepartmentPersonBean = DepartmentPersonBean()
                    bean.departmentsname = "班级学生名单"
                    bean.data = it.data

                    mDataInvolve.add(bean)
                    rvInvolvePerson.notifyDataSetChanged()
                }
            }
        })

        mViewModel.mDepartmentPersonData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<ArrayList<DepartmentPersonBean>>(it)?.let {
                    mDataInvolve.clear()
                    it.forEach {bean->
                        bean.data.forEach {
                            it.parentId = id
                            if (id.contains("_")) {
                                it.topdepartid = id.split("_")[1]
                            }
                            it.secdepartid = bean.id
                            setChoice(it)
                        }
                    }
                    mDataInvolve.addAll(it)
                    rvInvolvePerson.notifyDataSetChanged()
                }
            }
        })

    }



}