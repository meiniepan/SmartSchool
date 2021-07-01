package com.xiaoneng.ss.module.circular.view

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.circular.adapter.DaysOfMonthAdapter
import com.xiaoneng.ss.module.circular.adapter.DaysOfWeekAdapter
import com.xiaoneng.ss.module.circular.adapter.EventAdapter
import com.xiaoneng.ss.module.circular.adapter.WeekTitleAdapter
import com.xiaoneng.ss.module.circular.model.DayBean
import com.xiaoneng.ss.module.circular.model.ScheduleBean
import com.xiaoneng.ss.module.circular.model.ScheduleDayResponse
import com.xiaoneng.ss.module.circular.model.ScheduleResponse
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.fragment_schedule.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ScheduleFragment : BaseLifeCycleFragment<CircularViewModel>() {
    private var weekStr: String? = ""
    private var lastId: String? = null
    private var chosenDay: Long? = System.currentTimeMillis()
    lateinit var mAdapterWeek: DaysOfWeekAdapter
    lateinit var mAdapterMonth: DaysOfMonthAdapter
    lateinit var mAdapterEvent: EventAdapter
    var isDayOfWeek = true
    var mDataWeekTitle = ArrayList<String>()
    var mDataWeek = ArrayList<DayBean>()
    var mDataMonth = ArrayList<DayBean>()
    var mDataEvent = ArrayList<ScheduleBean>()
    var dateOffset = 0

    override fun getLayoutId(): Int = R.layout.fragment_schedule

    companion object {
        fun getInstance(): Fragment {
            return ScheduleFragment()
        }

    }

    override fun initView() {
        super.initView()
        switch()
        ivSwitchSchedule.setOnClickListener {
            switch()
        }
//        tvWeekSchedule.text = Lunar.getWhichWeek(chosenDay)
        ivAddEvent.setOnClickListener {
            addEvent()
        }
        ivDateBack.setOnClickListener {
            dateOffset--
            setMonthDays()
        }
        ivDateNext.setOnClickListener {
            dateOffset++
            setMonthDays()
        }
        initAdapterWeekTitle()
        initAdapterDayOfWeek()
        initAdapterDayOfMonth()
        initAdapterEvent()
        mDataWeek.clear()
        mDataWeek.addAll(Lunar.getCurrentDaysOfWeek(chosenDay))
    }

    private fun addEvent() {
        mStartActivity<AddScheduleActivity>(requireContext()) {
            putExtra(Constant.TIME, chosenDay)
        }
    }

    private fun setMonthDays() {
        tvWeekSchedule.text = DateUtil.getWhichMonth(offset = dateOffset)
        queryMonthData()
    }

    private fun queryMonthData() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, dateOffset)
        var c = cal.timeInMillis
        showLoading()
        mViewModel.queryScheduleMonth(
            months =
            DateUtil.formatDateCustomMonth(c)
        )
    }

    override fun onResume() {
        super.onResume()
        getData()
        queryMonthData()
    }

    override fun getData() {
        rvEventSchedule.showLoadingView()
        mViewModel.querySchedule(DateUtil.formatDateCustomDay(chosenDay!!))
    }

    private fun initAdapterWeekTitle() {
        mDataWeekTitle.clear()
        mDataWeekTitle.add("日")
        mDataWeekTitle.add("一")
        mDataWeekTitle.add("二")
        mDataWeekTitle.add("三")
        mDataWeekTitle.add("四")
        mDataWeekTitle.add("五")
        mDataWeekTitle.add("六")
        rvWeekTitle.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = WeekTitleAdapter(R.layout.item_week_title, mDataWeekTitle)
        }

    }

    private fun initAdapterDayOfWeek() {


        mAdapterWeek = DaysOfWeekAdapter(R.layout.item_days_week, mDataWeek)
        rvWeek.apply {
            layoutManager = GridLayoutManager(context, 7)
            adapter = mAdapterWeek
        }
        mAdapterWeek.setOnItemClickListener { adapter, view, position ->
            chosenDay = mDataWeek[position].day
            for (i in 0 until mDataWeek.size) {
                mDataWeek[i].isCheck = i == position
            }
            adapter.notifyDataSetChanged()
            getData()
        }
    }

    private fun initAdapterDayOfMonth() {

        mAdapterMonth = DaysOfMonthAdapter(R.layout.item_days_week, mDataMonth)

        rvMonth.apply {
            layoutManager = GridLayoutManager(context, 7)

            adapter = mAdapterMonth
        }
        mAdapterMonth.setOnItemClickListener { adapter, view, position ->
            if (mDataMonth[position].inMonth) {
                chosenDay = mDataMonth[position].day
//                mDataWeek.clear()
//                mDataWeek.addAll(Lunar.getCurrentDaysOfWeek(chosenDay))
//                mAdapterWeek.notifyDataSetChanged()
//                switch()
                mDataMonth.forEach {
                    it.isCheck = false
                }
                mDataMonth[position].isCheck = true
                mAdapterMonth.notifyDataSetChanged()
                getData()
            }
        }
    }


    private fun initAdapterEvent() {
//        rvEventSchedule.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
//            override fun onLoadMore(refreshLayout: RefreshLayout) {
//                getData()
//            }
//
//            override fun onRefresh(refreshLayout: RefreshLayout) {
//                doRefresh()
//            }
//        })
        mAdapterEvent = EventAdapter(R.layout.item_event_schedule, mDataEvent)

        rvEventSchedule.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterEvent)
        }
        mAdapterEvent.setOnItemClickListener { adapter, view, position ->
            if (mDataEvent[position].itype=="2" && (UserInfo.getUserBean().uid != mDataEvent[position].cuser_id)) {
                return@setOnItemClickListener
            }
            mStartActivity<ScheduleDetailActivity>(context) {
                putExtra(Constant.DATA, mDataEvent[position])
            }
        }
    }

    private fun switch() {
        isDayOfWeek = if (isDayOfWeek) {
            setDateNextVisibility(isDayOfWeek)
            queryMonthData()
            rvWeek.visibility = View.GONE
            rvMonth.visibility = View.VISIBLE
            tvWeekSchedule.text = DateUtil.getWhichMonth(offset = dateOffset)
            false
        } else {
            setDateNextVisibility(isDayOfWeek)
            rvWeek.visibility = View.VISIBLE
            rvMonth.visibility = View.GONE
            tvWeekSchedule.text = weekStr
            true
        }

    }

    private fun setDateNextVisibility(visibility: Boolean) {
        if (!visibility) {
            ivDateBack.visibility = View.GONE
            ivDateNext.visibility = View.GONE
        } else {
            ivDateBack.visibility = View.VISIBLE
            ivDateNext.visibility = View.VISIBLE
        }
    }

    override fun initDataObserver() {
        mViewModel.mScheduleData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<ScheduleResponse>(it)?.let {
                    weekStr = it.semesters
                    tvSem.text = it.semesters
                    it.data?.let {
                        mDataEvent.clear()
                        mDataEvent.addAll(it)
                        rvEventSchedule.notifyDataSetChanged()
                    }
                }
            }
        })
        mViewModel.mScheduleMonthData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<ScheduleResponse>(it)?.let {
                    it.days?.let {
                        mDataMonth.clear()
                        mDataMonth.addAll(
                            Lunar.getDaysOfMonth(
                                it,
                                chosenDay,
                                dateOffset
                            )
                        )
                        mAdapterMonth.notifyDataSetChanged()
                    }
                }
            }
        })
    }

}