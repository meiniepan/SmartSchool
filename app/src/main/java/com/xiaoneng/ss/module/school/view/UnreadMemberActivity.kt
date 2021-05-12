package com.xiaoneng.ss.module.school.view

import android.view.View
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.module.circular.model.UnreadMemberResponse
import com.xiaoneng.ss.module.school.model.UnreadMemberBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_unread_member.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/04/28
 * Time: 17:01
 */
class UnreadMemberActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var code = "0"
    var mDataUnread: UnreadMemberResponse? = null
    override fun getLayoutId(): Int = R.layout.activity_unread_member


    override fun initView() {
        super.initView()
        mDataUnread = intent.getParcelableExtra(Constant.DATA)
        mDataUnread?.let {
            code = it.code
            tvTitle0.setTitle(it.title)
            var str = ""
            it.data?.forEach {
                str = str + it.username + "、"
            }
            str = str.subSequence(0, str.length - 1).toString()
            tvMembers.text = str
            tvTitleUnread.text = "未读 （" + it.data?.size + ")"
            if (it.publishUserId == UserInfo.getUserBean().uid) {
                tvConfirm.visibility = View.VISIBLE
                tvConfirm.setOnClickListener {
                    if (mDataUnread?.code == "1") {
                        mViewModel.remindUnread(id = mDataUnread?.id)
                    }
                }
            } else {
                tvConfirm.visibility = View.GONE
            }
        }
    }

    override fun initData() {
        super.initData()

    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                finish()
//                netResponseFormat<DepartmentPersonResp>(it)?.let {
//
//                }
            }
        })


    }

}