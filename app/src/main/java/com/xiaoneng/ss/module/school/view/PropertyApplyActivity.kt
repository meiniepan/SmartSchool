package com.xiaoneng.ss.module.school.view

import android.widget.EditText
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.aliSpeech.SpeechTranscriberActivity
import com.xiaoneng.ss.module.school.model.RepairBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_property_apply.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:填写报修报送
 * @date :2020/10/23 3:17 PM
 */
class PropertyApplyActivity : SpeechTranscriberActivity<SchoolViewModel>() {

    private var chosenDevice: String = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_property_apply
    }

    override fun getEditText(): EditText {
        return etPropertyRemark
    }
    override fun initView() {
        super.initView()
        tvPropertyConfirm.setOnClickListener {
            doConfirm()
        }
        tvPropertyStart.setOnClickListener {
            doStart()
        }
        tvPropertyStop.setOnClickListener {
            doStop()
        }
    }


    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
    }

    private fun doConfirm() {
        var bean = RepairBean(
            token = UserInfo.getUserBean().token,
            remark = "aa"
        )
        mViewModel.addRepair(bean)
    }


    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                toast(R.string.deal_done)
                finish()
            }
        })
    }
}