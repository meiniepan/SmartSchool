package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.model.ClassBean
import com.xiaoneng.ss.model.TestBean
import com.xiaoneng.ss.model.TestCourseResp
import com.xiaoneng.ss.module.school.adapter.AchievementStuAdapter
import com.xiaoneng.ss.module.school.adapter.AchievementTeacherAdapter
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.model.AchievementBean
import com.xiaoneng.ss.module.school.model.CourseBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_performance.*

/**
 * @author Burning
 * @description:
 * @date :2020/8/20 11:32 AM
 */
class AchievementActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapterStudent: AchievementStuAdapter
    lateinit var mAdapterTea: AchievementTeacherAdapter
    var mData: ArrayList<AchievementBean> = ArrayList()
    var mDataClass: ArrayList<ClassBean> = ArrayList()
    var mDataCourse: ArrayList<CourseBean> = ArrayList()
    var mDataTest: ArrayList<TestBean> = ArrayList()
    var titlesClass = ArrayList<String>()
    var titlesCourse = ArrayList<String>()
    var titlesTest = ArrayList<String>()
    var currentClass: String = ""
    var currentCourse: String = ""
    var currentTest: String = ""
    var classid = ""
    var courseid = ""
    private lateinit var dialogClass: Dialog
    private lateinit var dialogCourse: Dialog
    private lateinit var dialogTest: Dialog

    override fun getLayoutId(): Int {
        return R.layout.activity_performance
    }

    override fun initView() {
        super.initView()
        tvActionClass.setOnClickListener {
            dialogClass.show()
        }
        tvActionCourse.setOnClickListener {
            dialogCourse.show()
        }
        tvActionTest.setOnClickListener {
            dialogTest.show()
        }
        initDialog()
    }

    override fun initData() {
        super.initData()
        showLoading()
        mViewModel.getTestCourse()
        when (UserInfo.getUserBean().usertype) {
            "1" -> {
                tvActionClass.visibility = View.GONE
                llTitleTea.visibility = View.GONE
                llTitleStu.visibility = View.VISIBLE
                initAdapterStu()
            }
            "2" -> {
                initAdapterTeacher()
                llTitleTea.visibility = View.VISIBLE
                llTitleStu.visibility = View.GONE
            }
            "99" -> {
                initAdapterTeacher()
                llTitleStu.visibility = View.VISIBLE
                llTitleStu.visibility = View.GONE
            }
            else -> {
            }
        }
    }

    private fun initAdapterStu() {
        mAdapterStudent = AchievementStuAdapter(R.layout.item_performance_stu, mData)
        rvPerformance?.apply {
            layoutManager = LinearLayoutManager(this@AchievementActivity)
            setAdapter(mAdapterStudent)
        }

        mAdapterStudent.setOnItemClickListener { adapter, view, position ->

        }
    }

    private fun initAdapterTeacher() {
        mAdapterTea = AchievementTeacherAdapter(R.layout.item_performance_tea, mData)
        rvPerformance?.apply {
            layoutManager = LinearLayoutManager(this@AchievementActivity)
            setAdapter(mAdapterTea)
        }

        mAdapterTea.setOnItemClickListener { adapter, view, position ->

        }
    }


    private fun initDialog() {
        initDialogClass()
        initDialogCourse()
        initDialogTest()
    }

    fun getPerformanceRequest() {
        mViewModel.getAchievement(currentTest, classid = classid, crid = courseid)
    }

    private fun initDialogClass() {

        // 底部弹出对话框
        dialogClass =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        dialogClass.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        dialogClass.window!!.setGravity(Gravity.BOTTOM)
        dialogClass.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titlesClass)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(this@AchievementActivity)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            tvActionClass.text = mDataClass[position].classname
            classid = mDataClass[position].classid
            rvPerformance.showLoadingView()
            getPerformanceRequest()
            dialogClass.dismiss()
        }

    }

    private fun initDialogCourse() {

        // 底部弹出对话框
        dialogCourse =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        dialogCourse.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        dialogCourse.window!!.setGravity(Gravity.BOTTOM)
        dialogCourse.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titlesCourse)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(this@AchievementActivity)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            tvActionCourse.text = mDataCourse[position].coursename
            courseid = mDataCourse[position].crsid!!
            rvPerformance.showLoadingView()
            getPerformanceRequest()
            dialogCourse.dismiss()
        }

    }

    private fun initDialogTest() {

        // 底部弹出对话框
        dialogTest =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        dialogTest.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        dialogTest.window!!.setGravity(Gravity.BOTTOM)
        dialogTest.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titlesTest)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(this@AchievementActivity)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            currentTest = mDataTest[position].testname
            tvActionTest.text = mDataTest[position].testname
            rvPerformance.showLoadingView()
            getPerformanceRequest()
            dialogTest.dismiss()
        }

    }


    override fun initDataObserver() {
        mViewModel.mAchievementData.observe(this, Observer { response ->
            response?.let {
                mData.clear()
                mData.addAll(it.list)
                rvPerformance.notifyDataSetChanged()
            }
        })

        mViewModel.mTestCourseData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<TestCourseResp>(it)?.let {
                    rvPerformance.showLoadingView()
                    titlesClass.clear()
                    titlesCourse.clear()
                    titlesTest.clear()
                    mDataClass.clear()
                    mDataCourse.clear()
                    mDataTest.clear()
                    mDataClass.addAll(it.classs)
                    mDataCourse.addAll(it.course)
                    mDataTest.addAll(it.testcourse)
                    if (mDataTest.size > 0) {
                        currentTest = mDataTest[0].testname
                    } else {
                        mRootView.post {
                            showEmpty()
                        }
                        mAlert("查询不到考试信息") {
                            finish()
                        }
                        return@Observer
                    }
                    if (mDataClass.size > 0) {
                        currentClass = mDataClass[0].classname
                        classid = mDataClass[0].classid
                    }
                    if (mDataCourse.size > 0) {
                        currentCourse = mDataCourse[0].coursename!!
                        courseid = mDataCourse[0].crsid!!
                    }
                    getPerformanceRequest()
                    tvActionClass.text = currentClass
                    tvActionCourse.text = currentCourse
                    tvActionTest.text = currentTest

                    mDataClass.forEach {
                        titlesClass.add(it.classname)
                    }

                    mDataCourse.forEach {
                        titlesCourse.add(it.coursename ?: "")
                    }

                    mDataTest.forEach {
                        titlesTest.add(it.testname)
                    }
                }
            }
        })
    }

}