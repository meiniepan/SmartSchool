package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
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
import com.xiaoneng.ss.module.activity.MainActivity
import com.xiaoneng.ss.module.school.adapter.AttCourseAdapter
import com.xiaoneng.ss.module.school.model.AttCourseBean
import com.xiaoneng.ss.module.school.model.LeaveBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_sick_leave.*
import org.jetbrains.anko.toast
import java.io.File

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
    var leaveType: String = "0"
    override fun getLayoutId(): Int = R.layout.activity_sick_leave


    override fun initView() {
        super.initView()
        tvTimeToday.text = "您的请假时间是" + DateUtil.formatTitleToday()
        leaveType = intent.getStringExtra(Constant.LEAVE_TYPE)
        if (leaveType == "1") {
            llRemarkLeave.visibility = View.VISIBLE
            llSick.visibility = View.GONE
        } else if (leaveType == "2") {
            llRemarkLeave.visibility = View.GONE
            llSick.visibility = View.VISIBLE
        }
        tvConfirm.setOnClickListener {
            doConfirm()
        }
        llItem8ApplyLeave.setOnClickListener {
            mStartForResult<ChooseCourseToLeaveActivity>(this, Constant.REQUEST_CODE_COURSE){}
        }
        ivAddPic.apply {
            setOnClickListener {
                choosePic()
            }
        }
        initAdapter()
    }

    private fun doConfirm() {

        if (mData.size > 0) {
            mData.forEach {
                mViewModel.addAttendance(
                    LeaveBean(
                        UserInfo.getUserBean().token,
                        UserInfo.getUserBean().uid,
                        usertype = UserInfo.getUserBean().usertype,
                        atttime = chosenDayNet,
                        leavetype = leaveType,
                        remark =  etLeaveRemark.text.toString(),
                        crsid = it.id ?: "",
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
        } else {
            toast(R.string.lack_info)
            return
        }
    }


    override fun initData() {
        super.initData()
//        mViewModel.getTimetable()
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
                    fileName = result!![0].fileName
                    mViewModel.getSts()
                    showLoading()
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
                mData.addAll(data.getParcelableArrayListExtra(Constant.DATA))
                if (mData.size > 0) {
                    chosenDay = data.getStringExtra(Constant.TITLE)
                    chosenDayNet = data.getStringExtra(Constant.TITLE_2)
                    tvTimeToday.text = "您的请假时间是" + chosenDay
                    rvAttLesson.notifyDataSetChanged()
                    llAttLesson.visibility = View.VISIBLE
                } else {
                    llAttLesson.visibility = View.GONE
                }
            }
        }
    }

    private fun doUpload(it: StsTokenResp) {
        showLoading()
        var mId: String = System.currentTimeMillis().toString() + "_" + fileName
        var objectKey = getOssObjectKey(UserInfo.getUserBean().usertype, UserInfo.getUserBean().uid, mId)
        OssUtils.asyncUploadFile(
            this@LeaveTypeActivity,
            it.Credentials,
            objectKey,
            avatarPath,
            object : OssListener {
                override fun onSuccess() {
                    mRootView.post {
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
            if (File(mDownloadFile(this, fileValue!!)).exists()) {
                displayImage(
                    this,
                    mDownloadFile(
                        this,
                        fileValue!!
                    ),
                    ivAddPic
                )
            } else {
                isDownLoad = true
                mViewModel.getSts()
            }
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
                mStartActivity<MainActivity>(this)
            }
        })

        mViewModel.mStsData.observe(this, Observer { response ->
            response?.let {
                if (isDownLoad) {
                    doDownload(it)
                } else {
                    doUpload(it)
                }
            }
        })

    }


}