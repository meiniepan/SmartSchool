package com.xiaoneng.ss.module.mine.view

import android.os.Handler
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.view.LoginSwitchActivity
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.mine.adapter.MineAdapter
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.toast
import java.io.File

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class
MineFragment : BaseLifeCycleFragment<AccountViewModel>() {
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

        if (UserInfo.getUserBean().usertype == "1" &&
            UserInfo.getUserBean().logintype == Constant.LOGIN_TYPE_PAR
        ) {
            tvNameMine.text = UserInfo.getUserBean().parentname
        }

        when (UserInfo.getUserBean().usertype) {
            "1" -> {
                if ((UserInfo.getUserBean().logintype) == Constant.LOGIN_TYPE_STU) {
                    llItem3.visibility = View.GONE
                    llItem6.visibility = View.VISIBLE
                } else {
                    llItem3.visibility = View.VISIBLE
                    llItem6.visibility = View.GONE
                    llItem2.visibility = View.GONE
                }
                llItem4.visibility = View.GONE
            }

            "2" -> {
                if ((UserInfo.getUserBean().classmaster) == "1") {
                    llItem4.visibility = View.VISIBLE
                } else {
                    llItem4.visibility = View.GONE
                }
                llItem3.visibility = View.GONE
                llItem6.visibility = View.GONE
            }

            "99" -> {
                if ((UserInfo.getUserBean().classmaster) == "1") {
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
//            requireContext().toast(text)
        }

        llItem7.setOnClickListener {
            mStartActivity<SysSettingActivity>(requireContext())
//            mViewModel.downloadFile(
//                requireContext(), StsTokenBean(
//                    "STS.NSt9Rb9EqkGW1ZqUjfMYHa4r2",
//                    "3GpQQryKu4D6V33CdaKzD3skoR7XYcus4v9n79w45Hr3",
//
//                    "CAIS8wF1q6Ft5B2yfSjIr5DBcujW1JpQ3IW8M3zAsWozQdZkjvHZ0Dz2IHtJenVvAO8ct/k0nW9Y7fYflqJ4T55IQ1Dza8J148zcJO1WyMyT1fau5Jko1beHewHKeTOZsebWZ+LmNqC/Ht6md1HDkAJq3LL+bk/Mdle5MJqP+/UFB5ZtKWveVzddA8pMLQZPsdITMWCrVcygKRn3mGHdfiEK00he8TovsvnnkpzCs0KE1gCqkbAvyt6vcsT+Xa5FJ4xiVtq55utye5fa3TRYgxowr/wr0vAboGid54zGWgYBuEXbKYvE89RoKgJ/afIq3jfIBgE0XJYagAFQ2kdmcjbbQp6Sr4a9xc9lNaJWjgT7hB+AaxUKAZaZQbkaKzeIb3L6u5N+uoczDslkDhK8pag97yRNYjY/X62N1Lc2bhKIW6cAFGXqRPJJR10etNcoWoGQuOBDi4sjJeSFeT5GE83t5LRryNmKFougQJCT46VJNJG5QwbrkGozHQ=="
//                )
//            )
        }

    }

    override fun onResume() {
        super.onResume()
        if (UserInfo.getUserBean().usertype == "1" &&
            UserInfo.getUserBean().logintype == Constant.LOGIN_TYPE_PAR
        ) {

        } else {
            initAvatar()
        }
    }

    private fun initAvatar() {
        val mAvatarFileName: String = UserInfo.getUserBean().portrait.split("/").last()
        if (!TextUtils.isEmpty(mAvatarFileName)) {
            if (File(mDownloadFile(requireContext(), mAvatarFileName)).exists()) {
                displayImage(
                    requireContext(),
                    mDownloadFile(
                        requireContext(),
                        mAvatarFileName
                    ),
                    ivAvatarMine
                )
            } else {
                showLoading()
                mViewModel.getSts()
            }
        }
    }

    override fun initDataObserver() {
        mViewModel.mStsData.observe(this, Observer { response ->
            response?.let {

                Handler().postDelayed(
                    {
                        doDownload(it)
                    },100)
            }
        })
    }

    private fun doDownload(it: StsTokenResp) {
        val mAvatarFileName: String = UserInfo.getUserBean().portrait.split("/").last()
        showLoading()
        OssUtils.downloadFile(
            requireContext(),
            it.Credentials,
            UserInfo.getUserBean().portrait,
            mDownloadFile(requireContext(), mAvatarFileName),
            object : OssListener {

                override fun onFail() {
                    showSuccess()
                    view?.post {
                        requireContext().toast("头像下载失败")
                    }
                }

                override fun onSuccess() {
                    view?.post {
                        showSuccess()
                        displayImage(
                            requireContext(),
                            mDownloadFile(requireContext(), mAvatarFileName),
                            ivAvatarMine
                        )
                    }
                }
            })
    }
}