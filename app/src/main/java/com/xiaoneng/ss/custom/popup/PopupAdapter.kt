package com.xiaoneng.ss.custom.popup

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class PopupAdapter(layoutId: Int, listData: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: String?) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvPopup, item)
//                .setText(R.id.tvTime3,item?.noticetime)
//
//            holder.setText(R.id.tvAction, item?.title)
        }
    }
}