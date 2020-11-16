package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.SalaryDetailBean


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
            holder.setText(R.id.tvSalaryName, eData?.keys?.get(holder.adapterPosition))
                .setText(R.id.tvSalaryValue, eData?.vals?.get(holder.adapterPosition))


        }
    }

    fun setEdata(it: SalaryDetailBean) {
        eData = it
    }
}