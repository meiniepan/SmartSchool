package com.xiaoneng.ss.module.circular.adapter

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
class WeekTitleAdapter(layoutId: Int, listData: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: String?) {
        viewHolder?.let { holder ->

            holder
                .setText(R.id.tvWeekTitle, item)

        }
    }
}