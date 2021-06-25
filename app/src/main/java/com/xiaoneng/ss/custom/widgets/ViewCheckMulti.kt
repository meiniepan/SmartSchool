package com.xiaoneng.ss.custom.widgets

import android.app.Activity
import android.app.Dialog
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.RecycleViewDivider
import com.xiaoneng.ss.common.utils.dp2px
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import kotlinx.android.synthetic.main.custom_choose_item.view.*
import org.jetbrains.anko.toast

/**
 * @author Burning
 * @description:
 * @date :2021/06/25 7:38 PM
 */
class ViewCheckMulti @JvmOverloads constructor(
    val context: Activity,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    val data: QuantizeTemplateBean
) : FrameLayout(context, attrs, defStyleAttr) {
    private lateinit var dialogType: Dialog

    init {

        val typedArray =
            getContext().obtainStyledAttributes(attrs, R.styleable.CustomJumpItem)

        typedArray.recycle()
        init()
    }

    private fun init() {

        View.inflate(context, R.layout.custom_choose_item, this)
        tvJumpTitleKey.text = data.label
        tvJumpTitle.hint = data.placeholder

        initDialog()
        setOnClickListener {
            dialogType.show()
        }
    }
    private fun initDialog() {
        var titles = ArrayList<String>()
        data.selections?.let { titles.addAll(it) }
        // 底部弹出对话框
        dialogType =
            Dialog(context, R.style.BottomDialog)
        val contentView: View =
            LayoutInflater.from(context).inflate(R.layout.dialog_list, null)
        dialogType.setContentView(contentView)
        val params = contentView.layoutParams as ViewGroup.MarginLayoutParams
        params.width =
            resources.displayMetrics.widthPixels
        params.bottomMargin = dp2px(context, 0f).toInt()
        contentView.layoutParams = params
        dialogType.window!!.setGravity(Gravity.BOTTOM)
        dialogType.window!!.setWindowAnimations(R.style.BottomDialog_Animation)
        var dialogAdapter = DialogListAdapter(R.layout.item_dialog_list, titles)
        var recyclerView = contentView.findViewById<RecyclerView>(R.id.rvDialogList).apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                RecycleViewDivider(
                    dp2px(context, 1f).toInt(),
                    context.resources.getColor(R.color.splitColor)
                )
            )
            adapter = dialogAdapter
        }
        dialogAdapter.setOnItemClickListener { adapter, view, position ->
            data.value = titles[position]
            dialogType.dismiss()
        }

    }

}