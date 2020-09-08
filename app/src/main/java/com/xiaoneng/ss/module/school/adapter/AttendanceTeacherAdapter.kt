package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.module.school.model.AttendanceBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class AttendanceTeacherAdapter(layoutId: Int, listData: MutableList<AttendanceBean>) :
    BaseQuickAdapter<AttendanceBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: AttendanceBean) {
        viewHolder?.let { holder ->
//            holder.setText(R.id.tvTitle3, item?.title)
//                .setText(R.id.tvTime3,item?.noticetime)
//
//            holder.setText(R.id.tvAction, item?.title)
        }
    }
}