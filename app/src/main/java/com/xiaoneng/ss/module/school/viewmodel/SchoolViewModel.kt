package com.xiaoneng.ss.module.school.viewmodel

import androidx.lifecycle.MutableLiveData
import com.xiaoneng.ss.base.viewmodel.BaseViewModel
import com.xiaoneng.ss.model.StsTokenResp
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
    val mAddMoralScoreData: MutableLiveData<Any> = MutableLiveData()
    val mTaskListData: MutableLiveData<TaskResponse> = MutableLiveData()
    val mAchievementData: MutableLiveData<AchievementResponse> = MutableLiveData()
    val mArchivesData: MutableLiveData<Any> = MutableLiveData()
    val mTestCourseData: MutableLiveData<Any> = MutableLiveData()
    val mTimetableData: MutableLiveData<TimetableResponse> = MutableLiveData()
    val mAttTimetableData: MutableLiveData<Any> = MutableLiveData()
    val mTimetableDataT: MutableLiveData<TimetableResponse> = MutableLiveData()
    val mAttendanceSchoolData: MutableLiveData<Any> = MutableLiveData()
    val mAttendanceInfoData: MutableLiveData<Any> = MutableLiveData()
    val mAttendanceStuData: MutableLiveData<Any> = MutableLiveData()
    val mAttendanceTeaData: MutableLiveData<Any> = MutableLiveData()
    val mAttendanceQueryData: MutableLiveData<Any> = MutableLiveData()
    val mStudentData: MutableLiveData<Any> = MutableLiveData()
    val mInvolveStudentData: MutableLiveData<Any> = MutableLiveData()
    val mDepartmentsData: MutableLiveData<Any> = MutableLiveData()
    val mDepartmentPersonData: MutableLiveData<Any> = MutableLiveData()
    val mAddTaskData: MutableLiveData<Any> = MutableLiveData()
    val mDeleteAttendanceData: MutableLiveData<Any> = MutableLiveData()
    val mStsData: MutableLiveData<StsTokenResp> = MutableLiveData()
    val mBaseData: MutableLiveData<Any> = MutableLiveData()
    val mMoralTypeData: MutableLiveData<Any> = MutableLiveData()
    val mQuantizeListCommonData: MutableLiveData<Any> = MutableLiveData()
    val mQuantizeListSpecialData: MutableLiveData<Any> = MutableLiveData()
    val mMoralTypeInfoData: MutableLiveData<Any> = MutableLiveData()
    val mRepairTypeData: MutableLiveData<Any> = MutableLiveData()
    val mDeviceTypeData: MutableLiveData<Any> = MutableLiveData()
    val mPriCloudData: MutableLiveData<Any> = MutableLiveData()
    val mAuthUsersData: MutableLiveData<Any> = MutableLiveData()
    val mAddFileData: MutableLiveData<Any> = MutableLiveData()
    val mModifyFileData: MutableLiveData<Any> = MutableLiveData()
    val mCopyCloudData: MutableLiveData<Any> = MutableLiveData()
    val mDelCloudFileData: MutableLiveData<Any> = MutableLiveData()
    val mDelCloudFolderData: MutableLiveData<Any> = MutableLiveData()
    val mCancelFolderAuthData: MutableLiveData<Any> = MutableLiveData()
    val mPriCloudFilesData: MutableLiveData<Any> = MutableLiveData()
    val mPubCloudData: MutableLiveData<Any> = MutableLiveData()
    val mPubCloudFilesData: MutableLiveData<Any> = MutableLiveData()
    val mNewFolderData: MutableLiveData<Any> = MutableLiveData()
    val mBookRoomData: MutableLiveData<Any> = MutableLiveData()
    val mBookMonthData: MutableLiveData<Any> = MutableLiveData()
    val mAddBookSiteData: MutableLiveData<Any> = MutableLiveData()
    val mModifyBookSiteData: MutableLiveData<Any> = MutableLiveData()
    val mModifyRepairData: MutableLiveData<Any> = MutableLiveData()
    val mRemindRepairData: MutableLiveData<Any> = MutableLiveData()
    val mSalaryDetailData: MutableLiveData<Any> = MutableLiveData()
    val mSalaryReadData: MutableLiveData<Any> = MutableLiveData()
    val mSalaryListData: MutableLiveData<Any> = MutableLiveData()
    val mTmpTokenData: MutableLiveData<Any> = MutableLiveData()
    val mTaskDetailData: MutableLiveData<Any> = MutableLiveData()
    val mRefuseData: MutableLiveData<Any> = MutableLiveData()
    val mModifyTaskStatusData: MutableLiveData<Any> = MutableLiveData()

    fun getTaskList(
        lastid: String? = null, pagenum: String? = null, status: String? = null,
        stime: String? = null,
        etime: String? = null
    ) {
        initiateRequest(
            {
                mTaskListData.value = mRepository.getTaskList(
                    pagenum, status, lastid = lastid,
                    stime = stime,
                    etime = etime
                )
            },
            loadState
        )
    }

    fun getPublishTaskList(
        lastid: String? = null,
        pagenum: String? = null,
        status: String? = null,
        stime: String? = null,
        etime: String? = null
    ) {
        initiateRequest(
            {
                mTaskListData.value =
                    mRepository.getPublishTaskList(
                        pagenum, status, lastid = lastid,
                        stime = stime,
                        etime = etime
                    )
            },
            loadState
        )
    }

    fun getTimetable(classid: String? = null, time: String? = null) {
        initiateRequest(
            { mTimetableData.value = mRepository.getTimetable(classid, time) },
            loadState
        )
    }

    fun getAttTimetable(time: String? = null, uId: String? = null) {
        initiateRequest(
            { mAttTimetableData.value = mRepository.getAttTimetable(time, uId) },
            loadState
        )
    }

    fun getTimetableT() {
        initiateRequest(
            { mTimetableDataT.value = mRepository.getTimetableT() },
            loadState
        )
    }

    fun getAchievement(
        testname: String? = null,
        crsid: String? = null,
        classid: String? = null,
        lastid: String? = null
    ) {
        initiateRequest(
            {
                mAchievementData.value =
                    mRepository.getAchievement(testname, crsid, classid, lastid = lastid)
            },
            loadState
        )
    }

    fun getArchives() {
        initiateRequest(
            {
                mArchivesData.value =
                    mRepository.getArchives()
            },
            loadState
        )
    }

    fun getTestCourse() {
        initiateRequest(
            { mTestCourseData.value = mRepository.getTestCourse() },
            loadState
        )
    }

    fun getAttendanceSchool(classid: String? = null, time: String? = null) {
        initiateRequest(
            { mAttendanceSchoolData.value = mRepository.getAttendance(classid, time) },
            loadState
        )
    }


    fun getAttendanceTea(
        classid: String? = null, courseId: String? = null, time: String? = null,
        keyword: String? = null
    ) {
        initiateRequest(
            {
                if (keyword == null) {
                    mAttendanceTeaData.value =
                        mRepository.getAttendance(
                            classid = classid,
                            courseId = courseId,
                            atttime = time,
                            keyword = keyword
                        )
                } else {
                    mAttendanceQueryData.value =
                        mRepository.getAttendance(
                            classid = classid,
                            courseId = courseId,
                            atttime = time,
                            keyword = keyword
                        )
                }

            },
            loadState
        )
    }

    fun getAttendanceStu(classid: String? = null, time: String? = null) {
        initiateRequest(
            { mAttendanceStuData.value = mRepository.getAttendanceStu(classid, time) },
            loadState
        )
    }

    fun getAttendanceInfo(id: String) {
        initiateRequest(
            { mAttendanceInfoData.value = mRepository.getAttendanceInfo(id) },
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

    fun listByDepartment(id: String? = null, realName: String? = null) {
        initiateRequest(
            { mDepartmentPersonData.value = mRepository.listByDepartment(id, realName) },
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

    fun addMoralScore(bean: QuantizeBody) {
        initiateRequest(
            { mAddMoralScoreData.value = mRepository.addMoralScore(bean) },
            loadState
        )
    }

    fun addMoralScoreSpecial(bean: QuantizeBody) {
        initiateRequest(
            { mAddMoralScoreData.value = mRepository.addMoralScoreSpecial(bean) },
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

    fun getClassesByTea(isall: String?=null) {
        initiateRequest(
            { mBaseData.value = mRepository.getClassesByTea(isall) },
            loadState
        )
    }

    fun getQuantizeListCommon(typeid: String?) {
        initiateRequest(
            { mQuantizeListCommonData.value = mRepository.getQuantizeListCommon(typeid) },
            loadState
        )
    }

    fun getQuantizeListSpecial() {
        initiateRequest(
            { mQuantizeListSpecialData.value = mRepository.getQuantizeListSpecial() },
            loadState
        )
    }

    fun getStudentsByClass(classId: String? = null, realName: String? = null, isall: String? = null) {
        initiateRequest(
            { mStudentData.value = mRepository.getStudentsByClass(classId, realName,isall=isall) },
            loadState
        )
    }

    fun getTaskInfo(id: String, type: String? = null) {
        initiateRequest(
            { mTaskDetailData.value = mRepository.getTaskInfo(id, type) },
            loadState
        )
    }

    fun modifyTaskInfo(body: TaskLogRequest) {
        initiateRequest(
            { mBaseData.value = mRepository.modifyTaskInfo(body) },
            loadState
        )
    }

    fun refuseTask(body: TaskLogRequest) {
        initiateRequest(
            { mRefuseData.value = mRepository.refuseTask(body) },
            loadState
        )
    }

    fun modifyTaskStatus(body: TaskBean) {
        initiateRequest(
            { mModifyTaskStatusData.value = mRepository.modifyTaskStatus(body) },
            loadState
        )
    }

    fun delTaskDraft(id: String) {
        initiateRequest(
            { mBaseData.value = mRepository.delTaskDraft(id) },
            loadState
        )
    }

    fun getSalaryDetail(id: String?) {
        initiateRequest(
            { mSalaryDetailData.value = mRepository.getSalaryDetail(id) },
            loadState
        )
    }

    fun readSalaryDetail(id: String?) {
        initiateRequest(
            { mSalaryReadData.value = mRepository.readSalaryDetail(id) },
            loadState
        )
    }

    fun getSalaryList(type: String? = null, lastid: String? = null) {
        initiateRequest(
            { mSalaryListData.value = mRepository.getSalaryList(type, lastid) },
            loadState
        )
    }

    fun getSalaryCaptcha() {
        initiateRequest(
            { mBaseData.value = mRepository.getSalaryCaptcha() },
            loadState
        )
    }

    fun getTmpToken(code: String?) {
        initiateRequest(
            { mTmpTokenData.value = mRepository.getTmpToken(code) },
            loadState
        )
    }

    fun addRepair(body: RepairBody) {
        initiateRequest(
            { mBaseData.value = mRepository.addRepair(body) },
            loadState
        )
    }

    fun addBookSite(body: AddBookSiteBody) {
        initiateRequest(
            { mAddBookSiteData.value = mRepository.addBookSite(body) },
            loadState
        )
    }

    fun getPropertyRecord(lastid: String? = null, status: String? = null, type: String? = null) {
        initiateRequest(
            { mBaseData.value = mRepository.getPropertyRecord(lastid, status, type) },
            loadState
        )
    }

    fun getCanBookRooms(bookTime: String) {
        initiateRequest(
            { mBaseData.value = mRepository.getCanBookRooms(bookTime) },
            loadState
        )
    }

    fun getBookList(start: String, end: String? = null) {
        initiateRequest(
            { mBaseData.value = mRepository.getBookList(start, end) },
            loadState
        )
    }

    fun remindUnread(id: String? = null) {
        initiateRequest(
            { mBaseData.value = mRepository.remindUnread(id) },
            loadState
        )
    }

    fun getPriCloudList(folderid: String? = null) {
        initiateRequest(
            { mPriCloudData.value = mRepository.getPriCloudList(folderid) },
            loadState
        )
    }

    fun getMoralTypeList() {
        initiateRequest(
            { mMoralTypeData.value = mRepository.getMoralTypeList() },
            loadState
        )
    }

    fun getMoralTypeInfo(id: String? = null) {
        initiateRequest(
            { mMoralTypeInfoData.value = mRepository.getMoralTypeInfo(id) },
            loadState
        )
    }

    fun getAuthUsers(folderid: String? = null) {
        initiateRequest(
            { mAuthUsersData.value = mRepository.getAuthUsers(folderid) },
            loadState
        )
    }

    fun copyCloudFile(fileid: String? = null, folderid: String? = null) {
        initiateRequest(
            {
                mCopyCloudData.value =
                    mRepository.copyCloudFile(fileid = fileid, folderid = folderid)
            },
            loadState
        )
    }

    fun moveCloudFile(fileid: String? = null, folderid: String? = null) {
        initiateRequest(
            {
                mCopyCloudData.value =
                    mRepository.moveCloudFile(fileid = fileid, folderid = folderid)
            },
            loadState
        )
    }

    fun delMyCloudFile(fileid: String? = null) {
        initiateRequest(
            {
                mDelCloudFileData.value =
                    mRepository.delMyCloudFile(fileid = fileid)
            },
            loadState
        )
    }

    fun delCloudFolder(folderid: String? = null) {
        initiateRequest(
            {
                mDelCloudFolderData.value =
                    mRepository.delCloudFolder(folderid = folderid)
            },
            loadState
        )
    }

    fun cancelFolderAuth(folderid: String? = null) {
        initiateRequest(
            {
                mCancelFolderAuthData.value =
                    mRepository.cancelFolderAuth(folderid = folderid)
            },
            loadState
        )
    }

    fun getPriCloudFiles(folderid: String? = null) {
        initiateRequest(
            { mPriCloudFilesData.value = mRepository.getPriCloudFiles(folderid) },
            loadState
        )
    }

    fun addFile(fileBean: DiskFileBean) {
        initiateRequest(
            { mAddFileData.value = mRepository.addFile(fileBean) },
            loadState
        )
    }

    fun modifyFile(fileBean: FolderModifyBean) {
        initiateRequest(
            { mModifyFileData.value = mRepository.modifyFile(fileBean) },
            loadState
        )
    }

    fun modifyFolder(fileBean: FolderModifyBean) {
        initiateRequest(
            { mModifyFileData.value = mRepository.modifyFolder(fileBean) },
            loadState
        )
    }

    fun getPubCloudList(folderid: String? = null) {
        initiateRequest(
            { mPubCloudData.value = mRepository.getPubCloudList(folderid) },
            loadState
        )
    }

    fun getPubCloudFiles(folderid: String? = null) {
        initiateRequest(
            { mPubCloudFilesData.value = mRepository.getPubCloudFiles(folderid) },
            loadState
        )
    }

    fun newFileFolder(parentid: String? = null, foldername: String? = null) {
        initiateRequest(
            { mNewFolderData.value = mRepository.newFileFolder(parentid, foldername) },
            loadState
        )
    }

    fun setFileFolder(parentid: String? = null, folderid: String? = null, involve: String? = null, sendlabel: String? = null) {
        initiateRequest(
            { mBaseData.value = mRepository.setFileFolder(parentid, folderid, involve,sendlabel) },
            loadState
        )
    }

    fun getBookRoomList(start: String, end: String? = null) {
        initiateRequest(
            { mBookRoomData.value = mRepository.getBookRoomList(start, end) },
            loadState
        )
    }

    fun getBookListMonth(month: String) {
        initiateRequest(
            { mBookMonthData.value = mRepository.getBookListMonth(month) },
            loadState
        )
    }

    fun getBookSiteRecord(lastid: String? = null) {
        initiateRequest(
            { mBaseData.value = mRepository.getBookSiteRecord(lastid) },
            loadState
        )
    }

    fun getPropertyType(typeid: String? = null, lastid: String? = null) {
        initiateRequest(
            { mRepairTypeData.value = mRepository.getPropertyType(typeid = typeid) },
            loadState
        )
    }

    fun getDeviceType(typeid: String? = null) {
        initiateRequest(
            { mDeviceTypeData.value = mRepository.getDeviceType(typeid) },
            loadState
        )
    }

    fun modifyBookSite(body: AddBookSiteBody) {
        initiateRequest(
            { mModifyBookSiteData.value = mRepository.modifyBookSite(body) },
            loadState
        )
    }

    fun modifyRepair(body: RepairBody) {
        initiateRequest(
            { mModifyRepairData.value = mRepository.modifyRepair(body) },
            loadState
        )
    }

    fun remindRepair(id: String) {
        initiateRequest(
            { mRemindRepairData.value = mRepository.remindRepair(id) },
            loadState
        )
    }


}