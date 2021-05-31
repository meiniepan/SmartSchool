package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.xiaoneng.ss.R
import com.xiaoneng.ss.account.model.UserBean
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartForResult
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_folder_setting.*
import org.jetbrains.anko.toast


/**
 * @author Burning
 * @description:教学云盘
 * @date :2021/03/30 3:17 PM
 */
class FolderSettingActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    var folderBean: FolderBean? = null
    var involves: ArrayList<UserBeanSimple> = ArrayList()
    var mDataDepartment = ArrayList<DepartmentBean>()
    var receiveList: ArrayList<UserBeanSimple> = ArrayList()
    var isFirst = true

    override fun getLayoutId(): Int {
        return R.layout.activity_folder_setting
    }

    override fun initView() {
        super.initView()
        folderBean = intent.getParcelableExtra(Constant.DATA)
        tvConfirm.setOnClickListener {
            doConfirm()
        }
        llInvite.setOnClickListener {
            mStartForResult<AddInvolveActivity>(this, Constant.REQUEST_CODE_COURSE) {
                putExtra(Constant.DATA, mDataDepartment)
                //从草稿箱第一次选择参与人，传入原有参与人数据
                if (isFirst) {
                    if (receiveList.size > 0) {
                        putExtra(Constant.DATA3, receiveList)
                    }
                }
                //从草稿箱第一次选择参与人，传入原有参与人数据
                putExtra(Constant.TYPE, 1)
            }
        }
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mViewModel.getAuthUsers(folderBean?.folderid)
    }

    private fun doConfirm() {
//        if (involves.size <= 0) {
//            toast(R.string.lack_info)
//            return
//        }
        mViewModel.cancelFolderAuth(folderBean?.folderid)

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constant.REQUEST_CODE_COURSE) {
                if (data != null) {
                    isFirst = false
                    involves.clear()
                    receiveList.clear()
                    mDataDepartment = data.getParcelableArrayListExtra(Constant.DATA)!!
                    mDataDepartment.forEach {
                        addDepartment(it)
                    }
                    dealData()
                }
            }
        }
    }

    private fun dealData() {
        var str = ""
        if (receiveList.size > 0) {
            receiveList.forEach {
                str = str + it.realname + "、"
                involves.add(it)
            }
            str = str.substring(0, str.length - 1)
        }
        tvPeople.text = str
    }

    private fun addDepartment(it: DepartmentBean) {
        if (it.num!!.toInt() > 0) {
            it.list.forEach {
                receiveList.add(
                    UserBeanSimple(
                        uid = it.uid,
                        realname = it.realname,
                        classid = it.classid,
                        usertype = it.usertype
                    )
                )
            }
        }
    }

    override fun initDataObserver() {


        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                finish()
            }
        })

        mViewModel.mCancelFolderAuthData.observe(this, Observer { response ->
            response?.let {
                mViewModel.setFileFolder(
                    parentid = folderBean?.parentid,
                    folderid = folderBean?.id,
                    involve = Gson().toJson(involves)
                )
            }
        })

        mViewModel.mAuthUsersData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<ArrayList<UserBeanSimple>>(it)?.let {
                    if (it.size>0){
                        isFirst = true
                        receiveList.clear()
                        it.forEach {
                            var bean = it
                            if (it.deps?.size?:0>0){
                                bean.topdepartid = it.deps!![0].topdepartid
                                bean.secdepartid = it.deps!![0].secdepartid
                            }
                            receiveList.add(bean)
                        }
                        dealData()
                    }
                }
            }
        })
    }
}