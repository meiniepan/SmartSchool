package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jiang.awesomedownloader.downloader.AwesomeDownloader
import com.jiang.awesomedownloader.tool.PathSelector
import com.tencent.smtt.sdk.QbSdk
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.UserBean
import com.xiaoneng.ss.base.view.BaseApplication
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.circular.adapter.NoticeFileAdapter
import com.xiaoneng.ss.module.school.adapter.InvolveSimpleAdapter
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_schedule.*
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.activity_add_task.llBeginTime
import kotlinx.android.synthetic.main.activity_add_task.llEndTime
import kotlinx.android.synthetic.main.activity_add_task.rvParticipant
import kotlinx.android.synthetic.main.activity_add_task.rvTaskFile
import kotlinx.android.synthetic.main.activity_add_task.tvBeginDate
import kotlinx.android.synthetic.main.activity_add_task.tvBeginTime
import kotlinx.android.synthetic.main.activity_add_task.tvEndDate
import kotlinx.android.synthetic.main.activity_add_task.tvEndTime
import kotlinx.android.synthetic.main.activity_add_task.tvTitleAddTask
import kotlinx.android.synthetic.main.activity_notice_detail.*
import kotlinx.android.synthetic.main.activity_task_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class AddTaskActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private val mMax: Int = 5
    var mId: String? = null
    var beginTime: String = DateUtil.getNearTimeBeginYear()
    var endTime: String = DateUtil.getNearTimeEndYear()
    var orderTime: String = ""
    lateinit var mAdapter: InvolveSimpleAdapter
    lateinit var mAdapterPrincipal: InvolveSimpleAdapter
    var mData = ArrayList<UserBeanSimple>()
    var mDataPrincipal = ArrayList<UserBeanSimple>()
    var taskBean = TaskBean()
    var mDataDepartment = ArrayList<DepartmentBean>()
    var mDataClasses = ArrayList<DepartmentBean>()
    var receiveList: ArrayList<UserBeanSimple> = ArrayList()
    var isDraftFirst: Boolean = true
    lateinit var mAdapterFile: NoticeFileAdapter
    var mDataFile = ArrayList<FileInfoBean>()
    var idString = ""
    var fileNum = 0
    var downloadNum = 0
    var filePath:String? = null
    var fileName:String? = null


    override fun getLayoutId(): Int = R.layout.activity_add_task


    override fun initView() {
        super.initView()
        mId = intent.getStringExtra(Constant.ID)
        initAdapter()
        initAdapterPrincipal()
        initAdapterFile()
        initUi()
        llBeginTime.apply {
            setOnClickListener {
                showDatePick(tvBeginDate, tvBeginTime) {
                    beginTime = this
                    //当结束时间小于开始时间时
                    if (endTime.isNullOrEmpty() || DateUtil.getTimeInMillis(beginTime) > DateUtil.getTimeInMillis(endTime)) {
                        DateUtil.showTimeFromNet(DateUtil.getNearTimeBeginYear(DateUtil.getTimeInMillis(beginTime)), tvEndDate, tvEndTime)
                        endTime = DateUtil.getNearTimeBeginYear(DateUtil.getTimeInMillis(beginTime))
                    }
                }
            }
        }
        llEndTime.apply {
            setOnClickListener {
                showDatePick(tvEndDate, tvEndTime,beginTime = DateUtil.getTimeInMillis(beginTime)) {
                    endTime = this
                    //当结束时间小于开始时间时
                    if (beginTime.isNullOrEmpty() || DateUtil.getTimeInMillis(beginTime) > DateUtil.getTimeInMillis(endTime)) {
                        DateUtil.showTimeFromNet(endTime!!, tvBeginDate, tvBeginTime)
                        beginTime = endTime
                    }
                }
            }
        }
        tvTimingAddTask.apply {
            setOnClickListener {
                showDatePick(tvEndDate, tvEndTime) {
                    orderTime = this
                    setOrderPublish()
                }
            }
        }
        tvUpFile.apply {
            setOnClickListener {
                choseFile()
            }
        }


        tvAddParticipant.setOnClickListener {
            doAddParticipant()
        }
        tvAddPrincipal.setOnClickListener {
            doAddPrincipal()
        }
        tvConfirmAddTask.setOnClickListener {
            addTask()
        }
    }

    private fun setOrderPublish() {
        tvConfirmAddTask.text = "定时发布"
    }

    private fun initUi() {
        DateUtil.showTimeFromNet(beginTime, tvBeginDate, tvBeginTime)
        DateUtil.showTimeFromNet(endTime, tvEndDate, tvEndTime)
    }

    override fun initData() {
        super.initData()
        mId?.let {
            showLoading()
            mViewModel.getTaskInfo(it, "task")
        }
    }

    private fun choseFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*") //设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, Constant.REQUEST_CODE_FILE)
    }

    private fun addTask() {
        if (tvTitleAddTask.text.isEmpty() || receiveList.size == 0) {
            mToast(R.string.lack_info)
            return
        }
        var taskBean = TaskBean(
                UserInfo.getUserBean().token,
                taskname = tvTitleAddTask.text.toString(),
                plantime = beginTime,
                plantotal = 10.toString(),
                overtime = endTime,
                involve = Gson().toJson(receiveList),
                ordertime = orderTime,
                remark = etRemarkAddTask.text.toString(),
                status = "1",
                fileinfo = Gson().toJson(mDataFile)
        )
        mAlert("确定发布任务？") {
            if (mId.isNullOrEmpty()) {
                mViewModel.addTask(taskBean)
            } else {
                taskBean.id = mId
                mViewModel.modifyTaskStatus(taskBean)
            }
        }

    }

    private fun doOpen(filePath: String) {
        QbSdk.openFileReader(this, filePath, null, null)
    }

    private fun doAddPrincipal() {
//        mStartForResult<AddInvolveActivity>(this, Constant.REQUEST_CODE_PRINCIPAL) {
//
//        }
    }

    private fun initAdapterFile() {
        rvTaskFile.visibility = View.GONE
        mAdapterFile = NoticeFileAdapter(R.layout.item_notice_file, mDataFile)
        mAdapterFile.canDel()
        rvTaskFile.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setAdapter(mAdapterFile)
        }
        mAdapterFile.setOnItemClickListener { _, view, position ->
            var path = PathSelector(BaseApplication.instance).getDownloadsDirPath()
            var name = idString + mDataFile[position].name
            var filePath = path + File.separator + name
            var filename = File(filePath)
            if (filename.exists()) {
                doOpen(filePath)
            } else {
                doDown(mDataFile[position].url, name)
            }
        }
    }

    private fun doAddParticipant() {
        mStartForResult<AddInvolveActivity>(this, Constant.REQUEST_CODE_COURSE) {
            putExtra(Constant.DATA, mDataDepartment)
            putExtra(Constant.DATA2, mDataClasses)
            //从草稿箱第一次选择参与人，传入原有参与人数据
            if (isDraftFirst) {
                if (receiveList.size > 0) {
                    putExtra(Constant.DATA3, receiveList)
                }
            }
        }
    }


    private fun initAdapter() {
        mAdapter = InvolveSimpleAdapter(R.layout.item_involve2, mData)
        mAdapter.setMax(mMax)
        rvParticipant.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }

    private fun initAdapterPrincipal() {

    }


    override fun onBackPressed() {
        showDialog()
    }

    private fun showDialog() {
        // 底部弹出对话框
        val bottomDialog =
                Dialog(this, R.style.BottomDialog)
        val contentView: View =
                LayoutInflater.from(this).inflate(R.layout.dialog_save_draft, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
                resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        bottomDialog.show()
        contentView.findViewById<View>(R.id.tvSaveDraft)
                .setOnClickListener { v: View? ->
                    taskBean = TaskBean(
                            token = UserInfo.getUserBean().token,
                            taskname = tvTitleAddTask.text.toString(),
                            plantime = beginTime,
                            plantotal = 10.toString(),
                            overtime = endTime,
                            involve = Gson().toJson(receiveList),
                            ordertime = orderTime,
                            remark = etRemarkAddTask.text.toString(),
                            status = "0",
                            fileinfo = Gson().toJson(mDataFile)
                    )
                    if (mId.isNullOrEmpty()) {
                        mViewModel.addTask(taskBean)
                    } else {
                        taskBean.id = mId
                        mViewModel.modifyTaskStatus(taskBean)
                    }
                    bottomDialog.dismiss()
                }
        contentView.findViewById<View>(R.id.tvNoSaveDraft)
                .setOnClickListener { v: View? ->
                    finish()
                    bottomDialog.dismiss()
                }
        contentView.findViewById<View>(R.id.tvCancelDraft)
                .setOnClickListener { v: View? ->

                    bottomDialog.dismiss()
                }
    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_COURSE) {
                if (data != null) {
                    isDraftFirst = false
                    mData.clear()
                    receiveList.clear()
                    mDataDepartment = data.getParcelableArrayListExtra(Constant.DATA)!!
                    mDataClasses = data.getParcelableArrayListExtra(Constant.DATA2)!!
                    mDataDepartment.forEach {
                        addDepartment(it)
                    }
                    mDataClasses.forEach {
                        addDepartment(it)
                    }
                    for (i in 0 until receiveList.size) {
                        if (i < mMax) {
                            mData.add(receiveList[i])
                        }
                    }
                    mAdapter.notifyDataSetChanged()
                }
            } else if (requestCode == Constant.REQUEST_CODE_FILE) {
                val uri: Uri? = data?.getData() //得到uri，后面就是将uri转化成file的过程。
                    filePath = OssUtils().getPath(this, uri)
                    fileName = filePath?.split("/")?.last()
                    if (!filePath.isNullOrEmpty()) {
                        mViewModel.getSts()
                    }else{
                        mToast(getString(R.string.errFile))
                    }
            }
        }
    }

    private fun addDepartment(it: DepartmentBean) {
        if (it.num!!.toInt() > 0) {
            it.list.forEach {
                receiveList.add(
                        UserBeanSimple(
                                uid = it.uid,
                                realname = it.realname,
                                classid = it.classid,
                                usertype = it.usertype
                        )
                )
            }
        }
    }

    private fun doDustbin() {
        mAlert("删除后不可恢复请慎重选择是否删除该任务", "是否确定删除该任务") {
            mViewModel.delTaskDraft(mId ?: "")
        }
    }

    private fun doUpload(it: StsTokenResp) {

        showLoading()
        var mId: String = System.currentTimeMillis().toString() + "_" + fileName

        var objectKey =
                getOssObjectKey(UserInfo.getUserBean().usertype, UserInfo.getUserBean().uid, mId)
        OssUtils.asyncUploadFile(
                this,
                it.Credentials,
                objectKey,
                filePath,
                object : OssListener {
                    override fun onSuccess() {
                        mRootView.post {
                            showSuccess()
                            mDataFile.add(FileInfoBean(name = fileName, url = objectKey, ext = FileExtBean(size = File(filePath).length().toString())))
                            rvTaskFile.visibility = View.VISIBLE
                            mAdapterFile.notifyDataSetChanged()

                        }

                    }

                    override fun onFail() {
                        mRootView.post {
                            showSuccess()
                            mToast("文件上传失败")
                        }
                    }

                })
    }

    private fun doDown(url: String?, fileName: String?) {
        showLoading()
        AwesomeDownloader.init(BaseApplication.instance)
        //关闭通知栏
        AwesomeDownloader.option.showNotification = false
        val url = UserInfo.getUserBean().domain + url
        //获取应用外部照片储存路径
        val filePath = PathSelector(BaseApplication.instance).getDownloadsDirPath()
        //加入下载队列
        AwesomeDownloader.enqueue(url, filePath, fileName ?: "")
        AwesomeDownloader.setOnProgressChange { progress ->

            //do something...
        }.setOnStop { downloadBytes, totalBytes ->
            //do something...
        }.setOnFinished { filePath, fileName2 ->
            showSuccess()
            var path = PathSelector(BaseApplication.instance).getDownloadsDirPath()
            var name = fileName
            var filePath = path + File.separator + name
            doOpen(filePath)
        }.setOnError { exception ->
            //do something...
        }
    }

    override fun initDataObserver() {
        mViewModel.mStsData.observe(this, Observer { response ->
            response?.let {
                Handler().postDelayed(
                        {
                            GlobalScope.launch() {
                                async {
                                    doUpload(it)
                                }
                            }
                        }, 100
                )

            }
        })

        mViewModel.mAddTaskData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                finish()
            }
        })

        mViewModel.mModifyTaskStatusData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                finish()
            }
        })

        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                finish()
            }
        })

        mViewModel.mTaskDetailData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<TaskDetailBean>(it)?.let {
                    tvDustbin.visibility = View.VISIBLE
                    tvDustbin.setOnClickListener {
                        doDustbin()
                    }
                    it.plantime?.let {
                        beginTime = it
                    }
                    it.overtime?.let {
                        endTime = it
                    }
                    it.involve?.let {
                        receiveList.clear()
                        mData.clear()
                        receiveList.addAll(it)
                        for (i in 0 until receiveList.size) {
                            if (i < 4) {
                                mData.add(receiveList[i])
                            }
                        }
                        mAdapter.notifyDataSetChanged()
                    }
                    initUi()
                    it.taskname?.let {
                        tvTitleAddTask.setText(it)
                    }
                    it.remark?.let {
                        etRemarkAddTask.setText(it)
                    }
                    it.ordertime?.let {
                        if (it.isNotEmpty()) {
                            setOrderPublish()
                        }
                    }
                    //附件列表
                    mDataFile.clear()
                    var files= ArrayList<FileInfoBean>()
                    val resultType = object : TypeToken<ArrayList<FileInfoBean>>() {}.type
                    val gson = Gson()
                    try {
                        files = gson.fromJson<ArrayList<FileInfoBean>>(it.fileinfo, resultType)

                    } catch (e: Exception) {
//                        showError(getString(R.string.error_message))
                    }
                    mDataFile.addAll(files)
                    if (mDataFile.size > 0) {
                        rvTaskFile.visibility = View.VISIBLE
                        mAdapterFile.notifyDataSetChanged()
                    }
                }
            }
        })

    }

}