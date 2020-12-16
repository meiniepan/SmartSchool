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
                    name = "全校通告",
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
                    name = "工资条",
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
                    name = "日程安排",
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
                    name = "任务协作",
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
                    name = "报修报送",
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
                    name = "场地预约",
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
        if (AppInfo.checkRule1("admin/spacebook/default")) {
            bean4.items.add(
                SchoolItemBean(
                    name = "学生考勤",
                    remark = "早间考勤 课堂考勤",
                    icon = R.drawable.ic_kaoqin,
                    click = View.OnClickListener {
                        mStartActivity<AttendanceActivity>(context)
                    }
                )
            )
        }
        if (AppInfo.checkRule1("admin/spacebook/default")) {
            bean4.items.add(
                SchoolItemBean(
                    name = "成绩汇总",
                    remark = "各科成绩 排名浮动",
                    icon = R.drawable.ic_chengji,
                    click = View.OnClickListener {
                        mStartActivity<AchievementActivity>(context)
                    }
                )
            )
        }
        if (AppInfo.checkRule1("admin/spacebook/default")) {
            bean4.items.add(SchoolItemBean(
                name = "我的课表",
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