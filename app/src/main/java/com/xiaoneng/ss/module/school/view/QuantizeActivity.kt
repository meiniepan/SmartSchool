package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.QuantizeAdapter
import com.xiaoneng.ss.module.school.model.QuantizeTypeBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import com.xiaoneng.ss.network.response.BaseResp
import kotlinx.android.synthetic.main.activity_quantize.*

/**
 * @author Burning
 * @description:报修报送
 * @date :2020/10/23 3:17 PM
 */
class QuantizeActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: QuantizeAdapter
    var mData: ArrayList<QuantizeTypeBean> = ArrayList()


    override fun getLayoutId(): Int {
        return R.layout.activity_quantize
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
        mViewModel.getMoralTypeList()
    }

    private fun initAdapter() {
        mAdapter = QuantizeAdapter(R.layout.item_quantize, mData)
        rvQuantize.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mStartActivity<QuantizeTypeActivity>(this){
                putExtra(Constant.DATA,mData[position])
            }
        }
    }


    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                netResponseFormat<BaseResp<QuantizeTypeBean>>(it)?.let { bean ->
                    bean.data?.let {
                        mData.addAll(it)
                        rvQuantize.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}