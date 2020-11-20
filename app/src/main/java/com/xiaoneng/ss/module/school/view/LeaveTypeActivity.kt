package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.school.adapter.AttCourseAdapter
import com.xiaoneng.ss.module.school.model.AttCourseBean
import com.xiaoneng.ss.module.school.model.AttInfoBean
import com.xiaoneng.ss.module.school.model.AttendanceBean
import com.xiaoneng.ss.module.school.model.LeaveBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_sick_leave.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class LeaveTypeActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: AttCourseAdapter
    var mData: ArrayList<AttCourseBean> = ArrayList()
    var chosenDay = DateUtil.formatDateCustomDay()
    var chosenDayNet = DateUtil.formatDateCustomDay()
    private var fileName: String? = ""
    private var avatarPath: String? = ""
    private var fileValue: String? = ""
    var isDownLoad: Boolean = false
    var uId:String? = null
    var uType:String? = null
    var delNum = 0
    var resDelNum = 0
    var leaveType: String = "0"//1事假  2病假
    var leaveStr: String = "0"//1事假  2病假
    var bean: AttendanceBean? = null
    override fun getLayoutId(): Int = R.layout.activity_sick_leave


    override fun initView() {
        super.initView()
        tvTimeToday.text = "您的请假时间是" + DateUtil.formatTitleToday()
        leaveType = intent.getStringExtra(Constant.LEAVE_TYPE)!!
        bean = intent.getParcelableExtra(Constant.DATA)

        if (leaveType == "1") {
            leaveStr = "事假"
            llRemarkLeave.visibility = View.VISIBLE
            llSick.visibility = View.GONE
        } else if (leaveType == "2") {
            leaveStr = "病假"
            llRemarkLeave.visibility = View.GONE
            llSick.visibility = View.VISIBLE
        }
        tvConfirm.setOnClickListener {
            doConfirm()
        }
        llItem8ApplyLeave.setOnClickListener {
            mStartForResult<ChooseCourseToLeaveActivity>(this, Constant.REQUEST_CODE_COURSE) {
                bean?.let {
                    putExtra(Constant.DATA, bean)
                    putExtra(Constant.LEAVE_TYPE, leaveStr)
                }
            }
        }
        ivAddPic.apply {
            setOnClickListener {
                choosePic()
            }
        }
        initAdapter()
    }

    override fun initData() {
        super.initData()
        var mid = ""
        bean?.attlists?.let {
            it.forEach {
                if (it.attendances == leaveStr) {
                    mid = it.id ?: ""
                }
            }
            if (mid.isNotEmpty()) {
                showLoading()
                mViewModel.getAttendanceInfo(mid)
            }
        }
    }

    private fun initUI(it: AttInfoBean) {
        cbItem1ApplyLeave.isChecked = getStringBoolean(it.leave?.isfever)
        cbItem2ApplyLeave.isChecked = getStringBoolean(it.leave?.isdiarrhea)
        cbItem3ApplyLeave.isChecked = getStringBoolean(it.leave?.isvomit)
        cbItem4ApplyLeave.isChecked = getStringBoolean(it.leave?.ismedical)
        etItem4ApplyLeave.setText(it.leave?.temperature)
        etItem6ApplyLeave.setText(it.leave?.hospital)
        etItem7ApplyLeave.setText(it.leave?.diseasename)
        etLeaveRemark.setText(it.leave?.remark)
        it.courseslist?.let { bean ->
            mData.clear()
            bean.forEach {
                if (it.isattendances == "1") {
                    mData.add(it)
                }
            }
            initCourseList(it.leave?.atttime)
        }
        fileValue = it.leave?.fileinfo

        initAvatar()
    }

    private fun doConfirm() {

        if (bean == null) {
            uId = UserInfo.getUserBean().uid
            uType = UserInfo.getUserBean().usertype

            if (mData.size > 0) {
//                bean?.let {
//                    if (!it.id.isNullOrEmpty()){
//                        mViewModel.deleteAttendance(it.id ?: "")
//                    }
//
//                }
                doApplyLeave()
            } else {
                toast(R.string.lack_info)
                return
            }

        } else {
            delNum = 0
            resDelNum = 0
            uId = bean?.uid ?: ""
            uType = "1"
            if (mData.size > 0) {
                var has = false
                bean?.attlists?.let {
                    it.forEach {
                        if (it.attendances == leaveStr) {
                            delNum++
                        }
                    }
                    it.forEach {
                        if (it.attendances == leaveStr) {
                            mViewModel.deleteAttendance(it.id ?: "")
                        }
                    }
                }
                if (delNum == 0) {
                    doApplyLeave()
                }
            } else {
                toast(R.string.lack_info)
                return
            }
        }

    }

    private fun doApplyLeave() {
        if (bean == null) {
            doRealApply()
        } else {
            var msg = bean?.cno + bean?.realname + "\n" + bean?.levelname + bean?.classname
            mAlert(msg, "请确认学生身份") {
                doRealApply()
            }
        }
    }

    private fun doRealApply() {
        var courseList = ""
        mData.forEach {
            courseList += it.id + ","
        }
        if (courseList.isNotEmpty()) {
            courseList = courseList.substring(0, courseList.length - 1)
        }
        mViewModel.addAttendance(
            LeaveBean(
                UserInfo.getUserBean().token,
                uId,
                usertype = uType,
                atttime = chosenDayNet,
                leavetype = leaveType,
                remark = etLeaveRemark.text.toString(),
                crsid = courseList,
                isfever = getBooleanString(cbItem1ApplyLeave.isChecked),
                isdiarrhea = getBooleanString(cbItem2ApplyLeave.isChecked),
                isvomit = getBooleanString(cbItem3ApplyLeave.isChecked),
                ismedical = getBooleanString(cbItem4ApplyLeave.isChecked),
                temperature = etItem4ApplyLeave.text.toString(),
                hospital = etItem6ApplyLeave.text.toString(),
                diseasename = etItem7ApplyLeave.text.toString(),
                fileinfo = fileValue ?: ""
            )
        )
    }


    private fun initAdapter() {
        mAdapter = AttCourseAdapter(R.layout.item_att_course, mData)

        rvAttLesson.apply {
            layoutManager =
                object : LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {

                }
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }

    private fun choosePic() {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .maxSelectNum(1)
            .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    isDownLoad = false
                    avatarPath = result!![0].realPath
                    fileName = avatarPath?.split("/")?.last()
                    if(!avatarPath.isNullOrEmpty()) {
                        mViewModel.getSts()
                        showLoading()
                    }
                }

                override fun onCancel() {

                }

            })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.REQUEST_CODE_COURSE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                mData.clear()
                mData.addAll(data.getParcelableArrayListExtra(Constant.DATA)!!)
                initCourseList(data.getStringExtra(Constant.TITLE))
            }
        }
    }

    private fun initCourseList(dayNet: String?) {
        if (mData.size > 0) {
            chosenDay = DateUtil.formatTitleToday(dayNet ?: "")
            chosenDayNet = dayNet ?: ""
            tvTimeToday.text = "您的请假时间是" + chosenDay
            rvAttLesson.notifyDataSetChanged()
            llAttLesson.visibility = View.VISIBLE
        } else {
            llAttLesson.visibility = View.GONE
        }
    }

    private fun doUpload(it: StsTokenResp) {
        showLoading()
        var mId: String = System.currentTimeMillis().toString() + "_" + fileName
        var objectKey =
            getOssObjectKey(UserInfo.getUserBean().usertype, UserInfo.getUserBean().uid, mId)
        OssUtils.asyncUploadFile(
            this@LeaveTypeActivity,
            it.Credentials,
            objectKey,
            avatarPath,
            object : OssListener {
                override fun onSuccess() {
                    mRootView.post {
                        showSuccess()
                        fileValue = objectKey
                        displayImage(this@LeaveTypeActivity, avatarPath, ivAddPic)
                    }
                }

                override fun onFail() {
                    mRootView.post {
                        showSuccess()
                        toast("上传失败")
                    }
                }

            })
    }

    private fun initAvatar() {
        if (!TextUtils.isEmpty(fileValue!!)) {
            displayImage(
                this,
                UserInfo.getUserBean().domain + fileValue,
                ivAddPic
            )
        }
    }

    private fun doDownload(it: StsTokenResp) {

        showLoading()

        OssUtils.downloadFile(
            this@LeaveTypeActivity,
            it.Credentials,
            UserInfo.getUserBean().portrait,
            mDownloadFile(this, fileValue!!),
            object : OssListener {

                override fun onFail() {
                    mRootView.post {
                        showSuccess()
                        toast("头像下载失败")
                    }
                }

                override fun onSuccess() {
                    mRootView.post {
                        showSuccess()

                        displayImage(
                            this@LeaveTypeActivity,
                            mDownloadFile(
                                this@LeaveTypeActivity,
                                fileValue!!
                            ),
                            ivAddPic
                        )
                    }
                }
            })

    }

    override fun initDataObserver() {
        mViewModel.mAddAttendanceData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                mStartActivity<AttendanceActivity>(this)
            }
        })

        mViewModel.mDeleteAttendanceData.observe(this, Observer { response ->
            response?.let {
                resDelNum++
                if (resDelNum == delNum) {
                    doApplyLeave()
                }
            }
        })

        mViewModel.mAttendanceInfoData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<AttInfoBean>(it)?.let {
                    initUI(it)
                }
            }
        })

        mViewModel.mStsData.observe(this, Observer { response ->
            response?.let {
                Handler().postDelayed(
                    {
                        if (isDownLoad) {
                            doDownload(it)
                        } else {
                            GlobalScope.launch() {
                                async {
                                    doUpload(it)
                                }
                            }
                        }
                    }, 100
                )
            }
        })
    }

}