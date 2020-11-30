package com.xiaoneng.ss.module.school.view

import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
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
    private lateinit var tvRoomAddress: TextView
    private lateinit var tvRoomCapacity: TextView
    private lateinit var tvRoomEquip: TextView
    private lateinit var mSiteData: ArrayList<SiteItemBean>
    private var chosenDay: Long? = System.currentTimeMillis()
    var mMax = 47
    lateinit var mAdapter: SiteItemAdapter
    var mData: SiteBean? = null
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
            tvAddSiteDate.text = DateUtil.formatDateCustomWeekDay(it.chosenDay)
            ivAddSiteJian.isEnabled = false
            mBegin = it.position
            mEnd = it.position
            ivAddSiteJia.setOnClickListener { view ->
                if (mEnd == mMax) {
                    view.isEnabled = false
                    toast("不能再加了~")
                    return@setOnClickListener
                }
                ivAddSiteJian.isEnabled = true
                mEnd++
                mSiteData[mEnd].isChecked = true
                mAdapter.notifyDataSetChanged()
                tvAddSiteTime.text = getSiteTimeByPosition(
                    it.position,
                    mEnd
                ) + "   " + (mEnd - it.position + 1) * 30 + "分钟"
            }
            ivAddSiteJian.setOnClickListener { view ->
                if (mEnd - it.position == 0) {
                    view.isEnabled = false
                    toast("不能再减了~")
                    return@setOnClickListener
                }
                ivAddSiteJia.isEnabled = true
                mSiteData[mEnd].isChecked = false
                mAdapter.notifyDataSetChanged()
                mEnd--
                tvAddSiteTime.text = getSiteTimeByPosition(
                    it.position,
                    mEnd
                ) + "   " + (mEnd - it.position + 1) * 30 + "分钟"
            }
            tvAddSiteConfirm.setOnClickListener { view ->
                doConfirm(it)
            }
        }
    }

    private fun initUI(it: SiteBean) {
        recyclerView = findViewById(R.id.rvSiteItem)
        if (it.startType == 0) {
            var item = SiteItemBean()
            it.books?.forEach { it2 ->
                for (i in it2.os_position!!.toIntSafe()..it2.oe_position!!.toIntSafe()) {
                    if (i == it.position) {
                        item = it2
                        return@forEach
                    }
                }
            }
            var timeStr = ""
            item.oetime?.let {
                if (it.length > 5) {
                    timeStr = item.ostime + "-" + it.substring(it.length - 5, it.length)
                }
            }
            recyclerView.visibility = View.GONE
            ivAddSiteJia.visibility = View.GONE
            ivAddSiteJian.visibility = View.GONE
            tvAddSiteTime.text = timeStr
            llAddSiteOperatorName.visibility = View.VISIBLE
            tvAddSiteOperatorName.text = item.operator?.realname
            tvAddSiteConfirm.visibility = View.GONE
            tvAddSiteDate.isEnabled = false
            etAddSiteRemark.isEnabled = false
            etAddSiteRemark.setText(item.remark)
        } else if (it.startType == 1) {
            var tmpP = it.position
            it.books?.forEach { it2 ->
                if (it2.os_position.toIntSafe() > it.position) {
                    mMax = it2.os_position.toIntSafe() - 1
                    if (it2.os_position.toIntSafe() < tmpP) {
                        tmpP = it2.os_position.toIntSafe()
                        mMax = it2.os_position.toIntSafe() - 1
                    }
                }
            }
            tvAddSiteTime.text = getSiteTimeByPosition(it.position) + "   30分钟"
            recyclerView.visibility = View.VISIBLE
            ivAddSiteJia.visibility = View.VISIBLE
            ivAddSiteJian.visibility = View.VISIBLE
            llAddSiteOperatorName.visibility = View.GONE
            tvAddSiteConfirm.visibility = View.VISIBLE
            tvAddSiteDate.isEnabled = true
            etAddSiteRemark.isEnabled = true
            initAdapter()
        }
        tvRoomName = findViewById(R.id.tvSiteItemRoomName)
        tvRoomAddress = findViewById(R.id.tvSiteItemRoomAddress)
        tvRoomCapacity = findViewById(R.id.tvSiteItemRoomCapacity)
        tvRoomEquip = findViewById(R.id.tvSiteItemRoomEquip)
        tvRoomName.text = it.classroomname
        tvRoomAddress.text = it.addr
        tvRoomCapacity.text = it.total
        tvRoomEquip.text = it.remark
    }

    private fun doConfirm(it: SiteBean) {
        var bean = AddBookSiteBody(
            token = UserInfo.getUserBean().token,
            roomid = it.id,
            ostime = DateUtil.formatDateCustomDay(chosenDay!!) + " " + mSiteData[mBegin].timeStr,
            oetime = DateUtil.formatDateCustomDay(chosenDay!!) + " " + mSiteData[mEnd + 1].timeStr,
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
        mSiteData = initSiteTimes()
        mData?.books?.forEach {
            for (i in it.os_position!!.toIntSafe()..it.oe_position!!.toIntSafe()) {
                mSiteData[i].isBooked = true
            }
        }
        mSiteData[mData?.position!!].isChecked = true
        mAdapter = SiteItemAdapter(R.layout.item_site_item, mSiteData)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setAdapter(mAdapter)
        }
        recyclerView.scrollToPosition(mData?.position!!)
        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    override fun initDataObserver() {
        mViewModel.mAddBookSiteData.observe(this, Observer { response ->
            response?.let {
                toast(R.string.deal_done)
                finish()
//                netResponseFormat<List<SiteBean>>(it)?.let {
//                    mData.addAll(it)
//                    rvSite.notifyDataSetChanged()
//                }
            }
        })
    }
}