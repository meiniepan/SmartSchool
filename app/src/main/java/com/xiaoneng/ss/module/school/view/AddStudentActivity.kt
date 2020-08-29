package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.mine.adapter.InviteCodeAdapter
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_student.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class AddStudentActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private lateinit var mAdapter: InviteCodeAdapter
    var mData = ArrayList<NoticeBean>()


    override fun getLayoutId(): Int = R.layout.activity_add_student


    override fun initView() {
        super.initView()
        initAdapter()

    }


    private fun initAdapter() {
        mAdapter = InviteCodeAdapter(R.layout.item_invite_code, mData)
        rvAddStu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->
            showDialog()
        }
    }


    override fun initData() {
        super.initData()
        mData.add(NoticeBean(""))
        mData.add(NoticeBean(""))
        mData.add(NoticeBean(""))
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

    private fun showDialog() {
        // 弹出对话框
        val bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels- dp2px(this,53f*2).toInt()
        params.marginEnd = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.CENTER)
        bottomDialog.show()
        contentView.findViewById<View>(R.id.tvAction1AddStuDialog)
            .setOnClickListener { v: View? ->
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvAction2AddStuDialog)
            .setOnClickListener { v: View? ->
                bottomDialog.dismiss()
            }
        contentView.findViewById<TextView>(R.id.tvNameAddStuDialog)
            .text = "haha"
    }

}