package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.school.adapter.DiskPriAdapter
import com.xiaoneng.ss.module.school.model.DiskFileResp
import com.xiaoneng.ss.module.school.model.FolderBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_cloud_copy.*
import kotlinx.android.synthetic.main.custom_title_bar.*
import org.jetbrains.anko.toast


/**
 * @author Burning
 * @description:教学云盘文件、文件夹复制移动
 * @date :2021/4/9 3:17 PM
 */
class CloudCopyActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapterPri: DiskPriAdapter
    var mPriData: ArrayList<FolderBean> = ArrayList()
    var fullPathData: ArrayList<FolderBean> = ArrayList()
    var fullPath: String? = null
    var fileName: String? = null
    var cloudType: String = "1"//1私有云  2共享
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
        sourceFolderBean = intent.getParcelableExtra(Constant.DATA)
        initFullPath()
        tvDiskNew.setOnClickListener { newFolderDialog.show() }
        tvDiskCopy.setOnClickListener {
            showLoading()
            mViewModel.copyCloudFile(fileid = sourceFolderBean?.id, folderid = folderBean?.id)
        }
        tvCopyCancel.setOnClickListener {
            finish()
        }
        initOriginalData()
        initAdapter()
    }

    private fun initOriginalData() {
        mPriData.clear()
        mPriData.add(FolderBean(foldername = "私有云", isFolder = true))
        mPriData.add(FolderBean(foldername = "共享云", isFolder = true))
    }

    private fun initFullPath() {
        fullPath = null
        if (fullPathData.size > 0) {
            fullPathData.forEach {
                fullPath = it.foldername + ">"
            }

        }
        if (fullPath.isNullOrEmpty()) {
            tvParentName.visibility = View.GONE
        } else {
            tvParentName.visibility = View.VISIBLE
            tvParentName.text = fullPath
        }
    }

    override fun onBackPressed() {
        doBack()
    }

    private fun doBack() {
        if (fullPathData.size > 0) {
            fullPathData.removeAt(fullPathData.size - 1)
            folderBean = if (fullPathData.size > 0) {
                fullPathData.get(fullPathData.size - 1)
            }else{
                null
            }
            initFullPath()
            getData()
        } else {
            finish()
        }
    }

    override fun initData() {
        super.initData()
    }

    override fun getData() {
        super.getData()
            rvDisk.showLoadingView()
            if (cloudType == "1") {
                mViewModel.getPriCloudList(folderBean?.id)
            } else {
                mViewModel.getPubCloudList(folderBean?.id)
            }
    }

    private fun initAdapter() {

        mAdapterPri = DiskPriAdapter(R.layout.item_folder_pri, mPriData)
        rvDisk.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CloudCopyActivity)
            setAdapter(mAdapterPri)
        }

        mAdapterPri.setOnItemClickListener { adapter, view, position ->
            folderBean = mPriData[position]
            fullPathData.add(folderBean!!)
            if (mPriData[position].id.isNullOrEmpty()) {
                if (mPriData[position].foldername == "私有云") {
                    cloudType = "1"
                } else if (mPriData[position].foldername == "共享云") {
                    cloudType = "2"
                }

            } else {

            }
            initFullPath()
            getData()
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

        mViewModel.mPubCloudData.observe(this, Observer { response ->
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
                finish()
            }
        })
    }
}