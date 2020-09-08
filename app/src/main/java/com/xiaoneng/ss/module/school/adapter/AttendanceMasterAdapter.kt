package com.xiaoneng.ss.module.school.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.school.model.AttendanceBean
import com.xiaoneng.ss.module.school.view.AddClassAttendanceType


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class AttendanceMasterAdapter(layoutId: Int, listData: MutableList<AttendanceBean>) :
    BaseQuickAdapter<AttendanceBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: AttendanceBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvStudentCode, item?.realname)
//                .setText(R.id.tvTime3,item?.noticetime)
//
            holder.getView<TextView>(R.id.tvLeaveType).apply {
                setOnClickListener {
                    mStartActivity<AddClassAttendanceType>(mContext)
                }
            }
        }
    }
}