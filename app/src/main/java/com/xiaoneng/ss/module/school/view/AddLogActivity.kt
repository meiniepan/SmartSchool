package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.module.school.model.LogBean
import com.xiaoneng.ss.module.school.model.TaskLogRequest
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_log.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AddLogActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    lateinit var taskBean:LogBean
    override fun getLayoutId(): Int = R.layout.activity_add_log


    override fun initView() {
        super.initView()
        taskBean = intent.getParcelableExtra(Constant.DATA)
        rvConfirm.setOnClickListener {
            if (etFeedback.text.toString().isNullOrEmpty()) {
                toast(R.string.lack_info)
                return@setOnClickListener
            }
            var bean = TaskLogRequest(
                UserInfo.getUserBean().token,
                taskBean.id?:"",
                etFeedback.text.toString(),
                completestatus = "1"
            )
            mViewModel.modifyTaskInfo(bean)
        }

        etFeedback.setText(taskBean.feedback)
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
    }


    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                finish()
            }
        })

    }


}