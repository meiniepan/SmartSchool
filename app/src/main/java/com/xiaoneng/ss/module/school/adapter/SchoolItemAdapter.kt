package com.xiaoneng.ss.module.school.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.SchoolItemBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/17
 * Time: 17:32
 */
class SchoolItemAdapter(layoutId: Int, listData: MutableList<SchoolItemBean>?) :
    BaseQuickAdapter<SchoolItemBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: SchoolItemBean?) {
        viewHolder?.let { holder ->
            var view = holder.getView<View>(R.id.llItem)
            view.setOnClickListener(item?.click)
            holder.setText(R.id.tvSchoolItemLabel, item?.name)
                .setText(R.id.tvSchoolItemRemark, item?.remark)
                .setBackgroundRes(R.id.ivSchoolItemIcon, item?.icon ?: -1)
        }
    }
}