package com.xiaoneng.ss.module.school.view

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.SchoolAdapter
import com.xiaoneng.ss.module.school.model.SalaryResponse
import com.xiaoneng.ss.module.school.model.SchoolBean
import com.xiaoneng.ss.module.school.model.SchoolItemBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.fragment_school.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class SchoolFragment : BaseLifeCycleFragment<SchoolViewModel>() {
    lateinit var mAdapter: SchoolAdapter
    var mData = ArrayList<SchoolBean>()
    var pp = ""

    override fun getLayoutId(): Int = R.layout.fragment_school

    companion object {
        fun getInstance(): Fragment {
            return SchoolFragment()
        }

    }

    override fun initView() {
        super.initView()
        checkRules()
        initAdapter()
    }

    private fun checkRules() {//判断权限
        var bean1 = SchoolBean(name = "信息递送")
        if (AppInfo.checkRule1("admin/notices/default")) {
            bean1.items.add(
                SchoolItemBean(
                    name = getString(R.string.noticeTitle),
                    remark = "快速准确 一键提醒",
                    icon = R.drawable.ic_tonggao,
                    click = View.OnClickListener {
                        mStartActivity<NoticeActivity>(context)
                    }
                )
            )
        }
        if (AppInfo.checkRule1("admin/wages/default")) {
            bean1.items.add(
                SchoolItemBean(
                    name = getString(R.string.salaryTitle),
                    remark = "保护隐私 查看明细",
                    icon = R.drawable.ic_gongzitiao,
                    click = View.OnClickListener {
                        showLoading()
                        mViewModel.getSalaryList()
                    }
                )
            )
        }
        if (AppInfo.checkRule1("admin/schedules/default")) {
            bean1.items.add(
                SchoolItemBean(
                    name = getString(R.string.scheduleTitle),
                    remark = "工作安排 井井有条",
                    icon = R.drawable.ic_richeng,
                    click = View.OnClickListener {
                        mStartActivity<ScheduleActivity>(context)
                    }
                )
            )
        }
        if (bean1.items.size > 0) {
            mData.add(bean1)
        }

        var bean2 = SchoolBean(name = "效率提升")
        if (AppInfo.checkRule1("admin/tasks/default")) {
            bean2.items.add(
                SchoolItemBean(
                    name = getString(R.string.taskTitle),
                    remark = "任务管理 办公协作",
                    icon = R.drawable.ic_renwu,
                    click = View.OnClickListener {
                        mStartActivity<TaskActivity>(context)
                    }
                )
            )
        }
        if (AppInfo.checkRule1("admin/repair/default")) {
            bean2.items.add(
                SchoolItemBean(
                    name = getString(R.string.propertyTitle),
                    remark = "一键拨打 随叫随到",
                    icon = R.drawable.ic_baoxiu,
                    click = View.OnClickListener {
                        mStartActivity<PropertyActivity>(context)
                    }
                )
            )
        }
        if (bean2.items.size > 0) {
            mData.add(bean2)
        }

        var bean3 = SchoolBean(name = "OA办公")
        if (AppInfo.checkRule1("admin/spacebook/default")) {
            bean3.items.add(
                SchoolItemBean(
                    name = getString(R.string.siteTitle),
                    remark = "场地时段 一目了然",
                    icon = R.drawable.ic_changdi,
                    click = View.OnClickListener {
                        mStartActivity<BookSiteActivity>(context)
                    }
                )
            )
        }

        if (bean3.items.size > 0) {
            mData.add(bean3)
        }

        var bean4 = SchoolBean(name = "家校协同")
        if (AppInfo.checkRule1("admin/attendances/default")) {
            bean4.items.add(
                SchoolItemBean(
                    name = getString(R.string.attendanceTitle),
                    remark = "实时更新 系统上报",
                    icon = R.drawable.ic_kaoqin,
                    click = View.OnClickListener {
                        mStartActivity<AttendanceActivity>(context)
                    }
                )
            )
        }
        if (AppInfo.checkRule1("admin/achievements/default")) {
            bean4.items.add(
                SchoolItemBean(
                    name = getString(R.string.achievementTitle),
                    remark = "各科成绩 汇总分析",
                    icon = R.drawable.ic_chengji,
                    click = View.OnClickListener {
                        mStartActivity<AchievementActivity>(context)
                    }
                )
            )
        }
        if (AppInfo.checkRule1("admin/courses/default")) {
            bean4.items.add(SchoolItemBean(
                name = getString(R.string.timetableTitle),
                remark = "班级课表 教学课表",
                icon = R.drawable.ic_kebiao,
                click = View.OnClickListener {
                    mStartActivity<TimetableActivity>(context)
                }
            ))
        }

        if (bean4.items.size > 0) {
            mData.add(bean4)
        }


    }


    private fun initAdapter() {
        mAdapter = SchoolAdapter(R.layout.item_school, mData)
        rvSchool.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    override fun initDataObserver() {
        mViewModel.mSalaryListData.observe(this, Observer {
            it?.let {
                netResponseFormat<SalaryResponse>(it)?.let {
                    mStartActivity<SalaryActivity>(requireContext()) {
                        putExtra(Constant.DATA, it.data)
                    }
                }
            }
        })
    }


}