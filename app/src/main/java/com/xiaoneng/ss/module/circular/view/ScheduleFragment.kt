package com.xiaoneng.ss.module.circular.view

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.utils.Lunar
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.module.circular.adapter.DaysOfWeekAdapter
import com.xiaoneng.ss.module.circular.adapter.EventAdapter
import com.xiaoneng.ss.module.circular.model.DayBean
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
    lateinit var mAdapter: DaysOfWeekAdapter
    lateinit var mAdapterEvent: EventAdapter
    var isDayOfWeek = true
    var mData = ArrayList<DayBean>()
    var mDataMonth = ArrayList<DayBean>()
    var mDataEvent = ArrayList<DayBean>()
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
        tvWeekSchedule.text = Lunar.getWhichWeek()
        ivAddEvent.setOnClickListener {
            addEvent()
        }
        initAdapterDayOfWeek()
        initAdapterDayOfMonth()
        initAdapterEvent()
    }

    private fun addEvent() {

    }

    override fun initData() {
        super.initData()
        mDataEvent.add(DayBean(""))
        mDataEvent.add(DayBean(""))
        mAdapterEvent.notifyDataSetChanged()
    }

    private fun initAdapterDayOfWeek() {
        view?.post {

            mData.clear()
            mData = Lunar.getCurrentDaysOfWeek()
            mAdapter = DaysOfWeekAdapter(R.layout.item_days_week, mData)
            var space = spaceView.width
            rvWeek.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                addItemDecoration(
                    RecycleViewDivider(
                        space.toInt(),
                        context.resources.getColor(R.color.transparent)
                    )
                )
                adapter = mAdapter
            }
            mAdapter.setOnItemClickListener { adapter, view, position ->
                for (i in 0 until mData.size) {
                    mData[i].isCheck = i == position
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun initAdapterDayOfMonth() {
        view?.post {
            mDataMonth.clear()
            mDataMonth = Lunar.getCurrentDaysOfMonth()
            mAdapter = DaysOfWeekAdapter(R.layout.item_days_week, mDataMonth)
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
                adapter = mAdapter
            }
            mAdapter.setOnItemClickListener { adapter, view, position ->
                if (mDataMonth[position].inMonth) {
                    for (i in 0 until mDataMonth.size) {
                        mDataMonth[i].isCheck = i == position
                    }
                    adapter.notifyDataSetChanged()
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

        }
    }

    private fun switch() {
        isDayOfWeek = if (isDayOfWeek) {
            rvWeek.visibility = View.GONE
            rvMonth.visibility = View.VISIBLE
            false
        } else {
            rvWeek.visibility = View.VISIBLE
            rvMonth.visibility = View.GONE
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