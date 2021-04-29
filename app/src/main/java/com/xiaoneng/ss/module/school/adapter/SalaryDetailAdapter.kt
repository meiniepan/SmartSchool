package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.SalaryDetailBean
import com.xiaoneng.ss.module.school.model.SalaryExpandRemarkBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/11/16
 * Time: 17:32
 */
class SalaryDetailAdapter(layoutId: Int, listData: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutId, listData) {
    var eData: SalaryDetailBean? = null
    override fun convert(viewHolder: BaseViewHolder?, item: String) {
        viewHolder?.let { holder ->
            var llOut = holder.getView<View>(R.id.llOut)
            var ivRemark = holder.getView<ImageView>(R.id.ivRemark)
            var llRemark = holder.getView<View>(R.id.llSalaryRemark)
            var tvRemark = holder.getView<TextView>(R.id.tvSalaryRemark)
            var remarkBean: SalaryExpandRemarkBean? =
                eData?.expand?.remark?.get(holder.adapterPosition)

            //value为0时隐藏
            var layoutParams: ViewGroup.LayoutParams = llOut.getLayoutParams();
            llOut.visibility = View.VISIBLE
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            if (remarkBean?.isShow == "0") {
                if (eData?.expand?.vals?.get(holder.adapterPosition) == "0") {
                    llOut.visibility = View.GONE
                    layoutParams.height = 0;
                    layoutParams.width = 0;
                }
            }
            llOut.setLayoutParams(layoutParams);


            if (remarkBean?.remark.isNullOrEmpty()) {
                ivRemark.visibility = View.GONE
                llRemark.visibility = View.GONE
            } else {
                ivRemark.visibility = View.VISIBLE
                if (remarkBean?.show == true) {
                    ivRemark.setImageResource(R.drawable.ic_up)
                    llRemark.visibility = View.VISIBLE
                    tvRemark.text = remarkBean.remark
                } else {
                    ivRemark.setImageResource(R.drawable.ic_down)
                    llRemark.visibility = View.GONE
                }
            }

            ivRemark.setOnClickListener {
                if (remarkBean?.show != true) {
                    ivRemark.setImageResource(R.drawable.ic_up)
                    llRemark.visibility = View.VISIBLE
                    tvRemark.text = remarkBean?.remark
                } else {
                    ivRemark.setImageResource(R.drawable.ic_down)
                    llRemark.visibility = View.GONE
                }
                remarkBean?.show = !(remarkBean?.show ?: false)
            }
            holder.setText(R.id.tvSalaryName, eData?.expand?.keys?.get(holder.adapterPosition))
                .setText(R.id.tvSalaryValue, eData?.expand?.vals?.get(holder.adapterPosition))


        }
    }

    fun setEdata(it: SalaryDetailBean) {
        eData = it
    }
}