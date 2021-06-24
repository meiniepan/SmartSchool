package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.custom.widgets.CustomTitleBar
import com.xiaoneng.ss.custom.widgets.ViewChooseStudent
import com.xiaoneng.ss.custom.widgets.ViewText
import com.xiaoneng.ss.custom.widgets.ViewTimeSection
import com.xiaoneng.ss.module.school.adapter.PropertyTypeAdapter
import com.xiaoneng.ss.module.school.interfaces.IChooseStudent
import com.xiaoneng.ss.module.school.model.DepartmentBean
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import com.xiaoneng.ss.module.school.model.QuantizeTypeBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import com.xiaoneng.ss.network.response.BaseResp
import kotlinx.android.synthetic.main.activity_quantize_type.*

/**
 * @author Burning
 * @description:报修报送
 * @date :2020/10/23 3:17 PM
 */
class QuantizeTypeActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: PropertyTypeAdapter
    var mBean: QuantizeTypeBean? = null
    var mData: ArrayList<PropertyTypeBean> = ArrayList()
    lateinit var mListener:IChooseStudent

    override fun getLayoutId(): Int {
        return R.layout.activity_quantize_type
    }

    override fun initView() {
        super.initView()
        mBean = intent.getParcelableExtra(Constant.DATA)
        tvConfirmQuantize.setOnClickListener {

        }

//        var split = layoutInflater.inflate(R.layout.layout_split,llRoot)
        var choose = ViewChooseStudent(this,data = mData,position = 0)
        llRoot.addView(choose)

        var timeSec = ViewTimeSection(this,data = mData,position = 1)
        llRoot.addView(timeSec)

        var text = ViewText(this,data = mData,position = 2)
        llRoot.addView(text)

        initAdapter()
    }


    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mViewModel.getMoralTypeInfo(mBean?.id)
    }

    private fun initAdapter() {


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
                    mListener.addInvolve(data)
                }
            }
        }
    }



    override fun initDataObserver() {
        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
//                netResponseFormat<BaseResp<PropertyTypeBean>>(it)?.let { bean ->
//                    bean.data?.let {
//                        mData.addAll(it)
//                    }
//                }
            }
        })
    }
}