package com.xiaoneng.ss.module.circular.view

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
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


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ScheduleFragment : BaseLifeCycleFragment<CircularViewModel>() {
    private var chosenDay: Long? = System.currentTimeMillis()
    lateinit var mAdapterWeek: DaysOfWeekAdapter
    lateinit var mAdapterMonth: DaysOfMonthAdapter
    lateinit var mAdapterEvent: EventAdapter
    var isDayOfWeek = true
    var hasInitMonth = false
    var mDataWeekTitle = ArrayList<String>()
    var mDataWeek = ArrayList<DayBean>()
    var mDataMonth = ArrayList<DayBean>()
    var mDataEvent = ArrayList<ScheduleBean>()
    override fun getLayoutId(): Int = R.layout.fragment_schedule

    companion object {
        fun getInstance(): Fragment {
            return ScheduleFragment()
        }

    }

    override fun initView() {
        super.initView()
        ivSwitchSchedule.setOnClickListener {
            switch()
        }
        tvWeekSchedule.text = Lunar.getWhichWeek(chosenDay)
        ivAddEvent.setOnClickListener {
            addEvent()
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
            putExtra(Constant.DATA, chosenDay)
        }
    }


    override fun onResume() {
        super.onResume()
        getData()
    }

    override fun getData() {
        rvEventSchedule.showLoadingView()
        mViewModel.querySchedule(DateUtil.formatDateCustomDay(chosenDay!!))
    }

    private fun initAdapterWeekTitle() {
        mDataWeekTitle.clear()
        mDataWeekTitle.add("周日")
        mDataWeekTitle.add("周一")
        mDataWeekTitle.add("周二")
        mDataWeekTitle.add("周三")
        mDataWeekTitle.add("周四")
        mDataWeekTitle.add("周五")
        mDataWeekTitle.add("周六")
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
                    mDataWeek.clear()
                    mDataWeek.addAll(Lunar.getCurrentDaysOfWeek(chosenDay))
                    mAdapterWeek.notifyDataSetChanged()
                    switch()
                    getData()
                }
        }
    }

    private fun initAdapterEvent() {
        mAdapterEvent = EventAdapter(R.layout.item_event_schedule, mDataEvent)

        rvEventSchedule.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(requireContext(), 10f).toInt(),
                    context.resources.getColor(R.color.transparent)
                )
            )
            setAdapter(mAdapterEvent)
        }
        mAdapterEvent.setOnItemClickListener { adapter, view, position ->
            mStartActivity<ScheduleDetailActivity>(context) {
                putExtra(Constant.DATA, mDataEvent[position])
            }
        }
    }

    private fun switch() {
        isDayOfWeek = if (isDayOfWeek) {
            showLoading()
            mViewModel.queryScheduleMonth(
                DateUtil.formatDateCustomDay(chosenDay!!),
                DateUtil.formatDateCustomMonth(chosenDay!!)
            )
            rvWeek.visibility = View.GONE
            rvMonth.visibility = View.VISIBLE
            tvWeekSchedule.text = DateUtil.getWhichMonth()
            false
        } else {
            rvWeek.visibility = View.VISIBLE
            rvMonth.visibility = View.GONE
            tvWeekSchedule.text = Lunar.getWhichWeek(chosenDay)
            true
        }

    }

    override fun initDataObserver() {
        mViewModel.mScheduleData.observe(this, Observer { response ->
            response?.let {
                val gson: Gson = GsonBuilder().enableComplexMapKeySerialization().create()
                val jsonString = gson.toJson(it)
                gson.fromJson(jsonString, ScheduleResponse::class.java)?.let {

                    mDataEvent.clear()
                    mDataEvent.addAll(it.data)
                    if (mDataEvent.size > 0) {
                        rvEventSchedule.notifyDataSetChanged()
                    } else {
                        rvEventSchedule.showEmptyView()
                    }
//                    showEmpty()

                }
            }
        })
        mViewModel.mScheduleMonthData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                val gson: Gson = GsonBuilder().enableComplexMapKeySerialization().create()
                val jsonString = gson.toJson(it)
                gson.fromJson(jsonString, ScheduleDayResponse::class.java)?.let {
                    mDataMonth.clear()
                    mDataMonth.addAll(Lunar.getCurrentDaysOfMonth(it.data))
                    mAdapterMonth.notifyDataSetChanged()

                }
            }
        })
    }


}