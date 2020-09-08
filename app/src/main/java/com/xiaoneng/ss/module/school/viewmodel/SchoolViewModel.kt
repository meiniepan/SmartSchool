package com.xiaoneng.ss.module.school.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.model.StudentResp
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.repository.SchoolRepository
import com.xiaoneng.ss.network.initiateRequest

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:09
 */
class SchoolViewModel : BaseViewModel<SchoolRepository>() {

    val mTaskListData: MutableLiveData<TaskResponse> = MutableLiveData()
    val mPerformanceData: MutableLiveData<PerformanceResponse> = MutableLiveData()
    val mTimetableData: MutableLiveData<TimetableResponse> = MutableLiveData()
    val mTimetableDataT: MutableLiveData<TimetableResponse> = MutableLiveData()
    val mAttendanceMasterData: MutableLiveData<AttendanceResponse> = MutableLiveData()
    val mAttendanceStuData: MutableLiveData<AttendanceResponse> = MutableLiveData()
    val mStudentData: MutableLiveData<StudentResp> = MutableLiveData()
    val mDepartmentsData: MutableLiveData<Any> = MutableLiveData()
    val mAddAttendanceData: MutableLiveData<Any> = MutableLiveData()
    val mAddTaskData: MutableLiveData<Any> = MutableLiveData()

    fun getTaskList(pagenum: String = "", status: String = "") {
        initiateRequest(
            { mTaskListData.value = mRepository.getTaskList(pagenum, status) },
            loadState
        )
    }

    fun getTimetable(classid: String = "",time: String = "") {
        initiateRequest(
            { mTimetableData.value = mRepository.getTimetable(classid,time) },
            loadState
        )
    }

    fun getTimetableT() {
        initiateRequest(
            { mTimetableDataT.value = mRepository.getTimetableT() },
            loadState
        )
    }

    fun getPerformance(testname: String, crid: String = "") {
        initiateRequest(
            { mPerformanceData.value = mRepository.getPerformance(testname, crid) },
            loadState
        )
    }

    fun getAttendanceMaster(classid: String = "", atttime: String = "") {
        initiateRequest(
            { mAttendanceMasterData.value = mRepository.getAttendance(classid, atttime) },
            loadState
        )
    }

    fun getAttendanceStu(classid: String = "", atttime: String = "") {
        initiateRequest(
            { mAttendanceStuData.value = mRepository.getAttendance(classid, atttime) },
            loadState
        )
    }

    fun queryStudent(key: String) {
        initiateRequest(
            { mStudentData.value = mRepository.queryStudent(key) },
            loadState
        )
    }

    fun queryDepartments() {
        initiateRequest(
            { mDepartmentsData.value = mRepository.queryDepartments() },
            loadState
        )
    }

    fun addAttendanceByMaster() {
        initiateRequest(
            { mAddAttendanceData.value = mRepository.addAttendanceByMaster() },
            loadState
        )
    }

    fun addTask(bean: TaskBean) {
        initiateRequest(
            { mAddTaskData.value = mRepository.addTask(bean) },
            loadState
        )
    }

}