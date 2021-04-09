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
import kotlinx.android.synthetic.main.activity_cloud_copy.*
import kotlinx.android.synthetic.main.activity_cloud_folder.*
import kotlinx.android.synthetic.main.activity_cloud_folder.rvDisk
import kotlinx.android.synthetic.main.activity_cloud_folder.tvDiskNew
import kotlinx.android.synthetic.main.activity_cloud_folder.tvParentName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import java.io.File


/**
 * @author Burning
 * @description:教学云盘文件、文件夹复制移动
 * @date :2021/4/9 3:17 PM
 */
class CloudCopyActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapterPri: DiskPriAdapter
    var mPriData: ArrayList<FolderBean> = ArrayList()
    var filePath: String? = null
    var fileName: String? = null
    var folderBean: FolderBean? = null
    var sourceFolderBean: FolderBean? = null
    private val newFolderDialog: Dialog by lazy {
        initDialog()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_cloud_copy
    }

    override fun initView() {
        super.initView()
        folderBean = intent.getParcelableExtra(Constant.DATA)
        sourceFolderBean = intent.getParcelableExtra(Constant.DATA2)
        tvParentName.text = folderBean?.fullName

        tvDiskNew.setOnClickListener { newFolderDialog.show() }
        tvDiskCopy.setOnClickListener {
            showLoading()
            mViewModel.copyCloudFile(fileid = sourceFolderBean?.id, folderid = folderBean?.id)
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
        mViewModel.getPriCloudList(folderBean?.id)
    }

    private fun initAdapter() {

        mAdapterPri = DiskPriAdapter(R.layout.item_folder_pri, mPriData)
        rvDisk.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CloudCopyActivity)
            setAdapter(mAdapterPri)
        }

        mAdapterPri.setOnItemClickListener { adapter, view, position ->
            mStartActivity<CloudCopyActivity>(this) {
                var bean = mPriData[position]
                bean.fullName = folderBean?.fullName + bean.foldername
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
                    var cb: CheckBox = view as CheckBox
                    if (cb.isChecked) {
                        llBottom.visibility = View.VISIBLE
                    } else {
                        llBottom.visibility = View.GONE
                    }
                }

            }
        }
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
            mViewModel.newFileFolder(parentid = folderBean?.id, foldername = folderName)
            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    override fun initDataObserver() {
        mViewModel.mNewFolderData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                getData()
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

        mViewModel.mCopyCloudData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
            }
        })
    }
}