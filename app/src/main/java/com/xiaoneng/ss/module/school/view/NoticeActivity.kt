package com.xiaoneng.ss.module.school.view

import android.text.TextUtils
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.eventBus.ChangeThemeEvent
import com.xiaoneng.ss.common.utils.eventBus.RefreshUnreadEvent
import com.xiaoneng.ss.module.school.interfaces.INoticeUnread
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_notice.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author Burning
 * @description:全校通告
 * @date :2020/11/25 3:17 PM
 */
class NoticeActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_notice
    }

    override fun initView() {
        super.initView()

    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()

    }



    override fun initDataObserver() {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshNotice(event: RefreshUnreadEvent) {
        var unread = event.unread
        if (unread=="0"|| TextUtils.isEmpty(unread)){
            unread="通知公告"
        }else{
            unread = "通知公告($unread)"
        }
        ctbNotice.setTitle(unread)
    }

}