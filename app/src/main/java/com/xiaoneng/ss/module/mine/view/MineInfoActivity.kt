package com.xiaoneng.ss.module.mine.view

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.UserBean
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import kotlinx.android.synthetic.main.activity_mine_info.*
import org.jetbrains.anko.toast
import java.io.File

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class MineInfoActivity : BaseLifeCycleActivity<AccountViewModel>() {

    private var avatarPath: String? = ""
    val mDownloadData: MutableLiveData<Boolean> = MutableLiveData()
    var isDownLoad: Boolean = false

    //    private val OBJECT_KEY = "avatar/"
    override fun getLayoutId(): Int = R.layout.activity_mine_info


    override fun initView() {
        super.initView()
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
        if (!TextUtils.isEmpty(UserInfo.getUserBean().portrait)) {
            if (File(mDownloadFile(this, UserInfo.getUserBean().portrait)).exists()) {
                displayImage(
                    this@MineInfoActivity,
                    mDownloadFile(
                        this@MineInfoActivity,
                        UserInfo.getUserBean().portrait
                    ),
                    ivAvatarMineInfo
                )
            } else {
                isDownLoad = true
                mViewModel.getSts()
            }
        }
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

        mViewModel.mUserInfoData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                UserInfo.modifyAvatar(response.portrait)
                toast("头像上传成功")
                displayImage(
                    this@MineInfoActivity, avatarPath,
                    ivAvatarMineInfo
                )
            }
        })

    }

    private fun doUpload(it: StsTokenResp) {
        showLoading()
        var mId: String = System.currentTimeMillis().toString()
        OssUtils.asyncUploadFile(
            this@MineInfoActivity,
            it.Credentials,
            Constant.OBJECT_KEY + mId,
            avatarPath,
            object : OssListener {
                override fun onSuccess() {
                    mRootView.post {
                        mViewModel.modifyUserInfo(
                            UserBean(
                                UserInfo.getUserBean().token,
                                portrait = mId
                            )
                        )

                    }

                }

                override fun onFail() {
                    mRootView.post {
                        toast("头像上传失败")
                    }
                }

            })
    }

    private fun doDownload(it: StsTokenResp) {

        showLoading()
        OssUtils.downloadFile(
            this@MineInfoActivity,
            it.Credentials,
            Constant.OBJECT_KEY + UserInfo.getUserBean().portrait,
            mDownloadFile(this, UserInfo.getUserBean().portrait),
            object : OssListener {

                override fun onFail() {
                    showSuccess()
                    mRootView.post {
                        toast("头像下载失败")
                    }
                }

                override fun onSuccess() {
                    mRootView.post {
                        showSuccess()

                        displayImage(
                            this@MineInfoActivity,
                            mDownloadFile(
                                this@MineInfoActivity,
                                UserInfo.getUserBean().portrait
                            ),
                            ivAvatarMineInfo
                        )
                    }
                }
            })

    }


}