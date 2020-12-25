package com.xiaoneng.ss.module.school.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.module.school.adapter.RoomBookRecordAdapter
import com.xiaoneng.ss.module.school.model.AddBookSiteBody
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.model.SiteItemBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_book_room_records.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:场地的预约列表
 * @date :2020/10/23 3:17 PM
 */
class RoomBookRecordsActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    lateinit var mAdapter: RoomBookRecordAdapter
    var mData: ArrayList<SiteItemBean>? = null
    var curPosition = 0
    var oStr = "3"

    override fun getLayoutId(): Int {
        return R.layout.activity_book_room_records
    }

    override fun initView() {
        super.initView()

        mData = intent.getParcelableArrayListExtra(Constant.DATA)
        mData?.let {
            initAdapter()
            rvRoomBookRecords.notifyDataSetChanged()
        }
    }

    private fun doConfirm(it: SiteBean) {

    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
    }

    private fun initAdapter() {

        mAdapter = RoomBookRecordAdapter(R.layout.item_room_record, mData!!)
        rvRoomBookRecords.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tvAction) {
                var sourceBean = mData!![position]
                var bean = AddBookSiteBody(
                    token = UserInfo.getUserBean().token,
                    id = sourceBean.id,
                    status = oStr
                )
                mViewModel.modifyBookSite(bean)
                curPosition = position
            }
        }
    }

    override fun initDataObserver() {
        mViewModel.mModifyBookSiteData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                mData!![curPosition].status = oStr
                mAdapter.notifyItemChanged(curPosition)
            }
        })
    }
}