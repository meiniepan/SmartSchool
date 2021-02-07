package com.xiaoneng.ss.module.mine.view

import android.view.View
import androidx.fragment.app.Fragment
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.view.LoginSwitchActivity
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.AppManager
import com.xiaoneng.ss.common.utils.displayImage
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.mine.adapter.MineAdapter
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class MineFragment : BaseLifeCycleFragment<AccountViewModel>() {
    lateinit var mAdapter: MineAdapter
    override fun getLayoutId(): Int = R.layout.fragment_mine

    companion object {
        fun getInstance(): Fragment {
            return MineFragment()
        }

    }

    override fun initView() {
        super.initView()
        tvNameMine.text = UserInfo.getUserBean().realname

        when {
            AppInfo.checkRule2("user/student/modify") -> {
                llItem3.visibility = View.GONE
                llItem6.visibility = View.VISIBLE
                llItem4.visibility = View.GONE
            }

            AppInfo.checkRule2("user/student/modifyParents") -> {
                tvNameMine.text = UserInfo.getUserBean().parentname
                llItem3.visibility = View.VISIBLE
                llItem6.visibility = View.GONE
                llItem2.visibility = View.GONE
                llItem4.visibility = View.GONE
            }

            AppInfo.checkRule2("user/teachers/modify") -> {
                if (AppInfo.checkRule2("teacher/classs/codeList")) {
                    llItem4.visibility = View.VISIBLE
                } else {
                    llItem4.visibility = View.GONE
                }
                llItem3.visibility = View.GONE
                llItem6.visibility = View.GONE
            }


            else -> {
                llItem3.visibility = View.GONE
                llItem4.visibility = View.GONE
                llItem6.visibility = View.GONE
            }
        }

        llItem1.setOnClickListener {
            mStartActivity<MineInfoActivity>(requireContext())
        }

        llItem2.setOnClickListener {
            mStartActivity<AccountSecureActivity>(requireContext())
        }


        llItem3.setOnClickListener {
            mStartActivity<ChooseChildActivity>(requireContext())
        }


        llItem4.setOnClickListener {
            mStartActivity<InviteCodeActivity>(requireContext())
        }

        llItem5.setOnClickListener {
            requireActivity().mAlert(
                "切换身份后将改变您的操作权限",
                "是否确认切换身份"
            ) {
                AppManager.finishAllActivity()
                mStartActivity<LoginSwitchActivity>(requireContext())
            }
        }

        llItem6.setOnClickListener {
            mStartActivity<BindParentActivity>(requireContext())
//            var text:String = ""
//                File(mDownloadFile(requireContext()))?.let {text = it.readText()  }
//            requireContext().mToast(text)
        }

        llItem7.setOnClickListener {
            mStartActivity<SysSettingActivity>(requireContext())
        }

    }

    override fun onResume() {
        super.onResume()
        if (AppInfo.checkRule2("user/student/modifyParents")) {

        } else {
            initAvatar()
        }
    }

    private fun initAvatar() {
                displayImage(
                    requireContext(),
                    UserInfo.getUserBean().domain+UserInfo.getUserBean().portrait,
                    ivAvatarMine
                )
    }

    override fun initDataObserver() {

    }

}