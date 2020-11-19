package com.xiaoneng.ss.module.school.view

import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.GlideEngine
import com.xiaoneng.ss.common.utils.aliSpeech.SpeechTranscriberActivity
import com.xiaoneng.ss.module.school.adapter.AddImgAdapter
import com.xiaoneng.ss.module.school.model.RepairBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_property.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:填写报修报送
 * @date :2020/10/23 3:17 PM
 */
class AddPropertyActivity : SpeechTranscriberActivity<SchoolViewModel>() {

    private var chosenDevice: String = ""
    private var isStart = false
    lateinit var mAdapter: AddImgAdapter
    var mData: ArrayList<String> = ArrayList()

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
        var bean = RepairBean(
            token = UserInfo.getUserBean().token,
            remark = "aa"
        )
        mViewModel.addRepair(bean)
    }

    private fun uploadImg() {
        if (mData.size > 1) {
            showLoading()
            mViewModel.getSts()
        }
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                toast(R.string.deal_done)
                finish()
            }
        })
    }
}