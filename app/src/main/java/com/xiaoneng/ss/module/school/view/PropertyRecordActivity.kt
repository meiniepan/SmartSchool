package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.`interface`.IPropertyRecord
import com.xiaoneng.ss.module.school.adapter.PropertyRecordAdapter
import com.xiaoneng.ss.module.school.model.PropertyDetailBean
import com.xiaoneng.ss.module.school.model.PropertyDetailResp
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_property_record.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:填写报修报送
 * @date :2020/10/28 3:17 PM
 */
class PropertyRecordActivity : BaseLifeCycleActivity<SchoolViewModel>(), IPropertyRecord {
    private var lastId: String? = null
    lateinit var mAdapter: PropertyRecordAdapter
    var mData: ArrayList<PropertyDetailBean> = ArrayList()
    private var mType: String? = null//0报修 1维修
    var typeStr = ""
    lateinit var chosenBean: PropertyDetailBean
    private val delayDialog: Dialog by lazy {
        initDialog()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_property_record
    }

    override fun initView() {
        super.initView()
        mType = intent.getStringExtra(Constant.TYPE)
        if (mType == "0") {
            typeStr = "report"
        } else if (mType == "1") {
            typeStr = "repairer"
        }
        initAdapter()
    }


    private fun doRefresh() {
        lastId = null
        mData.clear()
        rvPropertyRecord.showLoadingView()
        rvPropertyRecord.setNoMoreData(false)
        getData()
    }


    override fun onResume() {
        super.onResume()
        doRefresh()
    }

    override fun getData() {
        super.getData()
        mViewModel.getPropertyRecord(type = typeStr, lastid = lastId)
    }

    private fun initAdapter() {
        rvPropertyRecord.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                getData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                doRefresh()
            }
        })
        mAdapter = PropertyRecordAdapter(R.layout.activity_property_detail, mData, this)
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
                rvPropertyRecord.finishRefreshLoadMore()
                netResponseFormat<PropertyDetailResp>(it)?.let { bean ->
                    bean.data?.let {
                        if (it.size > 0) {
                            lastId = it.last().id
                            mData.addAll(it)
                        } else {
                            if (lastId != null) {
                                rvPropertyRecord.showFinishLoadMore()
                            }
                        }
                        rvPropertyRecord.notifyDataSetChanged()
                    }
                }
            }
        })

        mViewModel.mModifyRepairData.observe(this, Observer {
            it?.let {
                toast(R.string.deal_done)
                getData()
            }
        })

        mViewModel.mRemindRepairData.observe(this, Observer {
            it?.let {
                toast(R.string.deal_done)
            }
        })
    }

    override fun doFinish(bean: PropertyDetailBean) {
        bean.token = UserInfo.getUserBean().token
        bean.status = "3"
        showLoading()
        mViewModel.modifyRepair(bean)
    }

    override fun doReceive(bean: PropertyDetailBean) {
        bean.token = UserInfo.getUserBean().token
        bean.status = "2"
        showLoading()
        mViewModel.modifyRepair(bean)
    }

    override fun doRemind(bean: PropertyDetailBean) {
        showLoading()
        mViewModel.remindRepair(bean.id ?: "")
    }

    override fun doCancel(bean: PropertyDetailBean) {
        bean.token = UserInfo.getUserBean().token
        bean.status = "0"
        getData()

        mViewModel.modifyRepair(bean)
    }

    override fun doShift(bean: PropertyDetailBean) {

    }

    override fun doDelay(bean: PropertyDetailBean) {
        delayDialog.show()
        chosenBean = bean
    }

    private fun initDialog(): Dialog {
        // 底部弹出对话框
        var bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_delay, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            this.resources.displayMetrics.widthPixels - dp2px(32f).toInt()
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.CENTER)
        var tvAction1 = contentView.findViewById<TextView>(R.id.tvPropertyDetailAction1)
        var tvAction2 = contentView.findViewById<TextView>(R.id.tvPropertyDetailAction2)
        var remarkStr = tvAction2.text
        var etRemark = contentView.findViewById<EditText>(R.id.etDelayRemark)
        var tvConfirm = contentView.findViewById<TextView>(R.id.tvDelayConfirm)
        tvAction1.setOnClickListener {
            remarkStr = tvAction1.text
        }
        tvAction2.setOnClickListener {
            remarkStr = tvAction2.text
        }
        tvConfirm.setOnClickListener {
            var remark = ""
            if (etRemark.text.toString().isEmpty()) {
                remark = remarkStr.toString()
            } else {
                remark = etRemark.text.toString()
            }
            chosenBean.token = UserInfo.getUserBean().token
            chosenBean.isdelay = "1"
            chosenBean.delayreasons = remark
            showLoading()
            mViewModel.modifyRepair(chosenBean)
        }

        return bottomDialog
    }
}