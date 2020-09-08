package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.custom.widgets.CustomTabView2
import com.xiaoneng.ss.model.ClassBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class TimetableClassAdapter(layoutId: Int, listData: MutableList<ClassBean>) :
    BaseQuickAdapter<ClassBean, BaseViewHolder>(layoutId, listData) {


    override fun convert(viewHolder: BaseViewHolder, item: ClassBean) {
        viewHolder?.let { holder ->
            holder.getView<CustomTabView2>(R.id.tvClassTimetable).apply {
                setChecked(item.isChecked)
                setText(item.classname)
            }

        }
    }

}