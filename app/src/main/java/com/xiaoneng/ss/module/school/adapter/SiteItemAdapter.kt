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
class SiteItemAdapter(layoutId: Int, listData: MutableList<SiteItemBean>) :
    BaseQuickAdapter<SiteItemBean, BaseViewHolder>(layoutId, listData) {


    override fun convert(viewHolder: BaseViewHolder, item: SiteItemBean) {
        viewHolder?.let { holder ->
            var view = holder.getView<View>(R.id.llSiteItem)
            if (holder.adapterPosition % 2 == 0) {
                view.setBackgroundColor(mContext.resources.getColor(R.color.commonRed))
            } else {
                view.setBackgroundColor(mContext.resources.getColor(R.color.transparent))
            }
            }

    }



}