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
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.common.callback.EmptyCallBack
import com.xiaoneng.ss.common.callback.ErrorCallBack
import com.xiaoneng.ss.common.callback.LoadingCallBack
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.StateType
import com.xiaoneng.ss.common.utils.CommonUtil
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mainLogin
import com.xiaoneng.ss.module.school.view.SalaryCaptchaActivity

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

    fun showSuccess() {
        loadService.showCallback(SuccessCallback::class.java)
    }

    private fun showError(msg: String) {
        loadService.showCallback(SuccessCallback::class.java)
//        loadService.showCallback(ErrorCallBack::class.java)
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//            loadService.setCallBack(ErrorCallBack::class.java, Transport { context, view ->
//                val mTvEmpty = view.findViewById(R.id.tv_error) as TextView
//                mTvEmpty.text = msg
//            })
        }
    }

    open fun showTip(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            requireContext().mAlert(msg){

            }
        }
        loadService.showCallback(SuccessCallback::class.java)
    }
    open fun showNotLoginTip(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            requireContext().mAlert(msg){
                mainLogin(requireContext())
            }
        }
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showTmpOutTimeTip(msg: String = "请重新验证") {
//        if (!TextUtils.isEmpty(msg)) {
//            MaterialDialog(requireContext()).show {
//                title(R.string.title)
//                message(text = msg)
//                cornerRadius(8.0f)
//                positiveButton(R.string.doneM)
//                positiveButton {
//                    mStartActivity<SalaryCaptchaActivity>(requireContext())
//                }
//                cancelOnTouchOutside(false)
//            }
//        }
        mStartActivity<SalaryCaptchaActivity>(requireContext())
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showEmpty() {
        loadService.showCallback(EmptyCallBack::class.java)
    }

    private val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
                    StateType.SUCCESS -> {
                        showSuccess()
                    }
                    StateType.LOADING -> showLoading()
                    StateType.ERROR -> showTip(it.message)
                    StateType.NETWORK_ERROR -> showError(getString(R.string.error_message))
                    StateType.TIP -> showTip(it.message)
                    StateType.EMPTY -> showEmpty()
                    StateType.NOT_LOGIN -> {
                        showNotLoginTip(it.message)
                    }
                    StateType.TEMP_OUT_TIME -> {
                        showTmpOutTimeTip(it.message)
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