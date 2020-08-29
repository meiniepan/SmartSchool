package com.xiaoneng.ss.module.mine.view

import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleFragment
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.common.utils.displayImage
import com.xiaoneng.ss.common.utils.mDownloadFile
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.mine.adapter.MineAdapter
import com.xiaoneng.ss.module.mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_mine.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class MineFragment : BaseLifeCycleFragment<MineViewModel>() {
    protected lateinit var mAdapter: MineAdapter
    private val OBJECT_KEY = "avatar/student/id/avatar"

    override fun getLayoutId(): Int = R.layout.fragment_mine

    companion object {
        fun getInstance(): MineFragment? {
            return MineFragment()
        }

    }

    override fun initView() {
        super.initView()
        displayImage(requireContext(), UserInfo.getUserBean().portrait, ivAvatarMine)
        tvNameMine.text = UserInfo.getUserBean().realname

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
initAvatar()

    }
    private fun initAvatar() {
        mViewModel.getSts()
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
            OBJECT_KEY,
            mDownloadFile(requireContext()),
            object : OssListener {
                override fun onSuccess(filePath: String) {


                }

                override fun onFail() {
                    view?.post {
                        requireContext().toast("头像下载失败")
                    }
                }

                override fun onSuccess2(filePath: ByteArray?) {
                    view?.post {
                        showSuccess()
                        displayImage(
                            requireContext(), filePath,
                            ivAvatarMine
                        )
                    }
                }
            })
    }
}