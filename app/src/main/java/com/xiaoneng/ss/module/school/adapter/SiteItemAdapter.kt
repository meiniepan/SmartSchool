package com.xiaoneng.ss.module.school.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.SiteItemBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class SiteItemAdapter(layoutId: Int, listData: MutableList<SiteItemBean>?) :
    BaseQuickAdapter<SiteItemBean, BaseViewHolder>(layoutId, listData) {


    override fun convert(viewHolder: BaseViewHolder, item: SiteItemBean) {
        viewHolder?.let { holder ->
            var space = holder.getView<View>(R.id.vSpace)
            var view = holder.getView<View>(R.id.tvItemSitePosition)
            var line1 = holder.getView<View>(R.id.vItemSiteLine1)
            if (holder.adapterPosition == 0) {
                space.visibility = View.VISIBLE
            } else {
                space.visibility = View.GONE
            }
            if (holder.adapterPosition % 2 == 0) {
                line1.visibility  = View.VISIBLE
                view.setBackgroundColor(mContext.resources.getColor(R.color.grayDB))
            } else {
                line1.visibility  = View.INVISIBLE
                view.setBackgroundColor(mContext.resources.getColor(R.color.transparent))
            }
            holder.setText(R.id.tvItemSiteTime,item.timeStr)
        }

    }


}