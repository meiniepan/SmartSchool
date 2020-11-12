package com.xiaoneng.ss.module.school.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.SalaryListBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/11/12
 * Time: 17:32
 */
class SalaryAdapter(layoutId: Int, listData: MutableList<SalaryListBean>?) :
    BaseQuickAdapter<SalaryListBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: SalaryListBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvSalaryTitle, item.title)
                .setText(R.id.tvSalaryTime, item.createtime)

            val view = holder.getView<View>(R.id.llSalaryList)
            if (holder.adapterPosition % 2 == 0) {
                view.setBackgroundColor(mContext.resources.getColor(R.color.white))
            } else {
                view.setBackgroundColor(mContext.resources.getColor(R.color.transparent))
            }
        }
    }
}