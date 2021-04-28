package com.xiaoneng.ss.module.circular.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.module.circular.model.NoticeDetailBean
import com.xiaoneng.ss.module.circular.model.NoticeResponse
import com.xiaoneng.ss.module.circular.model.ScheduleBean
import com.xiaoneng.ss.module.circular.repository.CircularRepository
import com.xiaoneng.ss.network.initiateRequest

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:09
 */
class CircularViewModel : BaseViewModel<CircularRepository>() {

    val mNoticeData: MutableLiveData<NoticeResponse> = MutableLiveData()
    val mNoticerData: MutableLiveData<Any> = MutableLiveData()
    val mNoticeDetail: MutableLiveData<NoticeDetailBean> = MutableLiveData()
    val mReadData: MutableLiveData<Any> = MutableLiveData()
    val mScheduleData: MutableLiveData<Any> = MutableLiveData()
    val mScheduleMonthData: MutableLiveData<Any> = MutableLiveData()
    val mAddScheduleData: MutableLiveData<Any> = MutableLiveData()

    fun getNoticeList(lastid: String? = null, pagenum: String? = null,type:String? = null) {
        initiateRequest(
            { mNoticeData.value = mRepository.getNoticeList(lastid, pagenum,type) },
            loadState
        )
    }

    fun getNoticerList(lastid: String? = null, type:String? = null, status:String? = null, noticeid:String? = null) {
        initiateRequest(
            { mNoticerData.value = mRepository.getNoticerList(
                lastid,
                type,
                status,
                noticeid) },
            loadState
        )
    }

    fun getNoticeDetail(id: String) {
        initiateRequest(
            { mNoticeDetail.value = mRepository.getNoticeDetail(id) },
            loadState
        )
    }

    fun read(id: String, status: String? = null,received:String? = null) {
        initiateRequest(
            { mReadData.value = mRepository.read(id, status,received) },
            loadState
        )
    }

    fun readAll() {
        initiateRequest(
            { mReadData.value = mRepository.readAll() },
            loadState
        )
    }

    fun querySchedule(day: String, months: String? = null) {
        initiateRequest(
            { mScheduleData.value = mRepository.querySchedule(day, months) },
            loadState
        )
    }

    fun queryScheduleMonth(day: String? = null, months: String? = null) {
        initiateRequest(
            { mScheduleMonthData.value = mRepository.querySchedule(day, months) },
            loadState
        )
    }

    fun addSchedule(bean: ScheduleBean?) {
        initiateRequest(
            { mAddScheduleData.value = mRepository.addSchedule(bean) },
            loadState
        )
    }

    fun modifySchedule(bean: ScheduleBean?) {
        initiateRequest(
            { mAddScheduleData.value = mRepository.modifySchedule(bean) },
            loadState
        )
    }

    fun deleteSchedule(bean: ScheduleBean?) {
        initiateRequest(
            { mAddScheduleData.value = mRepository.deleteSchedule(bean) },
            loadState
        )
    }

}