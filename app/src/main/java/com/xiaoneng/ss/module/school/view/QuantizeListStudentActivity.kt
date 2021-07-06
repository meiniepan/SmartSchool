package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.module.school.adapter.QuantizeAdapter
import com.xiaoneng.ss.module.school.adapter.QuantizeListAdapter
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
    var mBean: QuantizeTypeBean? = null
    var mData: ArrayList<QuantizeTemplateBean> = ArrayList()
    var strSpecial = "特殊情况报备"


    override fun getLayoutId(): Int {
        return R.layout.activity_quantize_list
    }

    override fun initView() {
        super.initView()
        mBean = intent.getParcelableExtra(Constant.DATA)

        initAdapter()
    }




    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        showLoading()
        if (mBean?.typename==strSpecial){
            mViewModel.getQuantizeListSpecial()
        }else{
            mViewModel.getQuantizeListCommon(mBean?.id)
        }

    }

    private fun initAdapter() {
        mAdapter = QuantizeListAdapter(R.layout.item_quantize, mData)
        rvQuantizeList.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }

    }

    override fun initDataObserver() {
        mViewModel.mQuantizeListCommonData.observe(this, Observer {
            it?.let {

            }
        })

        mViewModel.mQuantizeListSpecialData.observe(this, Observer {
            it?.let {
//                netResponseFormat<ArrayList<ClassesResponse>>(it)?.let {
//
//                }
            }
        })
    }
}