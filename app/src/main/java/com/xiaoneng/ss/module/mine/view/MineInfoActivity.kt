package com.xiaoneng.ss.module.mine.view

import android.text.TextUtils
import android.view.View
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
 * @date: 2020/08/27
 * Time: 17:01
 */
class MineInfoActivity : BaseLifeCycleActivity<AccountViewModel>() {
    val mAvatarFileName:String = UserInfo.getUserBean().portrait.split("/").last()
    private var fileName: String? = ""
    private var avatarPath: String? = ""
    var isDownLoad: Boolean = false

    override fun getLayoutId(): Int = R.layout.activity_mine_info


    override fun initView() {
        super.initView()
        var bean = UserInfo.getUserBean()
        var name = bean.realname
        var phone = bean.phone


        ivAvatarMineInfo.setOnClickListener {
            choosePic()
        }

        tvConfirm.setOnClickListener {
            doConfirm()
        }

        when (UserInfo.getUserBean().usertype) {

            "1" -> {
                if (UserInfo.getUserBean().logintype == Constant.LOGIN_TYPE_STU) {
                    llMineItem6.visibility = View.GONE
                } else {
                    name = bean.parentname
                    phone = bean.parentphone
                    llMineItem2.visibility = View.GONE
                    llMineItem3.visibility = View.GONE
                    llMineItem5.visibility = View.GONE
                    llMineItem6.visibility = View.GONE
                    llMineItem7.visibility = View.GONE
                    llMineItem8.visibility = View.GONE
                    ivAvatarMineInfo.isClickable = false
                }
            }
            "2" -> {
                llMineItem5.visibility = View.GONE
            }

            "99" -> {
                llMineItem5.visibility = View.GONE
            }
            else -> {

            }
        }
        tvNameMineInfo.text = name
        etMineItem1.setText(name)
        tvMineItem4.text = starPhoneNum(phone)
    }

    private fun doConfirm() {
        var bean = UserInfo.getUserBean()
        bean.realname = etMineItem1.text.toString()
        showLoading()
        mViewModel.modifyUserInfo(bean)
    }

    override fun initData() {
        super.initData()
        initAvatar()

    }

    private fun initAvatar() {
        if (!TextUtils.isEmpty(mAvatarFileName)) {
            if (File(mDownloadFile(this, mAvatarFileName)).exists()) {
                displayImage(
                    this@MineInfoActivity,
                    mDownloadFile(
                        this@MineInfoActivity,
                        mAvatarFileName
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
                    fileName = result!![0].fileName
                    mViewModel.getSts()

                }

                override fun onCancel() {

                }

            })
    }


    private fun doUpload(it: StsTokenResp) {
        showLoading()
        var mId: String = System.currentTimeMillis().toString()+"_"+fileName
        OssUtils.asyncUploadFile(
            this@MineInfoActivity,
            it.Credentials,
            getOssObjectKey(UserInfo.getUserBean().usertype,UserInfo.getUserBean().uid,mId),
            avatarPath,
            object : OssListener {
                override fun onSuccess() {
                    mRootView.post {
                        mViewModel.modifyAvatar(
                            UserBean(
                                UserInfo.getUserBean().token,
                                portrait = getOssObjectKey(UserInfo.getUserBean().usertype,UserInfo.getUserBean().uid,mId)
                            )
                        )

                    }

                }

                override fun onFail() {
                    mRootView.post {
                        showSuccess()
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
            UserInfo.getUserBean().portrait,
            mDownloadFile(this, mAvatarFileName),
            object : OssListener {

                override fun onFail() {
                    mRootView.post {
                        showSuccess()
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
                                mAvatarFileName
                            ),
                            ivAvatarMineInfo
                        )
                    }
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

        mViewModel.mAvatarData.observe(this, Observer { response ->
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

        mViewModel.mUserInfoData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                UserInfo.modifyUserBean(it)
                toast("修改成功")
                finish()
            }
        })

    }
}