package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.AttendanceStuBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class AttendanceStuAdapter(layoutId: Int, listData: MutableList<AttendanceStuBean>) :
    BaseQuickAdapter<AttendanceStuBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: AttendanceStuBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvStudentAttName, item?.name)
                .setText(R.id.tvStudentAttInfo, item?.total + item?.unit)
        }
    }
}