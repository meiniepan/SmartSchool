package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.showDatePick
import com.xiaoneng.ss.module.mine.adapter.InviteCodeAdapter
import com.xiaoneng.ss.module.mine.model.InviteCodeBean
import com.xiaoneng.ss.module.school.model.TaskBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_task.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AddTaskActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    var beginTime: String = System.currentTimeMillis().toString()
    var endTime: String = ""
    var orderTime: String = ""
    lateinit var mAdapter: InviteCodeAdapter
    var mData = ArrayList<InviteCodeBean>()


    override fun getLayoutId(): Int = R.layout.activity_add_task


    override fun initView() {
        super.initView()
        beginTime = DateUtil.getNearTimeBeginYear()
        endTime = DateUtil.getNearTimeEndYear()
        initAdapter()
        DateUtil.showTimeFromNet(DateUtil.getNearTimeBeginYear(),tvBeginDate,tvBeginTime)
        DateUtil.showTimeFromNet(DateUtil.getNearTimeEndYear(),tvEndDate,tvEndTime)
        llBeginTime.apply {
            setOnClickListener {
                showDatePick(tvBeginDate,tvBeginTime) {
                    beginTime = this
                }
            }
        }
        llEndTime.apply {
            setOnClickListener {
                showDatePick(tvEndDate,tvEndTime) {
                    endTime = this
                }
            }
        }
        ivTimingAddTask.apply {
            setOnClickListener {
                showDatePick(tvEndDate,tvEndTime) {
                    orderTime = this
                    this@apply.setImageResource(R.drawable.ic_timing_blue)
                    tvConfirmAddTask.text = "定时发布"
                }
            }
        }


        tvAddParticipant.setOnClickListener {
            doAddParticipant()
        }
        tvAddPrincipal.setOnClickListener {
            doAddPrincipal()
        }
        tvConfirmAddTask.setOnClickListener {
            addTask()
        }
    }

    private fun addTask() {
        if (tvTitleAddTask.text.isEmpty()){
            toast(R.string.lack_info)
            return
        }
        var pList :ArrayList<UserBeanSimple> = ArrayList()
        pList.add(UserBeanSimple("5","2"))
        mViewModel.addTask(
            TaskBean(
                UserInfo.getUserBean().token,
               taskname =  tvTitleAddTask.text.toString(),
               plantime =  beginTime,
               plantotal =  10.toString(),
                overtime = endTime,
                involve = pList,
                ordertime = orderTime
            )
        )
    }

    private fun doAddPrincipal() {
        mViewModel.queryDepartments()
    }

    private fun doAddParticipant() {
        mViewModel.queryDepartments()
    }

    override fun initStatusBar() {
        initStatusColor(resources.getColor(R.color.white))
    }


    private fun initAdapter() {
        mAdapter = InviteCodeAdapter(R.layout.item_invite_code, mData)
        rvParticipant.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(RecycleViewDivider(context, dp2px(context, 82f).toInt()))
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }


    override fun onBackPressed() {
        showDialog()
    }

    private fun showDialog() {
        // 底部弹出对话框
        val bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_save_draft, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        bottomDialog.show()
        contentView.findViewById<View>(R.id.tvSaveDraft)
            .setOnClickListener { v: View? ->
                finish()
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvNoSaveDraft)
            .setOnClickListener { v: View? ->
                finish()
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvCancelDraft)
            .setOnClickListener { v: View? ->

                bottomDialog.dismiss()
            }
    }



    override fun initDataObserver() {
        mViewModel.mAddTaskData.observe(this, Observer { response ->
            response?.let {
                toast("发布成功")
            }
        })

    }

}