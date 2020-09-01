package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.model.ClassBean
import com.xiaoneng.ss.module.school.adapter.TimetableAdapter
import com.xiaoneng.ss.module.school.adapter.TimetableClassAdapter
import com.xiaoneng.ss.module.school.adapter.TimetableLabelAdapter
import com.xiaoneng.ss.module.school.adapter.TitleTimetableAdapter
import com.xiaoneng.ss.module.school.model.TimetableBean
import com.xiaoneng.ss.module.school.model.TimetableLabelBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_timetable.rvLabelTimetable
import kotlinx.android.synthetic.main.activity_timetable.rvTimetable
import kotlinx.android.synthetic.main.activity_timetable.rvTitleTimetable
import kotlinx.android.synthetic.main.activity_timetable_master.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class TimetableMasterActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var hasInitClass: Boolean = false
    private var mClassPosition = 0
    lateinit var mAdapter: TimetableAdapter
    lateinit var mAdapterClass: TimetableClassAdapter
    lateinit var mAdapterTitle: TitleTimetableAdapter
    lateinit var mAdapterLabel: TimetableLabelAdapter
    var mData = ArrayList<TimetableBean>()
    var mDataClass = ArrayList<ClassBean>()
    var mLabelData = ArrayList<TimetableLabelBean>()

    override fun getLayoutId(): Int = R.layout.activity_timetable_master


    override fun initView() {
        super.initView()
        tvTitleTimetable.setOnClickListener {
            showDialog()
        }
        initAdapterLabel()
    }

    override fun initData() {
        super.initData()

        mViewModel.getTimetable()

    }

    private fun initAdapterClass() {
        mDataClass[0].isChecked = true
        mAdapterClass = TimetableClassAdapter(R.layout.item_timetable_class, mDataClass)
        rvClassTimetable.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapterClass
        }
        mAdapterClass.setOnItemClickListener { _, view, position ->
            initClassItem(position)
        }

    }

    private fun initClassItem(position: Int) {
        if (mClassPosition != position) {
            mClassPosition = position
            mViewModel.getTimetable(mDataClass[position].classid)
            for (i in mDataClass) {
                i.isChecked = i == mDataClass[position]
            }
            mAdapterClass.notifyDataSetChanged()
        }
    }

    private fun initAdapter() {
        mAdapter = TimetableAdapter(R.layout.item_timetable, mData, mLabelData.size)
        rvTimetable.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
            setItemViewCacheSize(7)
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
        rvTimetable.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                rvTitleTimetable.scrollBy(dx, dy)
            }
        })

    }

    private fun initAdapterTitle() {
        mAdapterTitle = TitleTimetableAdapter(R.layout.item_timetable_title, mData)
        rvTitleTimetable.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })
        rvTitleTimetable.apply {
            layoutManager =
                object : LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {

                }
            adapter = mAdapterTitle
        }
        mAdapterTitle.setOnItemClickListener { _, view, position ->

        }
    }


    private fun initAdapterLabel() {
        mAdapterLabel = TimetableLabelAdapter(R.layout.item_timetable_label, mLabelData)
        rvLabelTimetable.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterLabel
        }
        mAdapterLabel.setOnItemClickListener { _, view, position ->

        }
    }


    override fun initDataObserver() {
        mViewModel.mTimetableData.observe(this, Observer { response ->
            response?.let {
                //初始化时间段数据
                mLabelData.clear()
                mLabelData.addAll(it.positions)
                mAdapterLabel.notifyDataSetChanged()

                if (!hasInitClass){
                    hasInitClass = true
                    mDataClass.addAll(it.classs)
                    mAdapterClass.notifyDataSetChanged()
                }
                //填充课表
                mData.clear()
                mData.addAll(it.list)
                if (mData.size > 0) {
                    var i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                    initAdapter()
                    initAdapterTitle()
                    rvTimetable.scrollToPosition(DateUtil.getWeekPosition(i))
                    rvTitleTimetable.scrollToPosition(DateUtil.getWeekPosition(i))
                } else {
                    showEmpty()
                }

            }
        })

    }


    private fun showDialog() {
        // 底部弹出对话框
        val bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_timetable, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        bottomDialog.show()
        contentView.findViewById<View>(R.id.tvAction1TimeDialog)
            .setOnClickListener { v: View? ->
                tvTitleTimetable.text = "班级课表"
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvAction2TimeDialog)
            .setOnClickListener { v: View? ->
                tvTitleTimetable.text = "科任课表"
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvAction3TimeDialog)
            .setOnClickListener { v: View? ->
                bottomDialog.dismiss()
            }
    }

}