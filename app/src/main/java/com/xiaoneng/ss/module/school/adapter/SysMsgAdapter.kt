package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.circular.model.SysMsgBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class SysMsgAdapter(layoutId: Int, listData: MutableList<SysMsgBean>?) :
    BaseQuickAdapter<SysMsgBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: SysMsgBean?) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvTitle3, item?.title)
                .setText(R.id.tvTime3,item?.noticetime)
        }
    }
}