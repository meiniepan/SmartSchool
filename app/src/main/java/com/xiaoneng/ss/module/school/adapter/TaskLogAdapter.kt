package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.LogBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class TaskLogAdapter(layoutId: Int, listData: MutableList<LogBean>?) :
    BaseQuickAdapter<LogBean, BaseViewHolder>(layoutId, listData) {

    private var isOperator: Boolean = false

    override fun convert(viewHolder: BaseViewHolder?, item: LogBean?) {
        viewHolder?.let { holder ->
            var textView0 = holder.getView<TextView>(R.id.tvAction0Log)
            var textView1 = holder.getView<TextView>(R.id.tvAction1Log)
            var textView2 = holder.getView<TextView>(R.id.tvAction2Log)
            var ivMark = holder.getView<ImageView>(R.id.ivMark)
            holder.addOnClickListener(R.id.tvAction1Log)
            holder.addOnClickListener(R.id.tvAction2Log)
            holder.addOnClickListener(R.id.tvAction0Log)
            holder.setText(R.id.tvName4, item?.username)
                .setText(R.id.tvTime4, item?.plantime)
                .setText(R.id.tvIntro4, item?.feedback)
            textView1.visibility = View.GONE
            textView2.visibility = View.GONE

            if (isOperator) {
                textView0.visibility = View.GONE
                ivMark.visibility = View.GONE
                textView1.visibility = View.VISIBLE
                textView2.visibility = View.VISIBLE
            } else {
                textView0.visibility = View.VISIBLE


                textView1.visibility = View.GONE
                textView2.visibility = View.GONE
            }

            when (item?.examinestatus) {
                "2" -> {
                    ivMark.visibility = View.VISIBLE
                    ivMark.setImageResource(R.drawable.ic_refuse)
                    textView0.visibility = View.GONE
                    textView1.visibility = View.GONE
                    textView2.visibility = View.GONE
                }
                "1" -> {
                    ivMark.visibility = View.VISIBLE
                    ivMark.setImageResource(R.drawable.ic_pass)
                    textView0.visibility = View.GONE
                    textView1.visibility = View.GONE
                    textView2.visibility = View.GONE
                }
                else -> {
                    ivMark.visibility = View.GONE
                }
            }
//            holder.setText(R.id.tvAction, item?.title)
        }
    }

    fun setIsOperator(operator: Boolean) {
        isOperator = operator
    }
}