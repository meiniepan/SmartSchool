package com.xiaoneng.ss.module.circular.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.module.circular.model.NoticeDetailBean
import com.xiaoneng.ss.module.circular.model.NoticeResponse
import com.xiaoneng.ss.module.circular.repository.CircularRepository
import com.xiaoneng.ss.network.initiateRequest

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:09
 */
class CircularViewModel : BaseViewModel<CircularRepository>() {

    val mNoticeData: MutableLiveData<NoticeResponse> = MutableLiveData()
    val mNoticeDetail: MutableLiveData<NoticeDetailBean> = MutableLiveData()

    fun getNoticeList(page: String = "", pagenum: String = "") {
        initiateRequest(
            { mNoticeData.value = mRepository.getNoticeList(page, pagenum) },
            loadState
        )
    }

    fun getNoticeDetail(id: String ) {
        initiateRequest(
            { mNoticeDetail.value = mRepository.getNoticeDetail(id) },
            loadState
        )
    }

}