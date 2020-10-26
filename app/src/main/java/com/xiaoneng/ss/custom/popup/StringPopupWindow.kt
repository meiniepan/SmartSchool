package com.xiaoneng.ss.custom.popup

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px

/**
 * @author Burning
 * @description:
 * @date :2020/8/24 6:17 PM
 */
class StringPopupWindow constructor(context: Context, data: ArrayList<String>) : PopupWindow() {

    private lateinit var mContentView: View
    lateinit var mAdapter: PopupAdapter


    private var callBack: CallBack? = null

    init {
        init(context, data)
    }

    private fun init(context: Context, data: ArrayList<String>) {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        mContentView = inflater.inflate(R.layout.dialog_popup, null)
        this.contentView = mContentView
        this.width = LinearLayout.LayoutParams.WRAP_CONTENT
        this.height = LinearLayout.LayoutParams.WRAP_CONTENT
        this.isTouchable = true
        this.isFocusable = false
        this.isOutsideTouchable = true
        this.animationStyle = android.R.style.Animation_Dialog
        var rvPop: RecyclerView = mContentView.findViewById<RecyclerView>(R.id.rvPopup)
        mAdapter = PopupAdapter(R.layout.item_popup, data)
        rvPop.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.themeColor)
                )
            )
            adapter = mAdapter
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            callBack?.onShowContent(data[position])
            dismiss()
        }
        //确认和点击text 参数回传

    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }


    /**
     * 显示popupWindow
     *
     * @param parent
     */
    fun showPopupWindow(parent: View) {
        if (!this.isShowing) {
            this.showAsDropDown(parent)
        }
    }


    interface CallBack {
        fun onShowContent(content: String)
    }

}