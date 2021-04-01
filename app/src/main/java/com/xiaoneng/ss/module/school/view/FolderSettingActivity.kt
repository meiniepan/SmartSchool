package com.xiaoneng.ss.module.school.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.UserBean
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.FileTransInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.oss.OssListener
import com.xiaoneng.ss.common.utils.oss.OssUtils
import com.xiaoneng.ss.model.StsTokenResp
import com.xiaoneng.ss.model.TestCourseResp
import com.xiaoneng.ss.module.school.adapter.DiskAdapter
import com.xiaoneng.ss.module.school.adapter.DiskPriAdapter
import com.xiaoneng.ss.module.school.adapter.DiskPubAdapter
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_involve.*
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.activity_cloud_disk.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import java.io.File


/**
 * @author Burning
 * @description:教学云盘
 * @date :2021/03/30 3:17 PM
 */
class FolderSettingActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    var folderBean: FolderBean? = null
    var involves: ArrayList<UserBean> = ArrayList()



    override fun getLayoutId(): Int {
        return R.layout.activity_folder_setting
    }

    override fun initView() {
        super.initView()
        folderBean = intent.getParcelableExtra(Constant.DATA)
        tvConfirm.setOnClickListener {
            doConfirm()
        }
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
    }

    private fun doConfirm() {
        involves.add(UserBean(uid = "12",usertype = "2"))
        mViewModel.setFileFolder(parentid = folderBean?.parentid,folderid = folderBean?.id,involve = Gson().toJson(involves))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) { //是否选择，没选择就不会继续

        }

    }

    override fun initDataObserver() {


        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<DiskFileResp>(it)?.let {

                }

            }
        })
    }
}