package com.xiaoneng.ss.module.school.view

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.model.StudentBean
import com.xiaoneng.ss.module.school.adapter.InvolvePersonAdapter
import com.xiaoneng.ss.module.school.model.DepartmentPersonBean
import com.xiaoneng.ss.module.school.model.StudentsResponse
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
    lateinit var chosenDay: String
    var id = ""
    var type = ""


    override fun getLayoutId(): Int = R.layout.activity_involve_person


    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.TITLE)
        type = intent.getStringExtra(Constant.TYPE)
        initAdapterInvolve()
        tvConfirm.setOnClickListener {
            var data = ArrayList<StudentBean>()
            mDataInvolve.forEach {
              data.forEach {
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
            mViewModel.listByDepartment(id)
        } else if (type == "2") {
            mViewModel.getStudentsByClass(id)
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


    override fun initDataObserver() {


        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<StudentsResponse>(it)?.let {
                    mDataInvolve.clear()
                    it.data.forEach {
                        it.parentId = id
                    }
                    var bean:DepartmentPersonBean = DepartmentPersonBean()
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
                    it.forEach {
                        it.data.forEach {
                            it.parentId = id
                        }
                    }
                    mDataInvolve.addAll(it)
                    rvInvolvePerson.notifyDataSetChanged()
                }
            }
        })

    }

}