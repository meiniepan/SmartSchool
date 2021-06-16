package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.PropertyTypeAdapter
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import com.xiaoneng.ss.network.response.BaseResp
import kotlinx.android.synthetic.main.activity_property.rvPropertyType
import kotlinx.android.synthetic.main.activity_quantize.*

/**
 * @author Burning
 * @description:报修报送
 * @date :2020/10/23 3:17 PM
 */
class QuantizeActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: PropertyTypeAdapter
    var mData: ArrayList<PropertyTypeBean> = ArrayList()


    override fun getLayoutId(): Int {
        return R.layout.activity_quantize
    }

    override fun initView() {
        super.initView()
        tvSanitation.setOnClickListener {
            mStartActivity<QuantizeTypeActivity>(this)
        }
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