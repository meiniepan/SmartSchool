package com.xiaoneng.ss.module.school.view

import android.Manifest
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.permission.PermissionResult
import com.xiaoneng.ss.common.permission.Permissions
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.PropertyTypeAdapter
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import com.xiaoneng.ss.network.response.BaseResp
import kotlinx.android.synthetic.main.activity_property.*
import pub.devrel.easypermissions.AppSettingsDialog

/**
 * @author Burning
 * @description:报修报送
 * @date :2020/10/23 3:17 PM
 */
class PropertyActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: PropertyTypeAdapter
    var mData: ArrayList<PropertyTypeBean> = ArrayList()
    private val mPermissions = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )

    override fun getLayoutId(): Int {
        return R.layout.activity_property
    }

    override fun initView() {
        super.initView()
        initUI()

        tvPropertyRecord1.setOnClickListener {
            //维修记录
            mStartActivity<PropertyRecordActivity>(this) {
                putExtra(Constant.TYPE, "1")
                putExtra(Constant.DATA, mData)
            }
        }
        tvPropertyRecord2.setOnClickListener {
            //报修记录
            mStartActivity<PropertyRecordActivity>(this)
            {
                putExtra(Constant.TYPE, "0")
                putExtra(Constant.DATA, mData)
            }
        }
        initAdapter()
    }

    private fun initUI() {
        //判断是否有维修权限
        if (AppInfo.checkRule2("teacher/repairservice/listsByID")) {
            tvPropertyRecord1.visibility = View.VISIBLE
        } else {
            tvPropertyRecord1.visibility = View.GONE
        }
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        showLoading()
        mViewModel.getPropertyType()
    }

    private fun initAdapter() {

        mAdapter = PropertyTypeAdapter(R.layout.item_property_type, mData)
        rvPropertyType.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            initVoicePermission(position)
        }
    }

    private fun initVoicePermission(position: Int) {
        Permissions(this).request(*mPermissions).observe(
            this, Observer {
                when (it) {
                    is PermissionResult.Grant -> {
                        mStartActivity<AddPropertyActivity>(this) {
                            putExtra(Constant.DATA, mData[position])
                        }
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Rationale -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有录音权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Deny -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有录音权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                    }
                }
            }
        )

    }

    override fun initDataObserver() {
        mViewModel.mRepairTypeData.observe(this, Observer {
            it?.let {
                netResponseFormat<BaseResp<PropertyTypeBean>>(it)?.let { bean ->
                    bean.data?.let {
                        mData.addAll(it)
                        rvPropertyType.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}