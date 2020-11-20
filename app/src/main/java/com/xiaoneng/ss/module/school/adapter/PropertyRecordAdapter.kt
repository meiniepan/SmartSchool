package com.xiaoneng.ss.module.school.adapter

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.module.school.model.PropertyDetailBean
import org.jetbrains.anko.toast


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class PropertyRecordAdapter(layoutId: Int, listData: MutableList<PropertyDetailBean>) :
    BaseQuickAdapter<PropertyDetailBean, BaseViewHolder>(layoutId, listData) {
    var mType: String? = null
    private val delayDialog: Dialog by lazy {
        initDialog()
    }

    override fun convert(viewHolder: BaseViewHolder, item: PropertyDetailBean) {
        viewHolder?.let { holder ->
            var view1 = holder.getView<TextView>(R.id.tvPropertyDetailAction1)
            var view2 = holder.getView<TextView>(R.id.tvPropertyDetailAction2)
            var llResult = holder.getView<View>(R.id.llPropertyDetailResult)
            holder.setText(R.id.tvPropertyDetailTime, item.reporttime)
                .setText(R.id.tvPropertyDetailCode, item.id)
                .setText(R.id.tvPropertyDetailPerson, item.repairerinfo?.realname)
                .setText(R.id.tvPropertyDetailTime2, item.handletime)
                .setText(R.id.tvPropertyDetailRemark, item.remark)
            if (mType == "0") {
                holder.setText(R.id.tvPropertyDetailTypeKey, "报修类型")
                    .setText(R.id.tvPropertyDetailType, item.typename)
                llResult.visibility = View.GONE
                view1.apply {
                    text = "撤销"
                    setOnClickListener {
                        doCancel()
                    }
                }
                view2.apply {
                    text = "提醒"
                    setOnClickListener {
                        doRemind()
                    }
                }
            } else if (mType == "1") {
                view1.apply {
                    text = "转单"
                    setOnClickListener {
                        doDelay()
                    }
                }
                view2.apply {
                    text = "接单"
                    setOnClickListener {
                        doReceive()
                    }
                }
                llResult.visibility = View.VISIBLE
                holder.setText(R.id.tvPropertyDetailTypeKey, "发起人")
                    .setText(R.id.tvPropertyDetailType, item.reportinfo?.realname)
                    .setText(R.id.tvPropertyDetailResult, item.status)

            }
            item.fileinfo?.let {
                if (it.size > 0) {
                    initAdapter(holder, item)
                }

            }
        }

    }

    private fun doReceive() {

    }

    private fun doDispatch() {

    }

    private fun doRemind() {

    }

    private fun doCancel() {

    }

    private fun doDelay() {
        delayDialog.show()
    }

    private fun initDialog(): Dialog {

        // 底部弹出对话框
        var bottomDialog =
            Dialog(mContext, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(mContext).inflate(R.layout.dialog_delay, null)
        bottomDialog.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            mContext.resources.displayMetrics.widthPixels- dp2px(32f).toInt()
        params.bottomMargin = dp2px(mContext, 0f).toInt()
        contentView.layoutParams = params
        bottomDialog.window!!.setGravity(Gravity.CENTER)
        var tvConfirm = contentView.findViewById<TextView>(R.id.tvDelayConfirm)
        tvConfirm.setOnClickListener {
            mContext.toast("ss")
        }

        return bottomDialog
    }

    private fun initAdapter(holder: BaseViewHolder, item: PropertyDetailBean) {
        var mRecycler: RecyclerView = holder.getView(R.id.rvPropertyDetail)

        var eData: ArrayList<String> = ArrayList()
        item.fileinfo?.forEach {
            eData.add(UserInfo.getUserBean().domain + it.url)
        }

        var mAdapter = DisplayImgAdapter(R.layout.item_add_img, eData)

        mRecycler.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }

    fun setType(mType: String?) {
        this.mType = mType
    }

}