package com.xiaoneng.ss.module.school.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.custom.popup.StringPopupWindow
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_attendance_stu.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class AttendanceStuActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var stringPopupWindow: StringPopupWindow? = null
    var titles = ArrayList<String>()

    override fun getLayoutId(): Int = R.layout.activity_attendance_stu


    override fun initView() {
        super.initView()
        initTitle()
    }

    private fun initTitle() {
        titles.add("我的考勤")
        titles.add("你的考勤")
        tvTitleAttendanceStu.text = titles[0]
        tvTitleAttendanceStu.setOnClickListener {
            if (stringPopupWindow == null) {
                initPopWindow()
            }
            //  //这里设置宽度 否则非正常显示  构造方法设置定值默认无效 必须popwindow 初始化之后 设置才有效
            val width = windowManager?.defaultDisplay?.width
            stringPopupWindow?.width = tvTitleAttendanceStu.width
//showPopupWindow  不能直接放在initview 中
            stringPopupWindow?.showPopupWindow(tvTitleAttendanceStu)
        }


    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }


    override fun initDataObserver() {
//        mViewModel.mNoticeData.observe(this, Observer { response ->
//            response?.let {
//                mData.clear()
//                mData.addAll(it.data)
//                if (mData.size > 0) {
//                    mAdapter.notifyDataSetChanged()
//                } else {
//                    showEmpty()
//                }
//            }
//        })

    }

    private fun initPopWindow() {
        stringPopupWindow = StringPopupWindow(this,titles)
        stringPopupWindow?.setCallBack(object : StringPopupWindow.CallBack {
            override fun onShowContent(content: String) {
tvTitleAttendanceStu.text = content
            }


        })

    }
}