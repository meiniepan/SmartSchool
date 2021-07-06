package com.xiaoneng.ss.module.school.view

import android.app.Activity
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.dealTemplate
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.model.ClassBean
import com.xiaoneng.ss.module.school.adapter.PropertyTypeAdapter
import com.xiaoneng.ss.module.school.interfaces.IChooseStudent
import com.xiaoneng.ss.module.school.model.*
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_quantize_type.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:报修报送
 * @date :2020/10/23 3:17 PM
 */
class QuantizeTypeActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: PropertyTypeAdapter
    var mBean: QuantizeTypeBean? = null
    var mData: ArrayList<QuantizeTemplateBean> = ArrayList()
    var mDataClasses: ArrayList<ClassBean>? = ArrayList()
    var mDataCommit= QuantizeBody()
    lateinit var mListener: IChooseStudent

    override fun getLayoutId(): Int {
        return R.layout.activity_quantize_type
    }

    override fun initView() {
        super.initView()
        mBean = intent.getParcelableExtra(Constant.DATA)
        tvConfirmQuantize.setOnClickListener {
            mData.forEach {
                if (it.rules?.required?.required==true){
                    if (it.rules?.required?.hasValue==false){
                        toast(it.rules?.required?.message?:getString(R.string.lack_info))
                        return@setOnClickListener
                    }
                }
            }
            mDataCommit.templatedata = Gson().toJson(mData)
            mDataCommit.typeid = mBean?.id
            mViewModel.addMoralScore(mDataCommit)
        }
        initAdapter()
    }


    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        showLoading()
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
        mViewModel.mMoralTypeInfoData.observe(this, Observer {
            it?.let {
                netResponseFormat<QuantizeTypeBean>(it)?.let { bean ->
                    ctbTitle.setTitle(bean.typename)
                    bean.template?.let {
                        mData.clear()
                        var views = ArrayList<QuantizeTemplateBean>()
                        val resultType = object : TypeToken<ArrayList<QuantizeTemplateBean>>() {}.type
                        val gson = Gson()
                        try {
                            views = gson.fromJson<ArrayList<QuantizeTemplateBean>>(it, resultType)
                        } catch (e: Exception) {

                        }
                            mData.addAll(views)

                    }
                }
                Handler().postDelayed(
                    {
                        showLoading()
                        mViewModel.getClassesByTea()
                    }, 100
                )
            }
        })

        mViewModel.mBaseData.observe(this, Observer {
            it?.let {
                netResponseFormat<ArrayList<ClassesResponse>>(it)?.let {
                    mDataClasses?.clear()
                    var classes = ArrayList<String>()
                    it.forEach {
                        it.list.forEach {

                            mDataClasses?.add(it)
                        }

                    }
                    mData.forEach {
                        if (it.name=="CascaderClass"){
                            it.classes = mDataClasses
                        }
                    }
                    dealTemplate(this,llRoot,mData,mDataCommit)
                }
            }
        })

        mViewModel.mAddMoralScoreData.observe(this, Observer {
            it?.let {
                toast(R.string.deal_done)
                finish()
            }
        })
    }
}