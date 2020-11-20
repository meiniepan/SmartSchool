package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.PropertyRecordAdapter
import com.xiaoneng.ss.module.school.model.PropertyDetailBean
import com.xiaoneng.ss.module.school.model.PropertyDetailResp
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_property_record.*

/**
 * @author Burning
 * @description:填写报修报送
 * @date :2020/10/28 3:17 PM
 */
class PropertyRecordActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: PropertyRecordAdapter
    var mData: ArrayList<PropertyDetailBean> = ArrayList()
    private var mType: String? = null//0报修 1维修

    override fun getLayoutId(): Int {
        return R.layout.activity_property_record
    }

    override fun initView() {
        super.initView()
        mType = intent.getStringExtra(Constant.TYPE)
        initAdapter()
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        showLoading()
        mViewModel.getPropertyRecord()
    }

    private fun initAdapter() {
        mAdapter = PropertyRecordAdapter(R.layout.activity_property_detail, mData)
        mAdapter.setType(mType)
        rvPropertyRecord.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mStartActivity<AddBookSiteActivity>(this)
        }
    }

    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                netResponseFormat<PropertyDetailResp>(it)?.let { bean ->
                    bean.data?.let {
                        mData.addAll(it)
                        rvPropertyRecord.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}