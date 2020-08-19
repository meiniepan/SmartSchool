package com.xiaoneng.ss.module.circular.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.module.circular.model.NoticeResponse
import com.xiaoneng.ss.module.circular.repository.SchoolRepository
import com.xiaoneng.ss.network.initiateRequest

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:09
 */
class SchoolViewModel : BaseViewModel<SchoolRepository>() {

    val mNoticeData: MutableLiveData<NoticeResponse> = MutableLiveData()

    fun getNoticeList(page:String = "",pagenum:String = "") {
        initiateRequest(
            { mNoticeData.value = mRepository.getNoticeList( page,pagenum) },
            loadState
        )
    }

}