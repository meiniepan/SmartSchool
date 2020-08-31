package com.xiaoneng.ss.module.circular.view

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.circular.adapter.DaysOfMonthAdapter
import com.xiaoneng.ss.module.circular.adapter.DaysOfWeekAdapter
import com.xiaoneng.ss.module.circular.adapter.EventAdapter
import com.xiaoneng.ss.module.circular.model.DayBean
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.fragment_schedule.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class ScheduleFragment : BaseLifeCycleFragment<CircularViewModel>() {
    private var chosenDay: Long? = 0L
    lateinit var mAdapterWeek: DaysOfWeekAdapter
    lateinit var mAdapterMonth: DaysOfMonthAdapter
    lateinit var mAdapterEvent: EventAdapter
    var isDayOfWeek = true
    var mDataWeek = ArrayList<DayBean>()
    var mDataMonth = ArrayList<DayBean>()
    var mDataEvent = ArrayList<NoticeBean>()
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
        initAdapterDayOfWeek()
        initAdapterDayOfMonth()
        initAdapterEvent()
    }

    private fun addEvent() {
        mStartActivity<AddScheduleActivity>(requireContext())
    }

    override fun initData() {
        super.initData()
        mDataEvent.add(NoticeBean(""))
        mDataEvent.add(NoticeBean(""))
        mAdapterEvent.notifyDataSetChanged()
    }

    private fun initAdapterDayOfWeek() {
        view?.post {

            mDataWeek.clear()
            mDataWeek = Lunar.getCurrentDaysOfWeek(chosenDay)
            mAdapterWeek = DaysOfWeekAdapter(R.layout.item_days_week, mDataWeek)
            var space = spaceView.width
            rvWeek.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(
                    RecycleViewDivider(
                        space.toInt(),
                        context.resources.getColor(R.color.transparent)
                    )
                )
                adapter = mAdapterWeek
            }
            mAdapterWeek.setOnItemClickListener { adapter, view, position ->
                for (i in 0 until mDataWeek.size) {
                    mDataWeek[i].isCheck = i == position
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun initAdapterDayOfMonth() {
        view?.post {
            mDataMonth.clear()
            mDataMonth = Lunar.getCurrentDaysOfMonth()
            mAdapterMonth = DaysOfMonthAdapter(R.layout.item_days_week, mDataMonth)
            var spaceH = spaceView.width
            var params = rvMonth.layoutParams as ViewGroup.MarginLayoutParams
            params.marginEnd = -spaceH
            rvMonth.layoutParams = params
            rvMonth.apply {
                layoutManager = GridLayoutManager(context, 7)
                addItemDecoration(
                    RecycleViewDivider(
                        spaceH.toInt(),
                        context.resources.getColor(R.color.transparent),
                        0
                    )
                )
                adapter = mAdapterMonth
            }
            mAdapterMonth.setOnItemClickListener { adapter, view, position ->
                if (mDataMonth[position].inMonth) {
//                    for (i in 0 until mDataMonth.size) {
//                        mDataMonth[i].isCheck = i == position
//                    }
//                    adapter.notifyDataSetChanged()
                    chosenDay = mDataMonth[position].day
                    mDataWeek.clear()
                    mDataWeek = Lunar.getCurrentDaysOfWeek(chosenDay)
                    mAdapterWeek.notifyDataSetChanged()
                    tvWeekSchedule.text = Lunar.getWhichWeek(chosenDay)
                    switch()
                }
            }
        }
    }

    private fun initAdapterEvent() {
        mAdapterEvent = EventAdapter(R.layout.item_event_schedule, mDataEvent)

        rvEventSchedule.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(requireContext(), 10f).toInt(),
                    context.resources.getColor(R.color.transparent)
                )
            )
            adapter = mAdapterEvent
        }
        mAdapterEvent.setOnItemClickListener { adapter, view, position ->
            mStartActivity<ScheduleDetailActivity>(context) {
                putExtra(Constant.TITLE, mDataEvent[position].title)
                putExtra(Constant.ID, mDataEvent[position].id)
            }
        }
    }

    private fun switch() {
        isDayOfWeek = if (isDayOfWeek) {
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
//        mViewModel.mSystemTabNameData.observe(this, Observer { response ->
//            response?.let {
//                setSystemTabData(it)
//            }
//        })
    }


}