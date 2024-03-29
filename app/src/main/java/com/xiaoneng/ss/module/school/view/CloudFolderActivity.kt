package com.xiaoneng.ss.module.school.view

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.arialyy.annotations.Download
import com.arialyy.aria.core.Aria
import com.arialyy.aria.core.task.DownloadTask
import com.tencent.smtt.sdk.QbSdk
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseApplication
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.FileDownloadInfo
import com.xiaoneng.ss.common.state.FileTransInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.eventBus.FileDownloadEvent
import com.xiaoneng.ss.common.utils.eventBus.FileMoveEvent
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.activity.ImageScaleActivity
import com.xiaoneng.ss.module.activity.VideoActivity
import com.xiaoneng.ss.module.school.adapter.DiskPriAdapter
import com.xiaoneng.ss.module.school.model.DiskFileBean
import com.xiaoneng.ss.module.school.model.DiskFileResp
import com.xiaoneng.ss.module.school.model.FolderBean
import com.xiaoneng.ss.module.school.model.FolderModifyBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_cloud_folder.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast
import java.io.File


/**
 * @author Burning
 * @description:教学云盘
 * @date :2020/10/23 3:17 PM
 */
class CloudFolderActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapterPri: DiskPriAdapter
    var mPriData: ArrayList<FolderBean> = ArrayList()
    var mPriDataFolder: ArrayList<FolderBean>? = ArrayList()
    var mPriDataFile: ArrayList<FolderBean>? = ArrayList()
    var rotationB = false
    var filePath: String? = null
    var fileName: String? = null
    var idString: String = "cloud_"
    var folderBean: FolderBean? = null
    var mCurrent: Int = 0
    var mNetCount: Int = 0
    private val newFolderDialog: Dialog by lazy {
        initDialog(0)
    }
    private val renameFolderDialog: Dialog by lazy {
        initDialog(1)
    }
    private val renameFileDialog: Dialog by lazy {
        initDialog(2)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_cloud_folder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Aria.download(this).register()
    }

    override fun initView() {
        super.initView()
        folderBean = intent.getParcelableExtra(Constant.DATA)
        tvParentName.text = folderBean?.fullName
        ivAddFile.setOnClickListener {
            showAdd()
        }
        tvDiskUpload.setOnClickListener { choseFile() }
        tvDiskNew.setOnClickListener { newFolderDialog.show() }
        tvBottomDownload.setOnClickListener {
            var bean = mPriData[mCurrent]
            var filePath = PathSelector(BaseApplication.instance).getXiaonengPath()
            filePath = filePath + File.separator + idString + bean.id + bean.filename
            var diskFileBean = DiskFileBean(
                path = filePath,
                filename = mPriData[mCurrent].filename,
                objectid = bean.objectid, totalSize = 0
            )
            doDown(bean.objectid, filePath)
            mToast("已加入下载队列")
        }
        tvBottomRename.setOnClickListener {
            if (mPriData[mCurrent].isFolder) {
                renameFolderDialog.show()
            } else {
                renameFileDialog.show()
            }
        }
        tvBottomCopy.setOnClickListener {
            mStartActivity<CloudCopyActivity>(this) {
                putExtra(Constant.DATA, mPriData[mCurrent])
                putExtra(Constant.DATA2, 1)
            }
        }
        tvBottomMove.setOnClickListener {
            mStartActivity<CloudCopyActivity>(this) {
                putExtra(Constant.DATA, mPriData[mCurrent])
                putExtra(Constant.DATA2, 2)
            }
        }
        tvBottomDel.setOnClickListener {
            var str = "确认删除"
            if (mPriData[mCurrent].isFolder) {
                str = str + "文件夹" + mPriData[mCurrent].foldername
            } else {
                str = str + "文件" + mPriData[mCurrent].filename
            }
            str = str + "?"
            mAlert(str) {
                showLoading()
                if (mPriData[mCurrent].isFolder) {
                    mViewModel.delCloudFolder(folderid = mPriData[mCurrent].folderid)
                } else {
                    mViewModel.delMyCloudFile(fileid = mPriData[mCurrent].fileid)
                }

            }
        }
        ivFileRecord.setOnClickListener {
            mStartActivity<CloudTransActivity>(this)
        }
        llMain.setOnClickListener {
            showAdd()
        }
        initAdapter()
    }


    private fun showBottom(b: Boolean) {
        if (b) {
            llBottom.visibility = View.VISIBLE
        } else {
            llBottom.visibility = View.GONE
        }
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        rvDisk.showLoadingView()
        mNetCount = 0
        if (folderBean?.isPrivate != false) {
            mViewModel.getPriCloudFiles(folderBean?.folderid)
            mViewModel.getPriCloudList(folderBean?.folderid)
        } else {
            mViewModel.getPubCloudFiles(folderBean?.folderid)
            mViewModel.getPubCloudList(folderBean?.folderid)
        }

    }

    private fun initAdapter() {

        mAdapterPri = DiskPriAdapter(R.layout.item_folder_pri, mPriData)
        rvDisk.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CloudFolderActivity)
            setAdapter(mAdapterPri)
        }
        mAdapterPri.isPrivate = folderBean?.isPrivate ?: true
        mAdapterPri.setOnItemClickListener { adapter, view, position ->
            mCurrent = position
            if (mPriData[position].isFolder) {
                mStartActivity<CloudFolderActivity>(this) {
                    var bean = mPriData[position]
                    bean.isPrivate = folderBean?.isPrivate ?: true
                    bean.fullName = folderBean?.fullName + ">" + bean.foldername
                    putExtra(Constant.DATA, mPriData[position])
                }
            } else {
                var mUrl = ""
                var path = PathSelector(BaseApplication.instance).getXiaonengPath()
                var name = idString + mPriData[position].id + mPriData[position].filename
                var filePath = path + File.separator + name
                var file = File(filePath)
                if (file.exists()) {
                    mUrl = filePath
                } else {
                    mUrl = UserInfo.getUserBean().domain + mPriData[position].objectid
                }
                if (mPriData[position].objectid.endIsVideo()) {

                    mStartActivity<VideoActivity>(this) {
                        mPriData[position].path = mUrl
                        putExtra(Constant.DATA, mPriData[position])
                    }
                } else if (mPriData[position].objectid.endIsImage()) {
                    mStartActivity<ImageScaleActivity>(this) {
                        putExtra(Constant.DATA, mUrl)
                    }
                } else {

                    if (file.exists()) {
                        doOpen(filePath)
                    } else {
                        mToast("下载中，请稍后查看")
                        doDown(mPriData[position].objectid, filePath)
                    }
                }
            }
        }
        mAdapterPri.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {

                R.id.ivFolderInfo -> {
                    mStartActivity<FolderSettingActivity>(this) {
                        putExtra(Constant.DATA, mPriData[position])
                    }
                }
                R.id.cbDiskFile -> {
                    var cb: CheckBox = view as CheckBox
                    mPriData.forEach {
                        it.isChecked = false
                    }
                    mPriData[position]?.isChecked = cb.isChecked
                    mAdapterPri.notifyDataSetChanged()
                    mCurrent = position
                    showBottom(cb.isChecked)
                    if (cb.isChecked) {
                        if (mPriData[position].isFolder) {
                            llBottomDownload.visibility = View.GONE
                            llBottomRename.visibility = View.VISIBLE
                            llBottomCopy.visibility = View.GONE
                            llBottomMove.visibility = View.GONE
                            llBottomDel.visibility = View.VISIBLE
                        } else {
                            if (folderBean?.isPrivate != true && UserInfo.getUserBean().uid != folderBean?.cuser_id) {
                                llBottomDownload.visibility = View.VISIBLE
                                llBottomRename.visibility = View.GONE
                                llBottomCopy.visibility = View.GONE
                                llBottomMove.visibility = View.GONE
                                llBottomDel.visibility = View.GONE
                            } else {
                                llBottomDownload.visibility = View.VISIBLE
                                llBottomRename.visibility = View.VISIBLE
                                llBottomCopy.visibility = View.VISIBLE
                                llBottomMove.visibility = View.VISIBLE
                                llBottomDel.visibility = View.VISIBLE
                            }
                        }
                    } else {

                    }
                }

            }
        }
    }

    private fun showAdd(it: View = ivAddFile) {
        val anim: ObjectAnimator
        if (rotationB) {
            anim = ObjectAnimator.ofFloat(it, "rotation", 45.0F, 0F)
            anim.doOnEnd {
                tvDiskNew.visibility = View.GONE
                tvDiskUpload.visibility = View.GONE
                llMain.visibility = View.GONE
            }
        } else {
            anim = ObjectAnimator.ofFloat(it, "rotation", 0.0F, 45.0F)
            anim.doOnEnd {
                tvDiskNew.visibility = View.VISIBLE
                tvDiskUpload.visibility = View.VISIBLE
                llMain.visibility = View.VISIBLE
            }
        }
        rotationB = !rotationB
        anim.duration = 300
        anim.start()
    }


    private fun choseFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*") //设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 1)

    }

    private fun doOpen(filePath: String) {
        QbSdk.openFileReader(this, filePath, null, null)
    }

    private fun doDown(url: String?, filePath: String) {
        if (FileDownloadInfo.hasFile(url)) {
            return
        }
        val url2 = UserInfo.getUserBean().domain + url
        val taskId: Long = Aria.download(this)
            .load(url2) //读取下载地址
            .setFilePath(filePath) //设置文件保存的完整路径
            .create() //创建并启动下载
        var bean = DiskFileBean(
            path = filePath,
            downTaskId = taskId,
            status = 0,
            filename = mPriData[mCurrent].filename,
            objectid = url ?: ""
        )
        FileDownloadInfo.addFile(bean)
    }

    //在这里处理任务执行中的状态，如进度进度条的刷新
    @Download.onTaskRunning
    fun running(task: DownloadTask) {
        var taskUrl = task.getKey()
        if (taskUrl.length > UserInfo.getUserBean().domain?.length ?: 0) {
            taskUrl = taskUrl.substring(UserInfo.getUserBean().domain?.length ?: 0, taskUrl.length)
        }
        Log.w("=====", task.percent.toString())
        var bean = DiskFileBean(
            objectid = taskUrl, progress = task.getPercent().toLong(),
            totalSize = task.fileSize, currentSize = task.fileSize * task.percent / 100
        )
        FileDownloadInfo.modifyFile(bean)
        FileDownloadEvent(bean).post()

    }

    @Download.onTaskComplete
    fun taskComplete(task: DownloadTask) {
        //在这里处理任务完成的状态
        var taskUrl = task.getKey()
        if (taskUrl.length > UserInfo.getUserBean().domain?.length ?: 0) {
            taskUrl = taskUrl.substring(UserInfo.getUserBean().domain?.length ?: 0, taskUrl.length)
        }
        var bean = DiskFileBean(
            objectid = taskUrl, status = 2, totalSize = task.fileSize
        )
        FileDownloadInfo.modifyFile(bean)
        FileDownloadEvent(bean).post()
        if (mAdapterPri != null) {
            mAdapterPri.notifyDataSetChanged()
        }
    }

    private fun doUpload(it: StsTokenResp) {
        mRootView.post {
            mToast("已加入上传列表")
            showAdd()
        }
        var mId: String = System.currentTimeMillis().toString() + "_" + fileName
        var totalSize = 0L
        if (File(filePath).exists()) {
            totalSize = File(filePath).length()
        }
        //将上传文件的进度信息存储到本地
        var objectKey =
            getOssObjectKey(UserInfo.getUserBean().usertype, UserInfo.getUserBean().uid, mId)
        FileTransInfo.addFile(
            DiskFileBean(
                path = filePath ?: "",
                folderid = folderBean?.id,
                objectid = objectKey, totalSize = totalSize
            )
        )
        OssUtils.uploadResumeFile(
            this,
            it.Credentials,
            objectKey,
            filePath,
            object : OssListener {
                override fun onSuccess() {
                    mRootView.post {
                        var bean = DiskFileBean(
                            token = UserInfo.getUserBean().token,
                            filename = fileName ?: "",
                            objectid = objectKey,
                            folderid = folderBean?.folderid ?: ""
                        )
                        mViewModel.addFile(bean)
                    }

                }

                override fun onFail() {
                    mRootView.post {
//                        mToast("文件上传失败")
                    }
                }

            })
    }

    private fun initDialog(i: Int): Dialog {
        //i: 0 新建文件夹  1 文件夹重命名 2 文件重命名
        // 底部弹出对话框
        var bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_new_folder, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            this.resources.displayMetrics.widthPixels - dp2px(32f).toInt()
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.CENTER)
        var etFolderName = contentView.findViewById<EditText>(R.id.etFolderName)
        var tvTitle = contentView.findViewById<TextView>(R.id.tvTitle6)
        if (i == 0) {

        } else if (i == 1) {
            tvTitle.text = "重命名"
            etFolderName.hint = "文件夹名称"
        } else if (i == 2) {
            tvTitle.text = "重命名"
            etFolderName.hint = "文件名称"
        }
        var tvConfirm = contentView.findViewById<TextView>(R.id.tvFolderConfirm)
        contentView.findViewById<View>(R.id.ivClose).setOnClickListener {
            bottomDialog.dismiss()
        }

        tvConfirm.setOnClickListener {
            var folderName = etFolderName.text.toString()

            if (folderName.isEmpty()) {
                mToast(R.string.lack_info)
                return@setOnClickListener
            }

            showLoading()
            if (i == 0) {
                mViewModel.newFileFolder(parentid = folderBean?.id, foldername = folderName)

            } else if (i == 1) {
                var bean = FolderModifyBean(
                    token = UserInfo.getUserBean().token,
                    id = mPriData[mCurrent].folderid,
                    foldername = folderName
                )
                mPriData[mCurrent].foldername = folderName
                mViewModel.modifyFolder(bean)
            } else if (i == 2) {
                var bean = FolderModifyBean(
                    token = UserInfo.getUserBean().token,
                    id = mPriData[mCurrent].fileid,
                    filename = folderName
                )
                mPriData[mCurrent].filename = folderName
                mViewModel.modifyFile(bean)
            }
            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) { //是否选择，没选择就不会继续
            val uri: Uri? = data?.getData() //得到uri，后面就是将uri转化成file的过程。
            filePath = OssUtils().getPath(this, uri)
            fileName = filePath?.split("/")?.last()
            if (!filePath.isNullOrEmpty()) {
                mViewModel.getSts()
            } else {
                mToast(getString(R.string.errFile))
            }
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

        mViewModel.mNewFolderData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                getData()
                showAdd()
            }
        })

        mViewModel.mAddFileData.observe(this, Observer { response ->
            response?.let {
                toast("上传完成")
                getData()
            }
        })

        mViewModel.mDelCloudFileData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                onDelDone()
            }
        })

        mViewModel.mDelCloudFolderData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                onDelDone()
            }
        })

        mViewModel.mModifyFileData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                mAdapterPri.notifyItemChanged(mCurrent)
            }
        })

        mViewModel.mPriCloudData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<DiskFileResp>(it)?.let {
                    doFolderData(it)

                }
            }
        })

        mViewModel.mPubCloudData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<DiskFileResp>(it)?.let {
                    doFolderData(it)

                }
            }
        })

        mViewModel.mPriCloudFilesData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<DiskFileResp>(it)?.let {
                    doFilesData(it)
                }
            }
        })

        mViewModel.mPubCloudFilesData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<DiskFileResp>(it)?.let {
                    doFilesData(it)
                }
            }
        })

    }

    private fun onDelDone() {

        showBottom(false)
        mPriData.removeAt(mCurrent)
        mAdapterPri.notifyItemRemoved(mCurrent)
        if (mPriData.size == 0) {
            rvDisk.showEmptyView()
        }
    }

    private fun doFilesData(it: DiskFileResp) {
        mNetCount++
        mPriDataFile = it.data
        mPriDataFile?.let {
            it.forEach {
                it.isFolder = false
            }
        }
        if (mNetCount == 2) {
            mPriData.clear()
            mPriDataFolder?.let { it1 -> mPriData.addAll(it1) }
            mPriDataFile?.let { it1 -> mPriData.addAll(it1) }
            rvDisk.recyclerView.setAdapter(mAdapterPri)
            rvDisk.notifyDataSetChanged()
        }
    }

    private fun doFolderData(it: DiskFileResp) {
        mNetCount++
        mPriDataFolder = it.data
        if (mNetCount == 2) {
            mPriData.clear()
            mPriDataFolder?.let { it1 -> mPriData.addAll(it1) }
            mPriDataFile?.let { it1 -> mPriData.addAll(it1) }
            rvDisk.recyclerView.setAdapter(mAdapterPri)
            rvDisk.notifyDataSetChanged()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshMoveFile(event: FileMoveEvent) {
        onDelDone()
    }
}