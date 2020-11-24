package com.xiaoneng.ss.module.school.view

import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.getSiteTimeByPosition
import com.xiaoneng.ss.common.utils.initSiteTimes
import com.xiaoneng.ss.module.school.adapter.SiteItemAdapter
import com.xiaoneng.ss.module.school.model.AddBookSiteBody
import com.xiaoneng.ss.module.school.model.SiteBean
import com.xiaoneng.ss.module.school.model.SiteItemBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_book_site.*
import org.jetbrains.anko.toast
import java.util.*

/**
 * @author Burning
 * @description:场地预约详情
 * @date :2020/10/23 3:17 PM
 */
class AddBookSiteActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvRoomName: TextView
    private lateinit var mSiteTimes: ArrayList<SiteItemBean>
    private var chosenDay: Long? = System.currentTimeMillis()
    lateinit var mAdapter: SiteItemAdapter
    var mData:SiteBean? = null
    var mBegin = 0
    var mEnd = 1

    override fun getLayoutId(): Int {
        return R.layout.activity_add_book_site
    }

    override fun initView() {
        super.initView()

        mData = intent.getParcelableExtra(Constant.DATA)
        mData?.let {
            initUI(it)
            tvAddSiteDate.text = DateUtil.formatDateCustomDay(chosenDay!!)
            ivAddSiteJian.isEnabled = false
            mBegin = it.position
            mEnd = it.position
            var mMax = 47
            tvAddSiteTime.text = getSiteTimeByPosition(it.position)+"   30分钟"
            ivAddSiteJia.setOnClickListener {view->
                if (mEnd==mMax){
                    view.isEnabled = false
                    toast("不能再加了~")
                    return@setOnClickListener
                }
                ivAddSiteJian.isEnabled = true
                mEnd++
                mSiteTimes[mEnd].isChecked = true
                mAdapter.notifyDataSetChanged()
                tvAddSiteTime.text = getSiteTimeByPosition(it.position,mEnd)+"   "+(mEnd-it.position+1)*30+"分钟"
            }
            ivAddSiteJian.setOnClickListener {view->
                if (mEnd-it.position==0){
                    view.isEnabled = false
                    toast("不能再减了~")
                    return@setOnClickListener
                }
                ivAddSiteJia.isEnabled = true
                mSiteTimes[mEnd].isChecked = false
                mAdapter.notifyDataSetChanged()
                mEnd--
                tvAddSiteTime.text = getSiteTimeByPosition(it.position,mEnd)+"   "+(mEnd-it.position+1)*30+"分钟"
            }
            initAdapter()
            tvAddSiteConfirm.setOnClickListener {view->
                doConfirm(it)
            }
        }
    }

    private fun initUI(it: SiteBean) {
        tvRoomName = findViewById(R.id.tvSiteItemRoomName)
        tvRoomName.text = it.roomname
    }

    private fun doConfirm(it: SiteBean) {
        var bean = AddBookSiteBody(
            token = UserInfo.getUserBean().token,
            roomid = it.id,
            ostime = DateUtil.formatDateCustomDay(chosenDay!!)+" "+mSiteTimes[mBegin].timeStr,
            oetime = DateUtil.formatDateCustomDay(chosenDay!!)+" "+mSiteTimes[mEnd+1].timeStr,
            remark = etAddSiteRemark.text.toString(),
            status = null,
            os_position = mBegin.toString(),
            oe_position = mEnd.toString()
        )
        mViewModel.addBookSite(bean)
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        mViewModel.getCanBookRooms(DateUtil.formatDateCustomDay(chosenDay!!))
    }

    private fun initAdapter() {
         mSiteTimes = initSiteTimes()
        mSiteTimes[mData?.position!!].isChecked = true
        mAdapter = SiteItemAdapter(R.layout.item_site_item, mSiteTimes)
         recyclerView = findViewById(R.id.rvSiteItem)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setAdapter(mAdapter)
        }
        recyclerView.scrollToPosition(mData?.position!!)
        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    override fun initDataObserver() {
        mViewModel.mAddBookSiteData.observe(this, Observer{ response ->
            response?.let {
                toast(R.string.deal_done)
//                netResponseFormat<List<SiteBean>>(it)?.let {
//                    mData.addAll(it)
//                    rvSite.notifyDataSetChanged()
//                }
            }
        })
    }
}