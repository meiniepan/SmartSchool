package com.xiaoneng.ss.module.activity

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.WindowManager
import androidx.lifecycle.Observer
import cn.jzvd.Jzvd
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.AppBean
import com.xiaoneng.ss.account.viewmodel.AccountViewModel
import com.xiaoneng.ss.base.view.BaseActivity
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.mainLogin
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.model.FolderBean
import kotlinx.android.synthetic.main.activity_video.*

/**
 * @author Burning
 * @description:
 * @date :2021/6/6 3:02 PM
 */
class VideoActivity : BaseActivity() {
    var folderBean: FolderBean? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_video
    }

    override fun initView() {
        super.initView()
        folderBean = intent.getParcelableExtra(Constant.DATA)
    }

    override fun initData() {
        super.initData()
        jz_video.setUp(folderBean?.path,folderBean?.filename)
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos();
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}