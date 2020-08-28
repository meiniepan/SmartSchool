package com.xiaoneng.ss.module.mine.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.common.utils.OssUtils
import com.xiaoneng.ss.common.utils.mDownloadFile
import com.xiaoneng.ss.common.utils.mReadTxtFile
import com.xiaoneng.ss.module.circular.model.StsTokenBean
import com.xiaoneng.ss.module.mine.repository.MineRepository
import com.xiaoneng.ss.network.initiateRequest

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:09
 */
class MineViewModel : BaseViewModel<MineRepository>() {
    val mLogoutData: MutableLiveData<Any> = MutableLiveData()
    fun logout() {
        initiateRequest(
            { mLogoutData.value = mRepository.logout() },
            loadState
        )
    }

    fun upload(context: Context, bean: StsTokenBean) {
        uploadFile(context, bean)
//        initiateRequest(
//            { uploadFile(context,bean) },
//            loadState
//        )
//        Thread {
//            uploadFile(context,bean)
//        }.start()

    }

    fun uploadFile(context: Context, bean: StsTokenBean) {
        OssUtils.asyncUploadFile(context, bean, mReadTxtFile(context))
    }

    fun downloadFile(context: Context, bean: StsTokenBean) {
        Thread {
        OssUtils.downloadFile(context, bean, "Burning", mDownloadFile(context))
        }.start()
    }
}