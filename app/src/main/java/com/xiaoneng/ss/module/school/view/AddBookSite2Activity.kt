package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.adapter.SiteItemAdapter
import com.xiaoneng.ss.module.school.model.AddBookSiteBody
import com.xiaoneng.ss.module.school.model.RoomBean
import com.xiaoneng.ss.module.school.model.RoomResp
import com.xiaoneng.ss.module.school.model.SiteItemBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_add_book_site_2.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Burning
 * @description:场地预约详情
 * @date :2020/10/23 3:17 PM
 */
class AddBookSite2Activity : BaseLifeCycleActivity<SchoolViewModel>() {
    private lateinit var mSiteData: ArrayList<SiteItemBean>
    private var chosenDay: Long? = System.currentTimeMillis()
    var mMax = 47
    lateinit var mAdapter: SiteItemAdapter
    var mData: Long? = null
    var mRoomData = ArrayList<RoomBean>()
    var mBegin = 0
    var mEnd = 1
    var dateText = ""
    var roomId = ""
    var roomPosition = 0
    private val timeDialog: Dialog by lazy {
        initTimeDialog()
    }
    private val roomDialog: Dialog by lazy {
        initRoomDialog()
    }
    var basePosition = 16
    var startPosition = 0
    var endPosition = 0
    var timeStart = ""
    var timeEnd = ""
    var isStart = false
    lateinit var timeDialogAdapter: DialogListAdapter
    lateinit var roomDialogAdapter: DialogListAdapter
    lateinit var timeRecycler: RecyclerView
    var timeTitles = ArrayList<String>()
    var roomTitles = ArrayList<String>()

    override fun getLayoutId(): Int {
        return R.layout.activity_add_book_site_2
    }

    override fun initView() {
        super.initView()
        tvAddSiteConfirm.isEnabled = false
        mData = intent.getLongExtra(Constant.TIME, 0)
        mData?.let {
            chosenDay = it
            initUI()
            dateText = DateUtil.formatDateCustomBookDay(it)
            tvAddSiteStart.text = dateText
            tvAddSiteEnd.text = dateText
            tvAddSiteStart.setOnClickListener {
                tvAddSiteConfirm.isEnabled = false
                tvAddSiteChoseRoom.text = "请选择可用会议室"
                timeDialog.show()
                timeRecycler.scrollToPosition(startPosition)
                isStart = true
                timeTitles.clear()
                timeTitles.apply {
                    var d = initSiteTimes()
                    for (i in basePosition until d.size) {
                        add(d[i].timeStr ?: "")
                    }
                }
                timeDialogAdapter.notifyDataSetChanged()

            }
            tvAddSiteEnd.setOnClickListener {
                tvAddSiteConfirm.isEnabled = false
                tvAddSiteChoseRoom.text = "请选择可用会议室"
                timeDialog.show()
                timeRecycler.scrollToPosition(0)
                isStart = false
                timeTitles.clear()
                timeTitles.apply {
                    var d = initSiteTimes()
                    for (i in basePosition+startPosition+1 until d.size) {
                        add(d[i].timeStr ?: "")
                    }
                }
                timeDialogAdapter.notifyDataSetChanged()

            }
            tvAddSiteChoseRoom.setOnClickListener {
                if (timeStart.isNullOrEmpty() || timeEnd.isNullOrEmpty()) {
                    mToast("请先选择时间")
                } else {
                    mViewModel.getBookRoomList(DateUtil.formatDateCustomDay(mData!!) + " " + timeStart, DateUtil.formatDateCustomDay(mData!!) + " " + timeEnd)
                    showLoading()
                }
            }
            tvAddSiteConfirm.setOnClickListener { view ->
                doConfirm()
            }
        }
    }

    private fun initUI() {

    }

    private fun doConfirm() {
        if (etAddSiteRemark.text.toString().isEmpty()) {
            mToast(R.string.lack_info)
            return
        }

        var bean = AddBookSiteBody(
            token = UserInfo.getUserBean().token,
            roomid = mRoomData[roomPosition].id,
            ostime = DateUtil.formatDateCustomDay(mData!!) + " " + timeStart,
            oetime = DateUtil.formatDateCustomDay(mData!!) + " " + timeEnd,
            remark = etAddSiteRemark.text.toString(),
            status = "0",
            os_position = startPosition.toString(),
            oe_position = (startPosition+endPosition+1).toString()
        )
        mViewModel.addBookSite(bean)
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
    }

    private fun initTimeDialog(): Dialog {

        // 底部弹出对话框
        var bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        params.height = resources.displayMetrics.heightPixels-dp2px(this, 200f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        timeDialogAdapter = DialogListAdapter(R.layout.item_dialog_list, timeTitles)
        timeRecycler = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(this@AddBookSite2Activity)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = timeDialogAdapter
        }
        timeDialogAdapter.setOnItemClickListener { adapter, view, position ->
            if (isStart) {
                startPosition = position
                timeStart = timeTitles[position]
                tvAddSiteStart.text = dateText + " " + timeStart
            } else {
                endPosition = position
                timeEnd = timeTitles[position]
                tvAddSiteEnd.text = dateText + " " + timeEnd
            }

            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    private fun initRoomDialog(): Dialog {

        // 底部弹出对话框
        var bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_list, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        roomDialogAdapter = DialogListAdapter(R.layout.item_dialog_list, roomTitles)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(this@AddBookSite2Activity)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = roomDialogAdapter
        }
        roomDialogAdapter.setOnItemClickListener { adapter, view, position ->
            tvAddSiteConfirm.isEnabled = true
            roomPosition = position
            tvAddSiteChoseRoom.text = mRoomData[roomPosition].classroomname
            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    override fun initDataObserver() {
        mViewModel.mAddBookSiteData.observe(this, Observer { response ->
            response?.let {
                mToast(R.string.deal_done)
                finish()
//                netResponseFormat<List<SiteBean>>(it)?.let {
//                    mData.addAll(it)
//                    rvSite.notifyDataSetChanged()
//                }
            }
        })

        mViewModel.mBookRoomData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<RoomResp>(it)?.let {
                    mRoomData.clear()
                    it.classrooms?.let { it1 -> mRoomData.addAll(it1) }
                    if (mRoomData.size > 0) {
                        roomDialog.show()
                        roomTitles.clear()
                        roomTitles.apply {
                            mRoomData.forEach{
                                add(it.classroomname?:"")
                            }
                        }
                        roomDialogAdapter.notifyDataSetChanged()
                    } else {
                        mAlert("所选时段暂无可用会议室"){}
                    }
                }
            }
        })
    }
}