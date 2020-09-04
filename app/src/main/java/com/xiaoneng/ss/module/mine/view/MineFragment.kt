package com.xiaoneng.ss.module.mine.view

import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.displayImage
import com.xiaoneng.ss.common.utils.mDownloadFile
import com.xiaoneng.ss.common.utils.mStartActivity
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
 * @date: 2020/02/27
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

        when (UserInfo.getUserBean().usertype) {
            "1" -> {
                if ((UserInfo.getUserBean().logintype) == Constant.LOGIN_TYPE_STU) {
                    llItem3.visibility = View.GONE
                    llItem6.visibility = View.VISIBLE
                } else {
                    llItem3.visibility = View.VISIBLE
                    llItem6.visibility = View.GONE
                }
            }

            else -> {
                llItem3.visibility = View.GONE
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
            mStartActivity<SwitchIdActivity>(requireContext())
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
        initAvatar()
    }

    private fun initAvatar() {
        if (!TextUtils.isEmpty(UserInfo.getUserBean().portrait)) {
            if (File(mDownloadFile(requireContext(), UserInfo.getUserBean().portrait)).exists()) {
                displayImage(
                    requireContext(),
                    mDownloadFile(
                        requireContext(),
                        UserInfo.getUserBean().portrait
                    ),
                    ivAvatarMine
                )
            } else {
                mViewModel.getSts()
            }
        }
    }

    override fun initDataObserver() {
        mViewModel.mStsData.observe(this, Observer { response ->
            response?.let {
                doDownload(it)
            }
        })
    }

    private fun doDownload(it: StsTokenResp) {

        showLoading()
        OssUtils.downloadFile(
            requireContext(),
            it.Credentials,
            Constant.OBJECT_KEY + UserInfo.getUserBean().portrait,
            mDownloadFile(requireContext(), UserInfo.getUserBean().portrait),
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
                            mDownloadFile(requireContext(), UserInfo.getUserBean().portrait),
                            ivAvatarMine
                        )
                    }
                }
            })
    }
}