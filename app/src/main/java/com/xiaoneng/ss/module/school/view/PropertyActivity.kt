package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.PropertyTypeAdapter
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import com.xiaoneng.ss.network.response.BaseResp
import kotlinx.android.synthetic.main.activity_property.*
import kotlinx.android.synthetic.main.activity_property_record.*

/**
 * @author Burning
 * @description:报修报送
 * @date :2020/10/23 3:17 PM
 */
class PropertyActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: PropertyTypeAdapter
    var mData: ArrayList<PropertyTypeBean> = ArrayList()
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
            }
        }
        tvPropertyRecord2.setOnClickListener {
            //报修记录
            mStartActivity<PropertyRecordActivity>(this)
            {
                putExtra(Constant.TYPE, "0")
            }
        }
        initAdapter()
    }

    private fun initUI() {
        //判断是否有维修权限
        if (AppInfo.checkRule("admin/repair/default")) {
        } else {
        }
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mViewModel.getPropertyType()
    }

    private fun initAdapter() {

        mAdapter = PropertyTypeAdapter(R.layout.item_property_type, mData)
        rvPropertyType.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mStartActivity<AddPropertyActivity>(this)
        }
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
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