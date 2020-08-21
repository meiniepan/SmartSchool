package com.xiaoneng.ss.module.mine.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.displayImage
import com.xiaoneng.ss.common.utils.starPhoneNum
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_mine_info.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class MineInfoActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_mine_info


    override fun initView() {
        super.initView()
        displayImage(this, UserInfo.getUserBean().portrait, ivAvatarMineInfo)
        tvNameMineInfo.text = UserInfo.getUserBean().realname
        tvMineItem1.text = UserInfo.getUserBean().realname
        tvMineItem4.text = starPhoneNum(UserInfo.getUserBean().phone)

    }

    override fun initDataObserver() {
//        mViewModel.mSystemTabNameData.observe(this, Observer { response ->
//            response?.let {
//                setSystemTabData(it)
//            }
//        })
    }


}