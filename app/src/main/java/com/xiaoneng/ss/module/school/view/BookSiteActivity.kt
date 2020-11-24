package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.Lunar
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.circular.adapter.DaysOfWeekAdapter
import com.xiaoneng.ss.module.circular.adapter.WeekTitleAdapter
import com.xiaoneng.ss.module.circular.model.DayBean
import com.xiaoneng.ss.module.school.adapter.SiteAdapter
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_book_site.*

/**
 * @author Burning
 * @description:场地预约
 * @date :2020/10/23 3:17 PM
 */
class BookSiteActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var chosenDay: Long? = System.currentTimeMillis()
    lateinit var mAdapterWeek: DaysOfWeekAdapter
    var isDayOfWeek = true
    var mDataWeekTitle = ArrayList<String>()
    var mDataWeek = ArrayList<DayBean>()
    lateinit var mAdapter: SiteAdapter
    var mData: ArrayList<SiteBean> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.activity_book_site
    }

    override fun initView() {
        super.initView()
        tvWeekSchedule.text = Lunar.getWhichWeek(chosenDay)

        initAdapterWeekTitle()
        initAdapterDayOfWeek()
        mDataWeek.clear()
        mDataWeek.addAll(Lunar.getCurrentDaysOfWeek(chosenDay))
        initAdapter()
        tvBookSiteRecords.setOnClickListener {
            mStartActivity<BookSiteRecordsActivity>(this)
        }
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mViewModel.getCanBookRooms(DateUtil.formatDateCustomDay(chosenDay!!))
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

    private fun initAdapter() {
        mAdapter = SiteAdapter(R.layout.item_site, mData)
        rvSite.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BookSiteActivity)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mStartActivity<AddBookSiteActivity>(this)
        }
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<List<SiteBean>>(it)?.let {
                    mData.addAll(it)
                    mAdapter.setPosition(DateUtil.getBookSitePositionNearNow(chosenDay!!))
                    rvSite.notifyDataSetChanged()
                }
            }
        })
    }
}