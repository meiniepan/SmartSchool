package com.xiaoneng.ss.base.view


import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Transport
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.view.LoginStuActivity
import com.xiaoneng.ss.account.view.LoginSwitchActivity
import com.xiaoneng.ss.account.view.LoginTeacherActivity
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.common.callback.EmptyCallBack
import com.xiaoneng.ss.common.callback.ErrorCallBack
import com.xiaoneng.ss.common.callback.LoadingCallBack
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.StateType
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.CommonUtil
import com.xiaoneng.ss.common.utils.mStartActivity

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/22
 * Time: 16:36
 */

abstract class BaseLifeCycleFragment<VM : BaseViewModel<*>> : BaseFragment() {
    protected lateinit var mViewModel: VM

    override fun initView() {

        showSuccess()

        mViewModel = ViewModelProvider(this).get(CommonUtil.getClass(this))

        mViewModel.loadState.observe(this, observer)

        initDataObserver()
    }

    abstract fun initDataObserver()

     fun showLoading() {
        loadService.showCallback(LoadingCallBack::class.java)
    }

    private fun showSuccess() {
        loadService.showCallback(SuccessCallback::class.java)
    }

    private fun showError(msg: String) {
        loadService.showCallback(ErrorCallBack::class.java)
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            loadService.setCallBack(ErrorCallBack::class.java, Transport { context, view ->
                val mTvEmpty = view.findViewById(R.id.tv_error) as TextView
                mTvEmpty.text = msg
            })
        }
    }

    open fun showTip(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            MaterialDialog(requireContext()).show {
                title(R.string.title)
                message(text = msg)
                cornerRadius(8.0f)
                negativeButton(R.string.done)
            }
            false
        }
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showEmpty() {
        loadService.showCallback(EmptyCallBack::class.java)
    }

    private val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
                    StateType.SUCCESS -> showSuccess()
                    StateType.LOADING -> showLoading()
                    StateType.ERROR -> showTip(it.message)
                    StateType.NETWORK_ERROR -> showError("网络异常")
                    StateType.TIP -> showTip(it.message)
                    StateType.EMPTY -> showEmpty()
                    StateType.NOT_LOGIN -> {
                        when (UserInfo.getUserBean().usertype) {
                            "1" -> {
                                mStartActivity<LoginStuActivity>(requireContext())
                            }
                            "2" -> {
                                mStartActivity<LoginTeacherActivity>(requireContext())
                            }
                            else -> {
                                mStartActivity<LoginSwitchActivity>(requireContext())
                            }
                        }
                    }
                }
            }
        }
    }


    override fun reLoad() {
        showLoading()
        super.reLoad()
    }

}