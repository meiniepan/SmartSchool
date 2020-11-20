package com.xiaoneng.ss.module.school.view

import android.os.Handler
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.GlideEngine
import com.xiaoneng.ss.common.utils.aliSpeech.SpeechTranscriberActivity
import com.xiaoneng.ss.common.utils.getOssObjectKey
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.school.adapter.AddImgAdapter
import com.xiaoneng.ss.module.school.model.FileInfoBean
import com.xiaoneng.ss.module.school.model.RepairBody
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_property.*
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
    private var isStart = false
    lateinit var mAdapter: AddImgAdapter
    var mData: ArrayList<String> = ArrayList()
    var mUrlData: ArrayList<String> = ArrayList()

    override fun getLayoutId(): Int {
        return R.layout.activity_add_property
    }

    override fun getEditText(): EditText {
        return etPropertyRemark
    }

    override fun initView() {
        super.initView()
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
        initAdapter()

    }


    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
    }

    private fun initAdapter() {
        mData.add("")
        mAdapter = AddImgAdapter(R.layout.item_add_img, mData)
        rvImg.apply {
            layoutManager = GridLayoutManager(context, 3)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (position == mData.size - 1) {
                PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .maxSelectNum(5)
                    .imageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                    .forResult(object : OnResultCallbackListener<LocalMedia> {
                        override fun onResult(result: MutableList<LocalMedia>?) {
                            result?.forEach {
                                mData.add(mData.size - 1, it.realPath)
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
        if (mData.size > 1) {
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
                        if (uploadNum == mData.size) {
                            doRealConfirm()
                        }

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

    private fun doRealConfirm() {
        var fileInfo = ArrayList<FileInfoBean>()
        var fileInfoStr:String?=null
        if (mUrlData.size > 0) {
            mUrlData.forEach {
                fileInfo.add(FileInfoBean(url = it))
            }
            fileInfoStr = Gson().toJson(fileInfo)
        }

        var bean = RepairBody(
            token = UserInfo.getUserBean().token,
            remark = etPropertyRemark.text.toString(),
            fileinfo = Gson().toJson(fileInfo)
        )
        mViewModel.addRepair(bean)
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                toast(R.string.deal_done)
                finish()
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
                                mData.removeAt(mData.size - 1)
                                mData.forEach {
                                    doUpload(bean, it)
                                }
                            }
                        }
                    }, 100
                )
            }
        })
    }
}