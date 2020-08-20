package com.xiaoneng.ss.module.circular.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.circular.model.NoticeBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class NoticeAdapter(layoutId: Int, listData: MutableList<NoticeBean>?) :
    BaseQuickAdapter<NoticeBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: NoticeBean?) {
        viewHolder?.let { holder ->

            holder.setText(R.id.tvTitle2, item?.title)
                .setText(R.id.tvTime2,item?.noticetime)
                .setText(R.id.tvContent,item?.remark)
            if (item?.status == "0"){
                holder.setGone(R.id.vOval,true)
            }else{
                holder.setGone(R.id.vOval,false)
            }
        }
    }
}