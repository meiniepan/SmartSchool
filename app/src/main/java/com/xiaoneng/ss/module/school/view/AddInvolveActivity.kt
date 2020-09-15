package com.xiaoneng.ss.module.school.view

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.eventBus.ManageInvolveEvent
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.model.StudentBean
import com.xiaoneng.ss.module.school.adapter.DepartmentAdapter
import com.xiaoneng.ss.module.school.adapter.InvolvePersonAdapter
import com.xiaoneng.ss.module.school.adapter.QueryStudentAdapter
import com.xiaoneng.ss.module.school.model.DepartmentBean
import com.xiaoneng.ss.module.school.model.LeaveBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_involve.*
import kotlinx.android.synthetic.main.activity_add_student.etSearch
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AddInvolveActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private lateinit var mAdapterQuery: QueryStudentAdapter
    private lateinit var mAdapterDepartment: DepartmentAdapter
    private lateinit var mAdapterInvolve: InvolvePersonAdapter
    var mDataQuery = ArrayList<StudentBean>()
    var mDataDepartment = ArrayList<DepartmentBean>()
    var mDataClasses = ArrayList<DepartmentBean>()
    var mDataInvolve = ArrayList<StudentBean>()
    var isManage = false
    lateinit var chosenDay: String


    override fun getLayoutId(): Int = R.layout.activity_add_involve


    override fun initView() {
        super.initView()
        initAdapterQuery()
        initTab()
        initAdapterDepart()
        initAdapterInvolve()
        etSearch.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if (etSearch.text.toString().isNotEmpty()) {
                        showLoading()
                        mViewModel.queryStudent(etSearch.text.toString())
                    }
                }

            }
            return@setOnEditorActionListener false
        }

        tvManage.apply {
            setOnClickListener {
                isManage = !isManage
                ManageInvolveEvent(isManage).post()
                if (isManage) {
                    this.text = "完成"
                } else {
                    this.text = "管理"
                }
            }
        }

    }

    private fun initTab() {
        tvInvolveTab1.setOnClickListener {
            checkFirsTab()
        }
        tvInvolveTab2.setOnClickListener {
            checkSecondTab()
        }

    }

    private fun checkFirsTab() {
        tvInvolveTab1.setChecked(true)
        tvInvolveTab2.setChecked(false)
        mAdapterDepartment.setNewData(mDataDepartment)
    }

    private fun checkSecondTab() {
        tvInvolveTab2.setChecked(true)
        tvInvolveTab1.setChecked(false)

        mAdapterDepartment.setNewData(mDataClasses)
    }

    private fun initAdapterQuery() {
        mAdapterQuery = QueryStudentAdapter(R.layout.item_query_student, mDataQuery)
        rvSearchInvolve.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterQuery)
        }
        mAdapterQuery.setOnItemClickListener { _, view, position ->
            mShowDialog(position)
        }
    }

    private fun initAdapterDepart() {
        mAdapterDepartment = DepartmentAdapter(R.layout.item_department, mDataDepartment)
        rvDepartment.apply {
            layoutManager = GridLayoutManager(context, 3)
            setAdapter(mAdapterDepartment)
        }
        mAdapterQuery.setOnItemClickListener { _, view, position ->

        }
    }

    private fun initAdapterInvolve() {
        mAdapterInvolve = InvolvePersonAdapter(R.layout.item_involve, mDataInvolve)
        rvInvolve.apply {
            layoutManager = GridLayoutManager(context, 5)
            setAdapter(mAdapterInvolve)
        }
        mAdapterInvolve.setOnItemClickListener { _, view, position ->

        }
    }


    override fun initData() {
        super.initData()
//        mData.add(NoticeBean(""))
//        mData.add(NoticeBean(""))
//        mData.add(NoticeBean(""))
//        mViewModel.getTimetable()
    }


    private fun mShowDialog(position: Int) {
        // 弹出对话框
        chosenDay = DateUtil.formatDate()
        var msg =
            mDataQuery[position].cno + mDataQuery[position].realname + "\n" + mDataQuery[position].levelname + mDataQuery[position].classname
        mAlert(msg, "请确认学生身份") {
            mViewModel.addAttendance(
                LeaveBean(
                    UserInfo.getUserBean().token, type = "1", status = "0",
                    uid = mDataQuery[position].uid, atttime = chosenDay, crsid = "", remark = "lai"
                )
            )
        }
    }

    override fun initDataObserver() {
        mViewModel.mStudentData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                rvSearchInvolve.visibility = View.VISIBLE
                mDataQuery.clear()
                mDataQuery.addAll(it.data)
                rvSearchInvolve.notifyDataSetChanged()
            }
        })

        mViewModel.mAddAttendanceData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                finish()
            }
        })

    }

}