package com.xiaoneng.ss.module.school.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.model.StsTokenResp
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

    val mAddAttendanceData: MutableLiveData<Any> = MutableLiveData()
    val mTaskListData: MutableLiveData<TaskResponse> = MutableLiveData()
    val mPerformanceData: MutableLiveData<PerformanceResponse> = MutableLiveData()
    val mTestCourseData: MutableLiveData<Any> = MutableLiveData()
    val mTimetableData: MutableLiveData<TimetableResponse> = MutableLiveData()
    val mAttTimetableData: MutableLiveData<Any> = MutableLiveData()
    val mTimetableDataT: MutableLiveData<TimetableResponse> = MutableLiveData()
    val mAttendanceSchoolData: MutableLiveData<Any> = MutableLiveData()
    val mAttendanceMasterData: MutableLiveData<Any> = MutableLiveData()
    val mAttendanceStuData: MutableLiveData<Any> = MutableLiveData()
    val mAttendanceTeaData: MutableLiveData<Any> = MutableLiveData()
    val mStudentData: MutableLiveData<StudentResp> = MutableLiveData()
    val mDepartmentsData: MutableLiveData<Any> = MutableLiveData()
    val mAddTaskData: MutableLiveData<Any> = MutableLiveData()
    val mDeleteAttendanceData: MutableLiveData<Any> = MutableLiveData()
    val mStsData: MutableLiveData<StsTokenResp> = MutableLiveData()
    val mBaseData: MutableLiveData<Any> = MutableLiveData()
    val mRefuseData: MutableLiveData<Any> = MutableLiveData()
    val mModifyTaskStatusData: MutableLiveData<Any> = MutableLiveData()

    fun getTaskList(pagenum: String = "", status: String = "") {
        initiateRequest(
            { mTaskListData.value = mRepository.getTaskList(pagenum, status) },
            loadState
        )
    }

    fun getPublishTaskList(pagenum: String = "", status: String = "") {
        initiateRequest(
            { mTaskListData.value = mRepository.getPublishTaskList(pagenum, status) },
            loadState
        )
    }

    fun getTimetable(classid: String = "", time: String = "") {
        initiateRequest(
            { mTimetableData.value = mRepository.getTimetable(classid, time) },
            loadState
        )
    }

    fun getAttTimetable(time: String = "") {
        initiateRequest(
            { mAttTimetableData.value = mRepository.getAttTimetable(time) },
            loadState
        )
    }

    fun getTimetableT() {
        initiateRequest(
            { mTimetableDataT.value = mRepository.getTimetable() },
            loadState
        )
    }

    fun getPerformance(testname: String = "", crid: String = "", classid: String = "") {
        initiateRequest(
            { mPerformanceData.value = mRepository.getPerformance(testname, crid, classid) },
            loadState
        )
    }

    fun getTestCourse() {
        initiateRequest(
            { mTestCourseData.value = mRepository.getTestCourse() },
            loadState
        )
    }

    fun getAttendanceSchool(classid: String = "", time: String = "") {
        initiateRequest(
            { mAttendanceSchoolData.value = mRepository.getAttendance(classid, time) },
            loadState
        )
    }


    fun getAttendanceTea(classid: String = "", time: String = "", courseId: String = "") {
        initiateRequest(
            { mAttendanceTeaData.value = mRepository.getAttendance(classid, time, courseId) },
            loadState
        )
    }

    fun getAttendanceStu(classid: String = "", time: String = "") {
        initiateRequest(
            { mAttendanceStuData.value = mRepository.getAttendance(classid, time) },
            loadState
        )
    }

    fun getAttendanceByStuAdmin(classid: String = "", time: String = "", courseId: String) {
        initiateRequest(
            { mAttendanceStuData.value = mRepository.getAttendance(classid, time, courseId) },
            loadState
        )
    }

    fun queryStudent(keyword: String) {
        initiateRequest(
            { mStudentData.value = mRepository.queryStudent(keyword) },
            loadState
        )
    }

    fun queryDepartments() {
        initiateRequest(
            { mDepartmentsData.value = mRepository.queryDepartments() },
            loadState
        )
    }


    fun addTask(bean: TaskBean) {
        initiateRequest(
            { mAddTaskData.value = mRepository.addTask(bean) },
            loadState
        )
    }

    fun addAttendance(bean: LeaveBean) {
        initiateRequest(
            { mAddAttendanceData.value = mRepository.addAttendance(bean) },
            loadState
        )
    }

    fun deleteAttendance(id: String) {
        initiateRequest(
            { mDeleteAttendanceData.value = mRepository.deleteAttendance(id) },
            loadState
        )
    }

    fun getSts() {
        initiateRequest(
            { mStsData.value = mRepository.getSts() },
            loadState
        )
    }

    fun getClassesByTea() {
        initiateRequest(
            { mBaseData.value = mRepository.getClassesByTea() },
            loadState
        )
    }

    fun getStudentsByClass(classId:String) {
        initiateRequest(
            { mBaseData.value = mRepository.getStudentsByClass(classId) },
            loadState
        )
    }

    fun getTaskInfo(id:String) {
        initiateRequest(
            { mBaseData.value = mRepository.getTaskInfo(id) },
            loadState
        )
    }
    fun modifyTaskInfo(body:TaskLogRequest) {
        initiateRequest(
            { mBaseData.value = mRepository.modifyTaskInfo(body) },
            loadState
        )
    }

    fun refuseTask(body:TaskLogRequest) {
        initiateRequest(
            { mRefuseData.value = mRepository.refuseTask(body) },
            loadState
        )
    }

    fun modifyTaskStatus(body:TaskDetailBean) {
        initiateRequest(
            { mModifyTaskStatusData.value = mRepository.modifyTaskStatus(body) },
            loadState
        )
    }

}