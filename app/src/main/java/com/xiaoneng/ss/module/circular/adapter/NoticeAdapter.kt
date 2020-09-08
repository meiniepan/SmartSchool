package com.xiaoneng.ss.module.circular.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.module.circular.model.NoticeBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class NoticeAdapter(layoutId: Int, listData: MutableList<NoticeBean>?) :
    BaseQuickAdapter<NoticeBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: NoticeBean?) {
        viewHolder?.let { holder ->

            holder.setText(R.id.tvTitle2, item?.title)
                .setText(R.id.tvTime2, DateUtil.formatShowTime(item?.noticetime!!))
                .setText(R.id.tvContent, item?.remark)
            if (item?.type == "feedback") {
                holder.setGone(R.id.tvTips, true)
                if (item?.received == "1") {
                    holder.setGone(R.id.vOval, false)

                } else {

                    holder.setGone(R.id.vOval, true)
                }
            } else {
                holder.setGone(R.id.tvTips, false)
                if (item?.status == "1") {
                    holder.setGone(R.id.vOval, false)
                } else {
                    holder.setGone(R.id.vOval, true)
                }
            }

        }
    }
}