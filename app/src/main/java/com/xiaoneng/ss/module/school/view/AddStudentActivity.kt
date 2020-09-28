package com.xiaoneng.ss.module.school.view

import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.model.StudentBean
import com.xiaoneng.ss.model.StudentResp
import com.xiaoneng.ss.module.school.adapter.QueryStudentAdapter
import com.xiaoneng.ss.module.school.model.LeaveBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_student.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AddStudentActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private lateinit var mAdapter: QueryStudentAdapter
    var mData = ArrayList<StudentBean>()
    lateinit var chosenDay :String


    override fun getLayoutId(): Int = R.layout.activity_add_student


    override fun initView() {
        super.initView()
        initAdapter()
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

    }


    private fun initAdapter() {
        mAdapter = QueryStudentAdapter(R.layout.item_query_student, mData)
        rvAddStu.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            mShowDialog(position)
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
        var msg = mData[position].cno + mData[position].realname + "\n" + mData[position].levelname +mData[position].classname
        mAlert(msg, "请确认学生身份") {
            mViewModel.addAttendance(
                LeaveBean(
                    UserInfo.getUserBean().token, type = "1", status = "0",leavetype = null,
                    uid = mData[position].uid, atttime = chosenDay, crsid = "", remark = "lai"
                )
            )
        }
    }

    override fun initDataObserver() {
        mViewModel.mStudentData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<StudentResp>(it)?.let {
                    showSuccess()
                    mData.clear()
                    mData.addAll(it.data)
                    rvAddStu.notifyDataSetChanged()
                }

            }
        })

        mViewModel.mAddAttendanceData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                mStartActivity<AttendanceActivity>(this)
            }
        })

    }

}