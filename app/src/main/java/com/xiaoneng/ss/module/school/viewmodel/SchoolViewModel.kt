package com.xiaoneng.ss.module.school.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.module.school.model.PerformanceResponse
import com.xiaoneng.ss.module.school.model.TaskResponse
import com.xiaoneng.ss.module.school.model.TimetableResponse
import com.xiaoneng.ss.module.school.repository.SchoolRepository
import com.xiaoneng.ss.network.initiateRequest

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:09
 */
class SchoolViewModel : BaseViewModel<SchoolRepository>() {

    val mTaskListData: MutableLiveData<TaskResponse> = MutableLiveData()
    val mPerformanceData: MutableLiveData<PerformanceResponse> = MutableLiveData()
    val mTimetableData: MutableLiveData<TimetableResponse> = MutableLiveData()

    fun getTaskList(pagenum:String = "") {
        initiateRequest(
            { mTaskListData.value = mRepository.getTaskList( pagenum) },
            loadState
        )
    }

    fun getTimetable(pagenum:String = "") {
        initiateRequest(
            { mTimetableData.value = mRepository.getTimetable( pagenum) },
            loadState
        )
    }

    fun getPerformance(crid:String = "") {
        initiateRequest(
            { mPerformanceData.value = mRepository.getPerformance( crid) },
            loadState
        )
    }

}