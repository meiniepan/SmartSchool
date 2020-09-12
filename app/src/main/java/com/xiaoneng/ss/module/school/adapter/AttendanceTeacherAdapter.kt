package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.AttCourseBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class AttendanceTeacherAdapter(layoutId: Int, listData: MutableList<AttCourseBean>) :
    BaseQuickAdapter<AttCourseBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: AttCourseBean) {
        viewHolder?.let { holder ->
            var llbac = holder.getView<View>(R.id.llChooseCourse)
            var tvCourseName = holder.getView<TextView>(R.id.tvCourseName)
            var tvTeacherName = holder.getView<TextView>(R.id.tvTeacherName)
            var tvRoomName = holder.getView<TextView>(R.id.tvRoomAtt)
            var tvAttTime = holder.getView<TextView>(R.id.tvTimeAtt)
            holder.setText(R.id.tvCourseName, item?.coursename)
                .setText(R.id.tvTeacherName, item?.teachername)
                .setText(R.id.tvRoomAtt, item?.classroomname)
                .setText(R.id.tvTimeAtt, item?.coursetime)

            if (item.checked) {
                llbac.setBackgroundResource(R.drawable.bac_blue_bac_5)


            } else {
                llbac.setBackgroundResource(R.drawable.bac_tint_blue_bac_5)

            }
        }
    }
}