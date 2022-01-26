package com.xiaoneng.ss.base.view


import android.Manifest
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.kingja.loadsir.callback.SuccessCallback
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.common.callback.EmptyCallBack
import com.xiaoneng.ss.common.callback.LoadingCallBack
import com.xiaoneng.ss.common.permission.PermissionResult
import com.xiaoneng.ss.common.permission.Permissions
import com.xiaoneng.ss.common.state.State
import com.xiaoneng.ss.common.state.StateType
import com.xiaoneng.ss.common.utils.CommonUtil
import com.xiaoneng.ss.common.utils.mAlert
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mainLogin
import com.xiaoneng.ss.module.school.view.SalaryCaptchaActivity
import pub.devrel.easypermissions.AppSettingsDialog


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/22
 * Time: 16:30
 */
abstract class BaseLifeCycleActivity<VM : BaseViewModel<*>> : BaseActivity() {
    protected lateinit var mViewModel: VM
     val mPermissionsCam = arrayOf(
        Manifest.permission.CAMERA,
    )
     val mPermissionsAudio = arrayOf(
        Manifest.permission.RECORD_AUDIO,
    )

    override fun initView() {

        mViewModel = ViewModelProvider(this).get(CommonUtil.getClass(this))

        mViewModel.loadState.observe(this, observer)

        // 初始化View的Observer
        initDataObserver()
    }

    abstract fun initDataObserver()


    open fun showLoading() {
        loadService.showCallback(LoadingCallBack::class.java)
    }

    open fun showSuccess() {
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showError(msg: String) {
        loadService.showCallback(SuccessCallback::class.java)
//        loadService.showCallback(ErrorCallBack::class.java)
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//            loadService.setCallBack(ErrorCallBack::class.java, Transport { context, view ->
//                val mTvEmpty = view.findViewById(R.id.tv_error) as TextView
//                mTvEmpty.text = msg
//            })
        }

    }

    open fun showTip(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            mAlert(msg){

            }
        }
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showNotLoginTip(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            mAlert(msg){
                mainLogin(this@BaseLifeCycleActivity)
            }

        }
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showTmpOutTimeTip(msg: String = "请重新验证") {
//        if (!TextUtils.isEmpty(msg)) {
//            MaterialDialog(this).show {
//                title(R.string.title)
//                message(text = msg)
//                cornerRadius(8.0f)
//                positiveButton(R.string.doneM)
//                positiveButton {
//                    mStartActivity<SalaryCaptchaActivity>(this@BaseLifeCycleActivity)
//                }
//                cancelOnTouchOutside(false)
//            }
//        }
        mStartActivity<SalaryCaptchaActivity>(this@BaseLifeCycleActivity)
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showEmpty() {
        loadService.showCallback(EmptyCallBack::class.java)
    }

     fun initPermission(permissions: Array<String>) {
        Permissions(this).request(*permissions).observe(
            this, Observer {
                when (it) {

                    // 进入设置界面申请权限
                    is PermissionResult.Rationale -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有相关权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Deny -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有相关权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                    }
                }
            }
        )

    }

    /**
     * 分发应用状态
     */
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
}