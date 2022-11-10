package com.xiaoneng.ss.module.activity

import android.os.CountDownTimer
import android.os.Handler
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.module.circular.adapter.ImageMarqueeAdapter
import com.xiaoneng.ss.module.circular.adapter.NoticeFileAdapter
import com.xiaoneng.ss.module.school.model.FileInfoBean
import kotlinx.android.synthetic.main.activity_add_parent.*
import kotlinx.android.synthetic.main.activity_my_test.*
import kotlinx.android.synthetic.main.activity_notice_detail.*

class MyTestActivity : BaseLifeCycleActivity<AccountViewModel>() {
    lateinit var mAdapterFile: ImageMarqueeAdapter
    var mDataFile = ArrayList<FileInfoBean>()
    private var timer: CountDownTimer? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_my_test
    }

    override fun initData() {

    }

    override fun initView() {
        mDataFile.add(FileInfoBean(name = "1"))
        mDataFile.add(FileInfoBean(name = "2"))
        mDataFile.add(FileInfoBean(name = "3"))
        mDataFile.add(FileInfoBean(name = "4"))
        mDataFile.add(FileInfoBean(name = "5"))
        mDataFile.add(FileInfoBean(name = "6"))
        mDataFile.add(FileInfoBean(name = "7"))
        mDataFile.add(FileInfoBean(name = "7"))
        mDataFile.add(FileInfoBean(name = "7"))
        mDataFile.add(FileInfoBean(name = "7"))
        mDataFile.add(FileInfoBean(name = "8"))
        mAdapterFile = ImageMarqueeAdapter(R.layout.item_image_marquee, mDataFile)
        rvTest.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            mAdapterFile.bindToRecyclerView(this)
        }
        //禁止手动滑动
        rvTest.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })
        lifecycle.addObserver(mAdapterFile)
        tv1.setOnClickListener {
            rvTest.scrollToPosition(0)
        }
        tv2.setOnClickListener {
        }
    }

    override fun initDataObserver() {

    }
}