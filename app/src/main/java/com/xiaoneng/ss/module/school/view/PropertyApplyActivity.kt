package com.xiaoneng.ss.module.school.view

import androidx.recyclerview.widget.GridLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.module.school.adapter.ChooseDeviceAdapter
import com.xiaoneng.ss.module.school.model.DeviceBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_property_apply.*

/**
 * @author Burning
 * @description:填写报修报送
 * @date :2020/10/23 3:17 PM
 */
class PropertyApplyActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: ChooseDeviceAdapter
    val mData = arrayListOf(
        DeviceBean("计算机"),
        DeviceBean("笔记本"),
        DeviceBean("打印机"),
        DeviceBean("网络维修"),
        DeviceBean("电教协助"),
        DeviceBean("其他")
    )
    private var chosenDevice: String = mData[0].name ?: ""

    override fun getLayoutId(): Int {
        return R.layout.activity_property_apply
    }

    override fun initView() {
        super.initView()
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
        mAdapter = ChooseDeviceAdapter(R.layout.item_property_device, mData)

        rvPropertyDevice.apply {
            layoutManager = GridLayoutManager(context, 3)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            for (i in mData) {
                if (i.isCheck) {
                    i.isCheck = false
                }
            }
            mData[position].isCheck = true
            adapter.notifyDataSetChanged()
            chosenDevice = mData[position].name ?: ""
        }


    }

    override fun initDataObserver() {

    }
}