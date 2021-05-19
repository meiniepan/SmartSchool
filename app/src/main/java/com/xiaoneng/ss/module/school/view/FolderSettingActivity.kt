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
import com.xiaoneng.ss.module.school.model.DepartmentBean
import com.xiaoneng.ss.module.school.model.DiskFileResp
import com.xiaoneng.ss.module.school.model.FolderBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
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
    var involves: ArrayList<UserBean> = ArrayList()
    var mDataDepartment = ArrayList<DepartmentBean>()
    var receiveList: ArrayList<UserBeanSimple> = ArrayList()

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
    }

    private fun doConfirm() {
//        if (involves.size <= 0) {
//            toast(R.string.lack_info)
//            return
//        }
        mViewModel.setFileFolder(
            parentid = folderBean?.parentid,
            folderid = folderBean?.id,
            involve = Gson().toJson(involves)
        )
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
                    involves.clear()
                    receiveList.clear()
                    mDataDepartment = data.getParcelableArrayListExtra(Constant.DATA)!!
                    var str = ""
                    mDataDepartment.forEach {
                        addDepartment(it)
                    }
                    if (receiveList.size > 0) {
                        receiveList.forEach {
                            str = str + it.realname + "、"
                            involves.add(UserBean(uid = it.uid, usertype = it.usertype))
                        }
                        str = str.substring(0, str.length - 1)
                    }
                        tvPeople.text = str
                }
            }
        }
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
    }
}