package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.common.utils.eventBus.ChangeMasterTimetableEvent
import com.xiaoneng.ss.model.ClassBean
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.adapter.TimetableAdapter
import com.xiaoneng.ss.module.school.adapter.TimetableLabelAdapter
import com.xiaoneng.ss.module.school.adapter.TitleTimetableAdapter
import com.xiaoneng.ss.module.school.model.TimetableBean
import com.xiaoneng.ss.module.school.model.TimetableLabelBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_timetable_master.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class TimetableActivity : BaseLifeCycleActivity<SchoolViewModel>() {

    private var curTit: Int = 1
    private var hasInitClass: Boolean = false
    private var mClassPosition = 0
    lateinit var mAdapter: TimetableAdapter
    lateinit var mAdapterTitle: TitleTimetableAdapter
    lateinit var mAdapterLabel: TimetableLabelAdapter
    var mData = ArrayList<TimetableBean>()
    var mDataClass = ArrayList<ClassBean>()
    var mLabelData = ArrayList<TimetableLabelBean>()
    var mClassId = ""

    private val classDialog: Dialog by lazy {
        initClassDialog()
    }

    private val typeDialog: Dialog by lazy {
        initTypeDialog()
    }

    override fun getLayoutId(): Int = R.layout.activity_timetable_master


    override fun initView() {
        super.initView()
        if (UserInfo.getUserBean().classmaster == "1") {
            ChangeMasterTimetableEvent(true).post()
            llClassTimetable.visibility = View.VISIBLE
            tvTab1.setOnClickListener {
                typeDialog.show()
            }
            tvTab2.setOnClickListener {
                classDialog.show()
            }
        }

        initAdapterLabel()
        initAdapter()
        initAdapterTitle()
    }

    override fun initData() {
        super.initData()

        mViewModel.getTimetable()

    }


    private fun initClassItem(position: Int = -1) {

        if (position != -1) {
            mClassId = mDataClass[position].classid
        }
        mViewModel.getTimetable(mClassId)

    }

    private fun initAdapter() {
        mAdapter = TimetableAdapter(R.layout.item_timetable, mData)
        rvTimetable.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setAdapter(mAdapter)
            setItemViewCacheSize(7)
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
        rvTimetable.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                rvTitleTimetable.scrollBy(dx, dy)
            }
        })

    }

    private fun initAdapterTitle() {
        mAdapterTitle = TitleTimetableAdapter(R.layout.item_timetable_title, mData)
        rvTitleTimetable.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })
        rvTitleTimetable.apply {
            layoutManager =
                object : LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {

                }
            adapter = mAdapterTitle
        }
        mAdapterTitle.setOnItemClickListener { _, view, position ->

        }
    }


    private fun initAdapterLabel() {
        mAdapterLabel = TimetableLabelAdapter(R.layout.item_timetable_label, mLabelData)
        rvLabelTimetable.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterLabel
        }
        mAdapterLabel.setOnItemClickListener { _, view, position ->

        }
    }


    private fun initTypeDialog(): Dialog {
        // 底部弹出对话框
        val bottomDialog =
            Dialog(this, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(this).inflate(R.layout.dialog_timetable, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(this, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.BOTTOM)
        bottomDialog.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        contentView.findViewById<View>(R.id.tvAction1TimeDialog)
            .setOnClickListener { v: View? ->
                if (curTit != 1) {
                    curTit = 1
                    tvTab1.text = "班级课表"
                    tvTab2.visibility = View.VISIBLE
                    if (mDataClass.size > 0) {
                        initClassItem()
                        tvTab2.visibility = View.VISIBLE
                    }
                    ChangeMasterTimetableEvent(true).post()
                }

                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvAction2TimeDialog)
            .setOnClickListener { v: View? ->
                if (curTit != 2) {
                    curTit = 2
                    tvTab1.text = "科任课表"
                    tvTab2.visibility = View.GONE
                    mViewModel.getTimetableT()
                    rvTimetable.showLoadingView()
                    tvTab2.visibility = View.GONE
                    ChangeMasterTimetableEvent(false).post()
                }
                bottomDialog.dismiss()
            }
        contentView.findViewById<View>(R.id.tvAction3TimeDialog)
            .setOnClickListener { v: View? ->
                bottomDialog.dismiss()
            }
        return bottomDialog
    }


    private fun initClassDialog(): Dialog {
        var titles = ArrayList<String>().apply {
            mDataClass.forEach {
                add(it.classname)
            }
        }
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
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titles)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(this@TimetableActivity)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            tvTab2.text = titles[position]
            initClassItem(position)
            bottomDialog.dismiss()
        }

        return bottomDialog
    }

    override fun initDataObserver() {
        mViewModel.mTimetableData.observe(this, Observer { response ->
            response?.let {
                //初始化时间段数据
                mLabelData.clear()
                mLabelData.addAll(it.positions)
                mAdapterLabel.notifyDataSetChanged()
                if (UserInfo.getUserBean().classmaster == "1") {
                    llClassTimetable.visibility = View.VISIBLE
                    if (!hasInitClass) {
                        hasInitClass = true
                        mDataClass.clear()
                        mDataClass.addAll(it.classs)
                        if (mDataClass.size > 0) {
                            mDataClass[0].isChecked = true
                            tvTab2.text = mDataClass[0].classname
                        }
                    }
                }
                //填充课表
                mData.clear()
                mData.addAll(it.list)
                mAdapter.setTotalSize(mLabelData.size)
                if (mData.size > 0) {
                    var i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                    rvTimetable.notifyDataSetChanged()
                    mAdapterTitle.notifyDataSetChanged()
                    rvTimetable.scrollToPosition(DateUtil.getWeekPosition(i))
                    rvTitleTimetable.scrollToPosition(DateUtil.getWeekPosition(i))
                } else {
                    showEmpty()
                }

            }
        })

        mViewModel.mTimetableDataT.observe(this, Observer { response ->
            response?.let {
                //初始化时间段数据
                mLabelData.clear()
                mLabelData.addAll(it.positions)
                mAdapterLabel.notifyDataSetChanged()

                //填充课表
                mData.clear()
                mData.addAll(it.list)
                mAdapter.setTotalSize(mLabelData.size)
                if (mData.size > 0) {
                    var i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                    rvTimetable.notifyDataSetChanged()
                    mAdapterTitle.notifyDataSetChanged()
                    rvTimetable.scrollToPosition(DateUtil.getWeekPosition(i))
                    rvTitleTimetable.scrollToPosition(DateUtil.getWeekPosition(i))
                } else {
                    showEmpty()
                }

            }
        })

    }

}
