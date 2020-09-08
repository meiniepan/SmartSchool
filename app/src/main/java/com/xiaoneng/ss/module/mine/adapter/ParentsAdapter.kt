package com.xiaoneng.ss.module.mine.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.model.ParentBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class ParentsAdapter(layoutId: Int, listData: MutableList<ParentBean>?) :
    BaseQuickAdapter<ParentBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: ParentBean) {
        viewHolder?.let { holder ->
            holder.addOnClickListener(R.id.tvUnbindParent)
            holder.setText(R.id.tvPhoneParent, item.phone)

        }
    }
}