package com.xiaoneng.ss.module.school.view

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.circular.adapter.DaysOfMonthAdapter
import com.xiaoneng.ss.module.circular.adapter.DaysOfWeekAdapter
import com.xiaoneng.ss.module.circular.adapter.WeekTitleAdapter
import com.xiaoneng.ss.module.circular.model.DayBean
import com.xiaoneng.ss.module.school.adapter.BookSiteAdapter
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.model.SiteResp
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_book_site.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Burning
 * @description:场地预约
 * @date :2020/10/23 3:17 PM
 */
class BookSiteActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var chosenDay: Long? = System.currentTimeMillis()
    lateinit var mAdapterWeek: DaysOfWeekAdapter
    var isDayOfWeek = true
    var weekStr: String? = ""
    var dateOffset = 0
    var mDataWeekTitle = ArrayList<String>()
    var mDataWeek = ArrayList<DayBean>()
    var mDataMonth = ArrayList<DayBean>()
    lateinit var mAdapterMonth: DaysOfMonthAdapter
    lateinit var mAdapter: BookSiteAdapter
    var mData: ArrayList<SiteBean> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.activity_book_site
    }

    override fun initView() {
        super.initView()
        switch()
//        tvWeekSchedule.text = Lunar.getWhichWeek(chosenDay)
        ivSwitchSchedule.setOnClickListener {
            switch()
        }
        ivDateBack.setOnClickListener {
            dateOffset--
            setMonthDays()
        }
        ivDateNext.setOnClickListener {
            dateOffset++
            setMonthDays()
        }
        ivAddEvent.setOnClickListener {
            addEvent()
        }
        initAdapterWeekTitle()
        initAdapterDayOfWeek()
        initAdapterDayOfMonth()
        mDataWeek.clear()
        mDataWeek.addAll(Lunar.getCurrentDaysOfWeek(chosenDay))
        initAdapter()
        tvBookSiteRecords.setOnClickListener {
            mStartActivity<BookSiteRecordsActivity>(this)
        }
    }

    private fun setMonthDays() {
        tvWeekSchedule.text = DateUtil.getWhichMonth(offset = dateOffset)
        queryMonthData()
    }

    override fun initData() {
        super.initData()
    }

    override fun getData() {
        super.getData()
        rvSite.showLoadingView()
        mViewModel.getBookList(DateUtil.formatDateCustomDay(chosenDay!!),end=DateUtil.formatDateCustomDay(chosenDay!!+24*3600*1000))
    }

    override fun onResume() {
        super.onResume()
        getData()
        queryMonthData()
    }

    private fun addEvent() {
        mStartActivity<AddBookSite2Activity>(this) {
            putExtra(Constant.TIME, chosenDay)
        }
    }

    private fun queryMonthData() {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, dateOffset)
        var c = cal.timeInMillis
        showLoading()
        mViewModel.getBookListMonth(month =
        DateUtil.formatDateCustomMonth(c))
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
                for (i in 0 until mDataMonth.size) {
                    mDataMonth[i].isCheck = i == position
                }
                adapter.notifyDataSetChanged()
                mDataWeek.clear()
                mDataWeek.addAll(Lunar.getCurrentDaysOfWeek(chosenDay))
                mAdapterWeek.notifyDataSetChanged()
//                switch()
                getData()
            }
        }
    }

    private fun initAdapter() {
        mAdapter = BookSiteAdapter(R.layout.item_book_site, mData)
        rvSite.recyclerView.apply {
            var linearLayoutManager = LinearLayoutManager(this@BookSiteActivity)
            layoutManager = linearLayoutManager
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            mStartActivity<RoomBookRecordsActivity>(this) {
                putExtra(Constant.DATA, mData)
            }
        }
    }

    private fun switch() {
        isDayOfWeek = if (isDayOfWeek) {
            setDateNextVisibility(isDayOfWeek)
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
        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<SiteResp>(it)?.let {

                    it.data?.let {
                        mData.clear()
                        mData.addAll(it)
                        rvSite.notifyDataSetChanged()
                    }
                }
            }
        })

        mViewModel.mBookMonthData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<SiteResp>(it)?.let {

                    it.days?.let {
                        mDataMonth.clear()
                        mDataMonth.addAll(Lunar.getDaysOfMonth(it,chosenDay = chosenDay!!, offset = dateOffset))
                        mAdapterMonth.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}