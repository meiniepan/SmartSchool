package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.QuantizeAdapter
import com.xiaoneng.ss.module.school.adapter.QuantizeListAdapter
import com.xiaoneng.ss.module.school.adapter.QuantizeListSpecialAdapter
import com.xiaoneng.ss.module.school.model.QuantizeBody
import com.xiaoneng.ss.module.school.model.QuantizeListResp
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import com.xiaoneng.ss.module.school.model.QuantizeTypeBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_quantize_list.*

/**
 * @author Burning
 * @description:
 * @date :2021/07/06 3:17 PM
 */
class QuantizeListStudentActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: QuantizeListAdapter
    lateinit var mAdapterSpecial: QuantizeListSpecialAdapter
    var mBean: QuantizeTypeBean? = null
    var mData: ArrayList<QuantizeBody> = ArrayList()
    var mDataSpecial: ArrayList<QuantizeBody> = ArrayList()
    var strSpecial = "特殊情况报备"


    override fun getLayoutId(): Int {
        return R.layout.activity_quantize_list
    }

    override fun initView() {
        super.initView()
        mBean = intent.getParcelableExtra(Constant.DATA)

    }


    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        showLoading()
        if (mBean?.typename == strSpecial) {
            mViewModel.getQuantizeListSpecial()
        } else {
            mViewModel.getQuantizeListCommon(mBean?.id)
        }

    }

    private fun initAdapterCommon() {
        mAdapter = QuantizeListAdapter(R.layout.item_quantize_list_common, mData)
        rvQuantizeList.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }

    }

    private fun initAdapterSpecial() {
        mAdapterSpecial =
            QuantizeListSpecialAdapter(R.layout.item_quantize_list_special, mDataSpecial)
        rvQuantizeList.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapterSpecial)
        }

    }

    override fun initDataObserver() {
        mViewModel.mQuantizeListCommonData.observe(this, Observer {
            it?.let {
                netResponseFormat<QuantizeListResp>(it)?.let {
                    it.data?.let {
                        mData.addAll(it)
                        initAdapterCommon()
                        rvQuantizeList.notifyDataSetChanged()
                    }

                }
            }
        })

        mViewModel.mQuantizeListSpecialData.observe(this, Observer {
            it?.let {
                netResponseFormat<QuantizeListResp>(it)?.let {
                    it.data?.let {
                        mDataSpecial.addAll(it)
                        initAdapterSpecial()
                        rvQuantizeList.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}