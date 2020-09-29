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

    private var isTeacher: Boolean = false

    override fun convert(viewHolder: BaseViewHolder?, item: AttCourseBean) {
        viewHolder?.let { holder ->
            var vPadding = holder.getView<View>(R.id.vPadding)
            if (holder.adapterPosition == 0) {
                vPadding.visibility = View.VISIBLE
            } else {
                vPadding.visibility = View.GONE
            }
            var tvCourseName = holder.getView<TextView>(R.id.tvCourseName)
            var tvTeacherName = holder.getView<TextView>(R.id.tvTeacherName)
            var tvRoomName = holder.getView<TextView>(R.id.tvRoomAtt)
            var tvAttTime = holder.getView<TextView>(R.id.tvTimeAtt)
            holder.setText(R.id.tvRoomAtt, item?.classroomname)
                .setText(R.id.tvTimeAtt, item?.crstime)

            if (isTeacher) {
                tvCourseName.text = item.classname
                tvTeacherName.visibility = View.GONE
            } else {
                tvCourseName.text = item.coursename
                tvTeacherName.text = item.teachername
                tvTeacherName.visibility = View.VISIBLE
            }

            if (item.checked) {


            } else {

            }
        }
    }

    fun setIsTeacher(b: Boolean) {
        this.isTeacher = b
    }
}