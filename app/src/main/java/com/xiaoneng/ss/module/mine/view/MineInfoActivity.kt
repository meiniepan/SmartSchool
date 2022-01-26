package com.xiaoneng.ss.module.mine.view

import android.os.Handler
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.UserBean
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import kotlinx.android.synthetic.main.activity_mine_info.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class MineInfoActivity : BaseLifeCycleActivity<AccountViewModel>() {
    val mAvatarFileName: String? = UserInfo.getUserBean().portrait?.split("/")?.last()
    private var fileName: String? = ""
    private var birthday: String? = UserInfo.getUserBean().birthday
    private var sex: String? = UserInfo.getUserBean().sex
    private var avatarPath: String? = ""

    override fun getLayoutId(): Int = R.layout.activity_mine_info


    override fun initView() {
        super.initView()
//        if (!isSystemWhiteList()){
//            mAlert("为不影响使用，请把智慧校园加入系统白名单"){}
//        }
        initPermission(mPermissionsCam)
        var bean = UserInfo.getUserBean()
        var name = bean.realname
        var phone = bean.phone


        ivAvatarMineInfo.setOnClickListener {
            choosePic()
        }


        tvMineItem2.apply {
            setOnClickListener {
                showSexPick(this) {
                    sex = getSexInt(this)
                    var bean = UserBean()
                    bean.token = UserInfo.getUserBean().token
                    bean.sex = sex
                    showLoading()
                    mViewModel.modifyUserInfoSex(bean)
                }
            }
        }
        tvMineItem3.apply {
            setOnClickListener {
                showBirthDayPick(this) {
                    birthday = this
                    var bean = UserBean()
                    bean.token = UserInfo.getUserBean().token
                    bean.birthday = birthday
                    showLoading()
                    mViewModel.modifyUserInfoBirth(bean)
                }
            }
        }

        etMineItem1.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_GO -> {
                    if (etMineItem1.text.toString().trim().isNotEmpty()) {
                        showLoading()
                        mViewModel.modifyParentName(etMineItem1.text.toString())
                    }
                }

            }
            return@setOnEditorActionListener false
        }

        tvMineItem6.setOnEditorActionListener { teew, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_GO -> {
                    var bean = UserBean()
                    bean.token = UserInfo.getUserBean().token
                    bean.idcard = tvMineItem6.text.toString()
                    showLoading()
                    mViewModel.modifyUserInfoWx(bean)
                }

            }
            return@setOnEditorActionListener false
        }

        when (UserInfo.getUserBean().usertype) {

            "1" -> {
                if (UserInfo.getUserBean().logintype == Constant.LOGIN_TYPE_STU) {
                    llMineItem6.visibility = View.GONE
                } else {
                    etMineItem1.isEnabled = true
                    name = bean.parentname
                    phone = bean.parentphone
                    llMineItem2.visibility = View.GONE
                    llMineItem3.visibility = View.GONE
                    llMineItem5.visibility = View.GONE
                    llMineItem51.visibility = View.GONE
                    llMineItem6.visibility = View.GONE
                    llMineItem7.visibility = View.GONE
                    llMineItemEduId.visibility = View.GONE
                    ivAvatarMineInfo.isClickable = false
                }
            }
            "2" -> {
                //生日、身份证号隐藏
                llMineItem3.visibility = View.GONE
                llMineItem6.visibility = View.GONE

                llMineItem5.visibility = View.GONE
                llMineItem51.visibility = View.GONE
                llMineItem7.visibility = View.GONE
                llMineItemEduId.visibility = View.GONE
            }

            "99" -> {
                //生日、身份证号隐藏
                llMineItem3.visibility = View.GONE
                llMineItem6.visibility = View.GONE

                llMineItem5.visibility = View.GONE
                llMineItem51.visibility = View.GONE
                llMineItem7.visibility = View.GONE
                llMineItemEduId.visibility = View.GONE
            }
            else -> {

            }
        }
        etMineItem1.setText(name)
        tvMineItem2.setText(getSexString(UserInfo.getUserBean().sex))
        tvMineItem3.setText(UserInfo.getUserBean().birthday)
        tvMineItem4.text = formatStarPhoneNum(phone)
        tvMineItem5.text = UserInfo.getUserBean().cno
        tvMineItem51.text = UserInfo.getUserBean().levelclass
        tvMineItem6.setText(UserInfo.getUserBean().idcard)
        tvMineItem7.text = UserInfo.getUserBean().sno
        tvMineItemEduId.text = UserInfo.getUserBean().eduid

    }


    override fun initData() {
        super.initData()
        if (UserInfo.getUserBean().usertype == "1" &&
            UserInfo.getUserBean().logintype == Constant.LOGIN_TYPE_PAR
        ) {

        } else {
            initAvatar()
        }

    }

    private fun initAvatar() {
        if (!UserInfo.getUserBean().portrait.isNullOrEmpty()) {
            displayImage(
                this,
                UserInfo.getUserBean().domain + UserInfo.getUserBean().portrait,
                ivAvatarMineInfo
            )
        }
    }

    private fun choosePic() {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .maxSelectNum(1)
            .imageEngine(GlideEngine.createGlideEngine())
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    avatarPath = result!![0].realPath
                    fileName = avatarPath?.split("/")?.last()
                    if (!avatarPath.isNullOrEmpty()) {
                        mViewModel.getSts()
                    }
                }

                override fun onCancel() {

                }

            })
    }


    private fun doUpload(it: StsTokenResp) {

        showLoading()
        var mId: String = System.currentTimeMillis().toString() + "_" + fileName
        var bitmapPath = mBitmap2Local(
            Glide.with(this)
                .asBitmap()
                .load(avatarPath)
                .submit(200, 200)
                .get(),
            fileName ?: ""
        )
        var objectKey =
            getOssObjectKey(UserInfo.getUserBean().usertype, UserInfo.getUserBean().uid, mId)
        OssUtils.asyncUploadFile(
            this@MineInfoActivity,
            it.Credentials,
            objectKey,
            bitmapPath,
            object : OssListener {
                override fun onSuccess() {
                    mRootView.post {
                        showSuccess()
                        mViewModel.modifyAvatar(
                            UserBean(
                                UserInfo.getUserBean().token,
                                portrait = objectKey
                            )
                        )

                    }

                }

                override fun onFail() {
                    mRootView.post {
                        showSuccess()
                        mToast("头像上传失败")
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
                        mToast("头像下载失败")
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

    fun getSexString(a: String?): String {
        return when (a) {
            "0" -> "未知"
            "1" -> "男"
            "2" -> "女"
            else -> "未知"
        }
    }

    fun getSexInt(a: String): String {
        return when (a) {
            "未知" -> "0"
            "男" -> "1"
            "女" -> "2"
            else -> "0"
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

        mViewModel.mAvatarData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                var userBean = UserInfo.getUserBean()
                userBean.portrait = response.portrait
                UserInfo.modifyUserBean(userBean)
                mToast("头像上传成功")
                displayImage(
                    this@MineInfoActivity, avatarPath,
                    ivAvatarMineInfo
                )
            }
        })

        mViewModel.mUserInfoData.observe(this, Observer { response ->
            response?.let {
                UserInfo.modifyUserBean(it)
                mToast("修改成功")
            }
        })

        mViewModel.mParentNameData.observe(this, Observer { response ->
            response?.let {
                var bean = UserInfo.getUserBean()
                bean.parentname = etMineItem1.text.toString()
                UserInfo.modifyUserBean(bean)
                mToast("修改成功")
            }
        })

        mViewModel.mUserInfoDataBirth.observe(this, Observer { response ->
            response?.let {
                var bean = UserInfo.getUserBean()
                bean.birthday = it.birthday
                UserInfo.modifyUserBean(bean)
                mToast("修改成功")
            }
        })

        mViewModel.mUserInfoDataSex.observe(this, Observer { response ->
            response?.let {
                var bean = UserInfo.getUserBean()
                bean.sex = it.sex
                UserInfo.modifyUserBean(bean)
                mToast("修改成功")
            }
        })

        mViewModel.mUserInfoDataWx.observe(this, Observer { response ->
            response?.let {
                var bean = UserInfo.getUserBean()
                bean.idcard = it.idcard
                UserInfo.modifyUserBean(bean)
                mToast("修改成功")
            }
        })

    }
}