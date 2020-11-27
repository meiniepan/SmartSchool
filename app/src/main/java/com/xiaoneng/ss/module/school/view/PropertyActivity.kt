package com.xiaoneng.ss.module.school.view

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_property.*

/**
 * @author Burning
 * @description:报修报送
 * @date :2020/10/23 3:17 PM
 */
class PropertyActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_property
    }

    override fun initView() {
        super.initView()
        tvPropertyApply1.setOnClickListener {
            mStartActivity<AddPropertyActivity>(this)
        }
        tvPropertyApply2.setOnClickListener {
            mStartActivity<AddPropertyActivity>(this)
        }
        tvPropertyRecord1.setOnClickListener {
            //维修记录
            mStartActivity<PropertyRecordActivity>(this){
                putExtra(Constant.TYPE,"1")
            }
        }
        tvPropertyRecord2.setOnClickListener {
            //报修记录
            mStartActivity<PropertyRecordActivity>(this)
            {
                putExtra(Constant.TYPE,"0")
            }
        }
        initAdapter()
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


    }

    override fun initDataObserver() {

    }
}