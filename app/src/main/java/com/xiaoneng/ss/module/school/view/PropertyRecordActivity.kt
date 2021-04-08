package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.common.utils.recyclerview.StatusRecyclerView
import com.xiaoneng.ss.module.activity.ImageScaleActivity
import com.xiaoneng.ss.module.school.adapter.PropertyRecordAdapter
import com.xiaoneng.ss.module.school.adapter.PropertyShiftAdapter
import com.xiaoneng.ss.module.school.interfaces.IPropertyRecord
import com.xiaoneng.ss.module.school.model.PropertyDetailBean
import com.xiaoneng.ss.module.school.model.PropertyDetailResp
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import com.xiaoneng.ss.module.school.model.RepairBody
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_property_record.*
import java.util.*

/**
 * @author Burning
 * @description:填写报修报送
 * @date :2020/10/28 3:17 PM
 */
class PropertyRecordActivity : BaseLifeCycleActivity<SchoolViewModel>(), IPropertyRecord {
    private var lastId: String? = null
    lateinit var mAdapter: PropertyRecordAdapter
    var mData: ArrayList<PropertyDetailBean> = ArrayList()
    var mTypeData: ArrayList<PropertyTypeBean>? = null
    private var mType: String? = null//0报修 1维修
    var typeStr = ""
    lateinit var chosenBean: RepairBody
    private val delayDialog: Dialog by lazy {
        initDialog()
    }
    private val shiftDialog: Dialog by lazy {
        initShiftDialog()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_property_record
    }

    override fun initView() {
        super.initView()
        mType = intent.getStringExtra(Constant.TYPE)
        mTypeData = intent.getParcelableArrayListExtra(Constant.DATA)
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
        rvPropertyRecord.setNoMoreData(false)
        getData()
    }


    override fun onResume() {
        super.onResume()
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        rvPropertyRecord.showLoadingView()
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

        }
    }


    override fun doFinish(bean: RepairBody) {
        bean.token = UserInfo.getUserBean().token
        bean.status = "3"
        showLoading()
        mViewModel.modifyRepair(bean)
    }

    override fun doReceive(bean: RepairBody) {
        bean.token = UserInfo.getUserBean().token
        bean.status = "2"
        bean.repairerid = UserInfo.getUserBean().uid
        showLoading()
        mViewModel.modifyRepair(bean)
    }

    override fun doRemind(bean: PropertyDetailBean) {
        showLoading()
        mViewModel.remindRepair(bean.id ?: "")
    }

    override fun doCancel(bean: RepairBody) {
        bean.token = UserInfo.getUserBean().token
        bean.status = "0"
        showLoading()
        mViewModel.modifyRepair(bean)
    }

    override fun doShift(bean: RepairBody) {
        chosenBean = bean
        shiftDialog.show()
    }

    override fun doDelay(bean: RepairBody) {
        chosenBean = bean
        delayDialog.show()
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
        var remarkStr = ""
        var etRemark = contentView.findViewById<EditText>(R.id.etDelayRemark)
        var tvConfirm = contentView.findViewById<TextView>(R.id.tvDelayConfirm)
        contentView.findViewById<View>(R.id.ivClose).setOnClickListener {
            bottomDialog.dismiss()
        }
        tvAction1.setOnClickListener {
            remarkStr = tvAction1.text.toString()
            tvAction1.setBackgroundResource(R.drawable.bac_blue_bac_19)
            tvAction2.setBackgroundResource(R.drawable.bac_blue_line_19)
            tvAction1.setTextColor(resources.getColor(R.color.white))
            tvAction2.setTextColor(resources.getColor(R.color.themeColor))
        }
        tvAction2.setOnClickListener {
            remarkStr = tvAction2.text.toString()
            tvAction2.setBackgroundResource(R.drawable.bac_blue_bac_19)
            tvAction1.setBackgroundResource(R.drawable.bac_blue_line_19)
            tvAction2.setTextColor(resources.getColor(R.color.white))
            tvAction1.setTextColor(resources.getColor(R.color.themeColor))
        }
        tvConfirm.setOnClickListener {
            var remark = ""
            if (etRemark.text.toString().isEmpty()) {
                remark = remarkStr.toString()
            } else {
                remark = etRemark.text.toString()
            }
            if (remark.isEmpty()) {
                mToast(R.string.lack_info)
                return@setOnClickListener
            }
            chosenBean.token = UserInfo.getUserBean().token
            chosenBean.isdelay = "1"
            chosenBean.delayreasons = remark
            showLoading()
            mViewModel.modifyRepair(chosenBean)
            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    private fun initShiftDialog(): Dialog {
        // 转单对话框
        var bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_shift, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            this.resources.displayMetrics.widthPixels - dp2px(32f).toInt()
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.CENTER)
        contentView.findViewById<View>(R.id.ivClose).setOnClickListener {
            bottomDialog.dismiss()
        }
        var recyclerView = contentView.findViewById<StatusRecyclerView>(R.id.rvShift)
        mTypeData?.let {
            var removeBean: PropertyTypeBean? = null
            it.forEach {
                if (it.id == chosenBean.typeid) {
                    removeBean = it
                }
            }
            it.remove(removeBean)
            it[0].checked = true
            var mAdapter = PropertyShiftAdapter(R.layout.item_shift, it)
            recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                setAdapter(mAdapter)
            }
            mAdapter.setOnItemClickListener { adapter, view, position ->
                it.forEach {
                    it.checked = false
                }
                it[position].checked = true
                mAdapter.notifyDataSetChanged()
            }

            var tvConfirm = contentView.findViewById<TextView>(R.id.tvDelayConfirm)

            tvConfirm.setOnClickListener { view ->
                var typeId = ""
                it.forEach {
                    if (it.checked) {
                        typeId = it.id ?: ""
                    }
                }
                chosenBean.token = UserInfo.getUserBean().token
                chosenBean.typeid = typeId
                showLoading()
                mViewModel.modifyRepair(chosenBean)
                bottomDialog.dismiss()
            }
        }
        return bottomDialog
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
                mToast(R.string.deal_done)
                doRefresh()
            }
        })

        mViewModel.mRemindRepairData.observe(this, Observer {
            it?.let {
                mToast(R.string.deal_done)
            }
        })
    }
}