package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.aliSpeech.SpeechTranscriberActivity
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.school.adapter.AddImgAdapter
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.model.FileInfoBean
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import com.xiaoneng.ss.module.school.model.RepairBody
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import com.xiaoneng.ss.network.response.BaseResp
import kotlinx.android.synthetic.main.activity_achievement.*
import kotlinx.android.synthetic.main.activity_add_property.*
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:填写报修报送
 * @date :2020/10/23 3:17 PM
 */
class AddPropertyActivity : SpeechTranscriberActivity<SchoolViewModel>() {

    private var uploadNum: Int = 0
    private var chosenDevice: String = ""
    private var typeid: String? = null
    private var needShow: Boolean = true
    private var deviceid: String? = null
    private var isStart = false
    lateinit var mAdapter: AddImgAdapter
    private lateinit var dialogType: Dialog
    private lateinit var dialogDevice: Dialog
    var mPicData: ArrayList<String> = ArrayList()
    var mTypeData: ArrayList<PropertyTypeBean> = ArrayList()
    var mDeviceData: ArrayList<PropertyTypeBean> = ArrayList()
    var mUrlData: ArrayList<String> = ArrayList()
    var mData: PropertyTypeBean? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_add_property
    }

    override fun getEditText(): EditText {
        return etPropertyRemark
    }

    override fun initView() {
        super.initView()
        mData = intent.getParcelableExtra(Constant.DATA)
        tvPropertyConfirm.setOnClickListener {
            doConfirm()
        }
        tvPropertySpeech.setOnClickListener {
            if (!isStart) {
                doStart()
                tvPropertySpeech.text = "点击停止"
            } else {
                doStop()
                tvPropertySpeech.text = "点击说话"
            }
            isStart = !isStart
        }
        llType.setOnClickListener {
            dialogType.show()
        }
        llDevice.setOnClickListener {
            if (needShow&&!typeid.isNullOrEmpty()) {
                showLoading()
                mViewModel.getDeviceType(typeid)
            } else {
                dialogDevice.show()
            }
        }
        initAdapter()
        initDialog()
    }

    private fun initDialog() {
        initDialogType()
        initDialogDevice()
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        showLoading()
        mViewModel.getPropertyType(mData?.id ?: "")
    }

    private fun initAdapter() {
        mPicData.add("")
        mAdapter = AddImgAdapter(R.layout.item_add_img, mPicData)
        rvImg.apply {
            layoutManager = GridLayoutManager(context, 3)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (position == mPicData.size - 1) {
                PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .maxSelectNum(5)
                    .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                    .forResult(object : OnResultCallbackListener<LocalMedia> {
                        override fun onResult(result: MutableList<LocalMedia>?) {
                            result?.forEach {
                                mPicData.add(mPicData.size - 1, it.realPath)
                            }

                            mAdapter.notifyDataSetChanged()
                        }

                        override fun onCancel() {

                        }

                    })
            }
        }
    }

    private fun doConfirm() {
        uploadImg()
    }

    private fun uploadImg() {
        if (mPicData.size > 1) {
            showLoading()
            mViewModel.getSts()
        } else {
            doRealConfirm()
        }
    }

    private fun doUpload(it: StsTokenResp, avatarPath: String) {
        showLoading()
        var mId: String = avatarPath.split("/")?.last()
        var objectKey =
            getOssObjectKey(UserInfo.getUserBean().usertype, UserInfo.getUserBean().uid, mId)
        OssUtils.asyncUploadFile(
            this,
            it.Credentials,
            objectKey,
            avatarPath,
            object : OssListener {
                override fun onSuccess() {
                    mRootView.post {
                        uploadNum++
                        mUrlData.add(objectKey)
                        if (uploadNum == mPicData.size - 1) {
                            doRealConfirm()
                        }

                    }
                }

                override fun onFail() {
                    mRootView.post {
                        showSuccess()
                        mToast("上传失败")
                    }
                }

            })
    }

    private fun doRealConfirm() {
        var fileInfo = ArrayList<FileInfoBean>()
        if (mUrlData.size > 0) {
            mUrlData.forEach {
                fileInfo.add(FileInfoBean(url = it))
            }
        }
        if (etPropertyRemark.text.toString()
                .isNullOrEmpty() || typeid.isNullOrEmpty() || deviceid.isNullOrEmpty()
        ) {
            showTip(getString(R.string.lack_info))
            return
        }
        var bean = RepairBody(
            typeid = typeid,
            deviceid = deviceid,
            token = UserInfo.getUserBean().token,
            remark = etPropertyRemark.text.toString(),
            addr = etPropertyAddr.text.toString(),
            fileinfo = Gson().toJson(fileInfo)
        )
        mViewModel.addRepair(bean)
    }

    private fun initDialogType() {
        var titles = ArrayList<String>()
        mTypeData.forEach {
            titles.add(it.name ?: "")
        }
        // 底部弹出对话框
        dialogType =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        dialogType.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        dialogType.window!!.setGravity(Gravity.BOTTOM)
        dialogType.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titles)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            needShow = false
            setTypeV(position)
            showLoading()
            mViewModel.getDeviceType(typeid)
            dialogType.dismiss()
        }

    }

    private fun setTypeV(position: Int) {
        tvType.text = mTypeData[position].name
        typeid = mTypeData[position].id
    }

    private fun initDialogDevice() {
        var titles = ArrayList<String>()
        mDeviceData.forEach {
            titles.add(it.name ?: "")
        }
        // 底部弹出对话框
        dialogDevice =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        dialogDevice.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        dialogDevice.window!!.setGravity(Gravity.BOTTOM)
        dialogDevice.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titles)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            setDeviceV(position)
            dialogDevice.dismiss()
        }

    }

    private fun setDeviceV(position: Int) {
        tvDevice.text = mDeviceData[position].name
        deviceid = mDeviceData[position].id
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                mToast(R.string.deal_done)
                finish()
            }
        })

        mViewModel.mRepairTypeData.observe(this, Observer {
            it?.let {
                netResponseFormat<BaseResp<PropertyTypeBean>>(it)?.let { bean ->
                    bean.data?.let {
                        if (it.size > 0) {
                            mTypeData.clear()
                            mTypeData.addAll(it)
                            initDialogType()
                            setTypeV(0)
                        }
                    }
                }
            }
        })

        mViewModel.mDeviceTypeData.observe(this, Observer {
            it?.let {
                netResponseFormat<BaseResp<PropertyTypeBean>>(it)?.let { bean ->
                    bean.data?.let {
                        if (it.size > 0) {
                            mDeviceData.clear()
                            mDeviceData.addAll(it)
                            initDialogDevice()
                            if (needShow) {
                                dialogDevice.show()
                                needShow = false
                            } else {
                                setDeviceV(0)
                            }
                        }
                    }
                }
            }
        })

        mViewModel.mStsData.observe(this, Observer { response ->
            response?.let { bean ->
                Handler().postDelayed(
                    {

                        GlobalScope.launch() {
                            async {
                                uploadNum = 0
                                mUrlData.clear()
                                mPicData.forEach {
                                    if (it.isNotEmpty()) {
                                        doUpload(bean, it)
                                    }
                                }
                            }
                        }
                    }, 100
                )
            }
        })
    }
}