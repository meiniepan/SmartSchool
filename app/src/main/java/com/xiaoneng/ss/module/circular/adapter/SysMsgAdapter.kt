package com.xiaoneng.ss.module.circular.adapter

import android.widget.TextView
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
class SysMsgAdapter(layoutId: Int, listData: MutableList<NoticeBean>?) :
    BaseQuickAdapter<NoticeBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: NoticeBean?) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvTitle3, item?.title)
                .setText(R.id.tvTime3, DateUtil.formatShowTime(item?.noticetime!!))

            holder.getView<TextView>(R.id.tvAction).apply {
                if (item?.status == "1") {
                    setTextColor(mContext.resources.getColor(R.color.commonHint))
                } else {
                    setTextColor(mContext.resources.getColor(R.color.commonBlue))
                }
            }
        }
    }
}