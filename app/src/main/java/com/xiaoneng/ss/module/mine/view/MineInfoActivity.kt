package com.xiaoneng.ss.module.mine.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.GlideEngine
import com.xiaoneng.ss.common.utils.Oss.OssListener
import com.xiaoneng.ss.common.utils.Oss.OssUtils
import com.xiaoneng.ss.common.utils.displayImage
import com.xiaoneng.ss.common.utils.mDownloadFile
import com.xiaoneng.ss.common.utils.starPhoneNum
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.module.mine.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.activity_mine_info.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class MineInfoActivity : BaseLifeCycleActivity<MineViewModel>() {

    private var avatarPath: String? = ""
    val mDownloadData: MutableLiveData<Boolean> = MutableLiveData()
    var isDownLoad: Boolean = false
    private val OBJECT_KEY = "avatar/student/id/avatar"
    override fun getLayoutId(): Int = R.layout.activity_mine_info


    override fun initView() {
        super.initView()
        displayImage(this, UserInfo.getUserBean().portrait, ivAvatarMineInfo)
        tvNameMineInfo.text = UserInfo.getUserBean().realname
        tvMineItem1.text = UserInfo.getUserBean().realname
        tvMineItem4.text = starPhoneNum(UserInfo.getUserBean().phone)

        ivAvatarMineInfo.setOnClickListener {
            choosePic()
        }

    }

    override fun initData() {
        super.initData()
        initAvatar()

    }

    private fun initAvatar() {
        isDownLoad = true
        mViewModel.getSts()
    }

    private fun choosePic() {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .maxSelectNum(1)
            .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    isDownLoad = false
                    avatarPath = result!![0].realPath
                    mViewModel.getSts()

                }

                override fun onCancel() {

                }

            })
    }

    override fun initDataObserver() {
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

    private fun doUpload(it: StsTokenResp) {
        showLoading()
        OssUtils.asyncUploadFile(
            this@MineInfoActivity,
            it.Credentials,
            OBJECT_KEY,
            avatarPath,
            object : OssListener {
                override fun onSuccess(filePath: String) {
                    mRootView.post {
                        showSuccess()
                        toast("头像上传成功")
                        displayImage(
                            this@MineInfoActivity, avatarPath,
                            ivAvatarMineInfo
                        )
                    }

                }

                override fun onFail() {
                    mRootView.post {
                        toast("头像上传失败")
                    }
                }

                override fun onSuccess2(filePath: ByteArray?) {

                }
            })
    }

    private fun doDownload(it: StsTokenResp) {
        showLoading()
        OssUtils.downloadFile(
            this@MineInfoActivity,
            it.Credentials,
            OBJECT_KEY,
            mDownloadFile(this),
            object : OssListener {
                override fun onSuccess(filePath: String) {


                }

                override fun onFail() {
                    mRootView.post {
                        toast("头像下载失败")
                    }
                }

                override fun onSuccess2(filePath: ByteArray?) {
                    mRootView.post {
                        showSuccess()
                        displayImage(
                            this@MineInfoActivity, filePath,
                            ivAvatarMineInfo
                        )
                    }
                }
            })
    }


}