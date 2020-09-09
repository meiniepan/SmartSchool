package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.circular.model.NoticeBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class TaskLogAdapter(layoutId: Int, listData: MutableList<NoticeBean>?) :
    BaseQuickAdapter<NoticeBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: NoticeBean?) {
        viewHolder?.let { holder ->
            holder.addOnClickListener(R.id.tvAction1Log)
            holder.addOnClickListener(R.id.tvAction2Log)
            holder.setText(R.id.tvName4, item?.title)
                .setText(R.id.tvIntro4,item?.noticetime)
//
//            holder.setText(R.id.tvAction, item?.title)
        }
    }
}