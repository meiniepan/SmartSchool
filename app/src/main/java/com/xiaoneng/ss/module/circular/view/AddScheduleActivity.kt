package com.xiaoneng.ss.module.circular.view

import android.app.Dialog
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.circular.adapter.ChooseColorAdapter
import com.xiaoneng.ss.module.circular.model.ScheduleBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import kotlinx.android.synthetic.main.activity_add_schedule.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class AddScheduleActivity : BaseLifeCycleActivity<CircularViewModel>() {
    private var chosenColor: String = "#5E37FF"
    var beanJson: String by SPreference(Constant.USER_INFO, UserInfo.emptyJson)
    lateinit var mAdapter: ChooseColorAdapter
    var time: Long = 0L
    var beginTime: String? = ""
    var endTime: String? = ""
    val mData by lazy { ColorUtil.getCustomColors() }


    override fun getLayoutId(): Int = R.layout.activity_add_schedule


    override fun initView() {
        super.initView()
        time = intent.getLongExtra(Constant.DATA, System.currentTimeMillis())
        tvBeginAddSchedule.text = DateUtil.getNearTimeBegin(time)
        tvStopAddSchedule.text = DateUtil.getNearTimeEnd(time)
        beginTime = DateUtil.getNearTimeBeginYear(time)
        endTime = DateUtil.getNearTimeEndYear(time)
        tvActionTitle.setOnClickListener {
            addSchedule()
        }
        tvBeginAddSchedule.apply {
            setOnClickListener {
                showDatePick(this) {
                    beginTime = this
                    toast(beginTime!!)
                }
            }
        }
        tvStopAddSchedule.apply {
            setOnClickListener {
                showDatePick(this) {
                    endTime = this
                    toast(endTime!!)
                }
            }
        }
        initAdapter()
    }

    private fun addSchedule() {
        if (
            TextUtils.isEmpty(etThemeAddSchedule.text.toString()) ||
            TextUtils.isEmpty(tvBeginAddSchedule.text.toString())

        ) {
            toast("请完善信息")
            return
        }
        var bean: ScheduleBean = ScheduleBean()
        bean.token = UserInfo.getUserBean().token
        bean.title = etThemeAddSchedule.text.toString()
        bean.scheduletime = beginTime
        bean.scheduleover = endTime
        bean.remark = etRemarkAddSchedule.text.toString()
        bean.color = chosenColor
        showLoading()
        mViewModel.addSchedule(bean)
    }

    private fun initAdapter() {
        mAdapter = ChooseColorAdapter(R.layout.item_choose_color, mData)

        rvChooseColor.apply {
            layoutManager = GridLayoutManager(context, 6)

            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            for (i in mData) {
                if (i.isCheck) {
                    i.isCheck = false
                }
                mData[position].isCheck = true
                adapter.notifyDataSetChanged()
                chosenColor = mData[position].color
            }
        }
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }


    override fun initDataObserver() {
        mViewModel.mAddScheduleData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                toast("添加成功")
                finish()
            }
        })

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

}