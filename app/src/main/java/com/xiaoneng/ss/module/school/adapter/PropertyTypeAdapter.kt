package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.displayImage
import com.xiaoneng.ss.module.school.model.PropertyTypeBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/11
 * Time: 17:32
 */
class PropertyTypeAdapter(layoutId: Int, listData: MutableList<PropertyTypeBean>) :
    BaseQuickAdapter<PropertyTypeBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder, item: PropertyTypeBean) {
        viewHolder?.let { holder ->
            var icon = holder.getView<ImageView>(R.id.ivPropertyTypeIcon)
            var space = holder.getView<View>(R.id.space)
            displayImage(
                mContext,
                UserInfo.getUserBean().domain + item.icon,
                icon
            )
            holder.setText(R.id.tvPropertyTypeName, item.name)
                .setText(R.id.tvPropertyTypeRemark, item.explain)
            if (holder.adapterPosition != 0) {
                space.visibility = View.VISIBLE
            } else {
                space.visibility = View.GONE
            }
        }
    }

}