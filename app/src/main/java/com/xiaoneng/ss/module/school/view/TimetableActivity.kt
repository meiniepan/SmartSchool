package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.module.school.adapter.TimetableAdapter
import com.xiaoneng.ss.module.school.adapter.TimetableLabelAdapter
import com.xiaoneng.ss.module.school.model.LessonBean
import com.xiaoneng.ss.module.school.model.TimetableBean
import com.xiaoneng.ss.module.school.model.TimetableLabelBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_timetable.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class TimetableActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: TimetableAdapter
    lateinit var mAdapterLabel: TimetableLabelAdapter
    var mData = ArrayList<TimetableBean>()
    var mLabelData = ArrayList<TimetableLabelBean>()

    override fun getLayoutId(): Int = R.layout.activity_timetable


    override fun initView() {
        super.initView()
        initAdapter()
        initAdapterLabel()
    }

    override fun initData() {
        super.initData()
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
        mLabelData.add(TimetableLabelBean("06:00-06:45"))
//        mViewModel.getTimetable()
        var lessonBean = ArrayList<LessonBean>().apply {
            add(LessonBean())
            add(LessonBean())
            add(LessonBean())
            add(LessonBean())
            add(LessonBean())
            add(LessonBean())
            add(LessonBean())
            add(LessonBean())
            add(LessonBean())
            add(LessonBean())
            add(LessonBean())
        }
        mData.add(TimetableBean(lessons = lessonBean))
        mData.add(TimetableBean(lessons = lessonBean))
        mData.add(TimetableBean(lessons = lessonBean))
        mData.add(TimetableBean(lessons = lessonBean))
        mData.add(TimetableBean(lessons = lessonBean))
        mData.add(TimetableBean(lessons = lessonBean))
        mData.add(TimetableBean(lessons = lessonBean))
        mAdapter.notifyDataSetChanged()
        mAdapterLabel.notifyDataSetChanged()
    }

    private fun initAdapter() {
        mAdapter = TimetableAdapter(R.layout.item_timetable, mData)
        rvTimetable.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

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

    override fun onBackPressed() {
        showDialog()
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
                finish()
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvAction2TimeDialog)
            .setOnClickListener { v: View? ->
                finish()
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvAction3TimeDialog)
            .setOnClickListener { v: View? ->

                bottomDialog.dismiss()
            }
    }

}