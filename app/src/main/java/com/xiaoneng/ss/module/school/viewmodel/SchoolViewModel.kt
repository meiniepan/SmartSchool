package com.xiaoneng.ss.module.school.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.model.StudentResp
import com.xiaoneng.ss.module.school.model.AttendanceResponse
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
    val mTimetableDataT: MutableLiveData<TimetableResponse> = MutableLiveData()
    val mAttendanceData: MutableLiveData<AttendanceResponse> = MutableLiveData()
    val mStudentData: MutableLiveData<StudentResp> = MutableLiveData()
    val mDepartmentsData: MutableLiveData<Any> = MutableLiveData()

    fun getTaskList(pagenum:String = "",status:String = "") {
        initiateRequest(
            { mTaskListData.value = mRepository.getTaskList( pagenum,status) },
            loadState
        )
    }

    fun getTimetable(classid:String = "") {
        initiateRequest(
            { mTimetableData.value = mRepository.getTimetable( classid) },
            loadState
        )
    }

    fun getTimetableT() {
        initiateRequest(
            { mTimetableDataT.value = mRepository.getTimetableT() },
            loadState
        )
    }

    fun getPerformance(testname:String,crid:String = "") {
        initiateRequest(
            { mPerformanceData.value = mRepository.getPerformance(testname, crid) },
            loadState
        )
    }

    fun getAttendance(classid:String = "") {
        initiateRequest(
            { mAttendanceData.value = mRepository.getAttendance( classid) },
            loadState
        )
    }

    fun queryStudent(key:String) {
        initiateRequest(
            { mStudentData.value = mRepository.queryStudent( key) },
            loadState
        )
    }

    fun queryDepartments() {
        initiateRequest(
            { mDepartmentsData.value = mRepository.queryDepartments() },
            loadState
        )
    }

}