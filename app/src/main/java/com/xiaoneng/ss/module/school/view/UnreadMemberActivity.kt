package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.module.school.model.UnreadMemberBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_unread_member.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/04/28
 * Time: 17:01
 */
class UnreadMemberActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private var code = 0
    var mDataUnread: ArrayList<UnreadMemberBean>? = null
    override fun getLayoutId(): Int = R.layout.activity_unread_member


    override fun initView() {
        super.initView()
        mDataUnread = intent.getParcelableArrayListExtra(Constant.DATA)
        code = intent.getIntExtra(Constant.ID,0)
        tvTitle0.setTitle(intent.getStringExtra(Constant.TITLE))
        mDataUnread?.let {
            var str = ""
            it.forEach {
                str = str + it.username + "、"
            }
            str = str.subSequence(0, str.length-1).toString()
            tvMembers.text = str
            tvTitleUnread.text = "未读 （" + it.size + ")"
            tvConfirm.setOnClickListener {

            }
        }
    }

    override fun initData() {
        super.initData()

    }

    override fun initDataObserver() {
        mViewModel.mStudentData.observe(this, Observer { response ->
            response?.let {
//                netResponseFormat<DepartmentPersonResp>(it)?.let {
//
//                }
            }
        })


    }

}