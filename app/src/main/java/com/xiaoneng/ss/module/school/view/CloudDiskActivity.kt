package com.xiaoneng.ss.module.school.view

import android.animation.ObjectAnimator
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Handler
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
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.FileTransInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.school.adapter.DiskPriAdapter
import com.xiaoneng.ss.module.school.adapter.DiskPubAdapter
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_cloud_disk.*
import kotlinx.android.synthetic.main.activity_cloud_disk.ivAddFile
import kotlinx.android.synthetic.main.activity_cloud_disk.llBottom
import kotlinx.android.synthetic.main.activity_cloud_disk.llMain
import kotlinx.android.synthetic.main.activity_cloud_disk.rvDisk
import kotlinx.android.synthetic.main.activity_cloud_disk.tvBottomCopy
import kotlinx.android.synthetic.main.activity_cloud_disk.tvBottomDel
import kotlinx.android.synthetic.main.activity_cloud_disk.tvBottomDownload
import kotlinx.android.synthetic.main.activity_cloud_disk.tvBottomMove
import kotlinx.android.synthetic.main.activity_cloud_disk.tvBottomRename
import kotlinx.android.synthetic.main.activity_cloud_disk.tvDiskNew
import kotlinx.android.synthetic.main.activity_cloud_disk.tvDiskUpload
import kotlinx.android.synthetic.main.activity_cloud_folder.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import java.io.File


/**
 * @author Burning
 * @description:教学云盘
 * @date :2020/10/23 3:17 PM
 */
class CloudDiskActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapterPri: DiskPriAdapter
    lateinit var mAdapterPub: DiskPubAdapter
    var mPriData: ArrayList<FolderBean> = ArrayList()
    var mPubData: ArrayList<FolderBean> = ArrayList()
    var rotationB = false
    var filePath: String? = null
    var fileName: String? = null
    var mCurrent: Int = 0
    private val newFolderDialog: Dialog by lazy {
        initDialog()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_cloud_disk
    }

    override fun initView() {
        super.initView()
        tvPrivate.setOnClickListener {
            checkFirsTab()
        }
        tvPublic.setOnClickListener {
            checkSecondTab()
        }
        ivAddFile.setOnClickListener {
            showAdd()
        }
        tvDiskUpload.setOnClickListener { choseFile() }
        tvDiskNew.setOnClickListener { newFolderDialog.show() }
        ivFileRecord.setOnClickListener {
            mStartActivity<CloudTransActivity>(this)
        }
        tvBottomDownload.setOnClickListener {

        }
        tvBottomRename.setOnClickListener {

        }
        tvBottomCopy.setOnClickListener {

        }
        tvBottomMove.setOnClickListener {

        }
        tvBottomDel.setOnClickListener {

        }
        initAdapter()
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        rvDisk.showLoadingView()
        mViewModel.getPriCloudList()
        mViewModel.getPubCloudList()
    }

    private fun initAdapter() {

        mAdapterPri = DiskPriAdapter(R.layout.item_folder_pri, mPriData)
        mAdapterPub = DiskPubAdapter(R.layout.item_disk, mPubData)
        rvDisk.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CloudDiskActivity)
            setAdapter(mAdapterPri)
        }

        mAdapterPri.setOnItemClickListener { adapter, view, position ->
            mStartActivity<CloudFolderActivity>(this) {
                var bean = mPriData[position]
                bean.fullName = "私有云>"+bean.foldername
                putExtra(Constant.DATA, mPriData[position])
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
                    var cb:CheckBox = view as CheckBox
                    if (cb.isChecked){
                        llBottom.visibility = View.VISIBLE
                    }else{
                        llBottom.visibility = View.GONE
                    }
                }

            }
        }
    }

    private fun showAdd(it: View=ivAddFile) {
        val anim: ObjectAnimator
        if (rotationB) {
            anim = ObjectAnimator.ofFloat(it, "rotation", 45.0F, 0F)
            anim.doOnEnd {
                tvDiskNew.visibility = View.GONE
                llMain.visibility = View.GONE
            }
        } else {
            anim = ObjectAnimator.ofFloat(it, "rotation", 0.0F, 45.0F)
            anim.doOnEnd {
                tvDiskNew.visibility = View.VISIBLE
                llMain.visibility = View.VISIBLE
            }
        }
        rotationB = !rotationB
        anim.duration = 300
        anim.start()
    }


    private fun checkFirsTab() {
        mCurrent = 0
        tvPrivate.setBackgroundResource(R.drawable.bac_blue_bac)
        tvPrivate.setTextColor(resources.getColor(R.color.white))
        tvPublic.setBackgroundResource(R.drawable.bac_blue_line_21)
        tvPublic.setTextColor(resources.getColor(R.color.themeColor))
        rvDisk.recyclerView.setAdapter(mAdapterPri)
        rvDisk.notifyDataSetChanged()
    }

    private fun checkSecondTab() {
        mCurrent = 1
        tvPublic.setBackgroundResource(R.drawable.bac_blue_bac)
        tvPublic.setTextColor(resources.getColor(R.color.white))
        tvPrivate.setBackgroundResource(R.drawable.bac_blue_line_21)
        tvPrivate.setTextColor(resources.getColor(R.color.themeColor))
        rvDisk.recyclerView.setAdapter(mAdapterPub)
        rvDisk.notifyDataSetChanged()
    }

    private fun choseFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("*/*") //设置类型，我这里是任意类型，任意后缀的可以这样写。
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 1)

    }

    private fun doUpload(it: StsTokenResp) {
        mRootView.post {
            mToast("已加入上传列表")
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
                path
                = filePath ?: "", objectKey = objectKey, totalSize = totalSize
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


                    }

                }

                override fun onFail() {
                    mRootView.post {
//                        mToast("文件上传失败")
                    }
                }

            })
    }

    private fun initDialog(): Dialog {
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
            mViewModel.newFileFolder(foldername = folderName)
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

        mViewModel.mPriCloudData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<DiskFileResp>(it)?.let {
                    mPriData.clear()
                    it.data?.let { it1 -> mPriData.addAll(it1) }
                    rvDisk.recyclerView.setAdapter(mAdapterPri)
                    rvDisk.notifyDataSetChanged()
                }
            }
        })

        mViewModel.mPubCloudData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<DiskFileResp>(it)?.let {
                    mPubData.clear()
                    it.data?.let { it1 -> mPubData.addAll(it1) }

                }

            }
        })
    }
}