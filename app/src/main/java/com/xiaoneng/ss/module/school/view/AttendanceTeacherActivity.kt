package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.module.school.adapter.AttendanceStuAdapter
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.model.AttendanceBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_attendance_stu.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AttendanceTeacherActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: AttendanceStuAdapter
    var mData: ArrayList<AttendanceBean> = ArrayList()
    private val bottomDialog: Dialog by lazy {
        initDialog()
    }

    override fun getLayoutId(): Int = R.layout.activity_attendance_stu


    override fun initView() {
        super.initView()
        initTitle()
    }

    private fun initTitle() {



    }


    override fun initData() {
        super.initData()
        mViewModel.getAttendanceMaster("")
    }


    override fun initDataObserver() {
        mViewModel.mAttendanceMasterData.observe(this, Observer { response ->
            response?.let {
                mData.clear()
                mData.addAll(it.data)
                if (mData.size > 0) {
                    mAdapter.notifyDataSetChanged()
                } else {
                    showEmpty()
                }
            }
        })

    }

    private fun initPopWindow() {
//        stringPopupWindow = StringPopupWindow(this, titles)
//        //  //这里设置宽度 否则非正常显示  构造方法设置定值默认无效 必须popwindow 初始化之后 设置才有效
//        val width = windowManager?.defaultDisplay?.width
//        stringPopupWindow?.width = tvTitleAttendanceStu.width
//        stringPopupWindow?.setCallBack(object : StringPopupWindow.CallBack {
//            override fun onShowContent(content: String) {
//                tvTitleAttendanceStu.text = content
//            }
//
//
//        })

    }

    private fun initDialog(): Dialog {
        var titles = ArrayList<String>().apply {
            add("今日考勤")
            add("班级考勤")
            add("课堂考勤")
        }
        // 底部弹出对话框
        var bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titles)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(this@AttendanceTeacherActivity)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            tvLabel1Attendance.text = titles[position]
            bottomDialog.dismiss()
        }

        return bottomDialog
    }
}