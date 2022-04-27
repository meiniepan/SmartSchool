package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.MultiCheckBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/06/28
 * Time: 17:32
 */
class DialogMultiCheckAdapter(layoutId: Int, listData: MutableList<MultiCheckBean>) :
    BaseQuickAdapter<MultiCheckBean, BaseViewHolder>(layoutId, listData) {


    override fun convert(viewHolder: BaseViewHolder, item: MultiCheckBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvDialogList, item.name)
            holder.setGone(R.id.ivChecked, item.isChecked)
            if (item.canCheck) {
                holder.setTextColor(R.id.tvDialogList, mContext.resources.getColor(R.color.black))
            } else {
                holder.setTextColor(R.id.tvDialogList, mContext.resources.getColor(R.color.commonHint))
            }

//                .setText(R.id.tvTime3,item?.noticetime)
//
//            holder.setText(R.id.tvAction, item?.title)

        }
    }

}