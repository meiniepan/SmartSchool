package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.CheckBox
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
class ChooseCourseAdapter(layoutId: Int, listData: MutableList<AttCourseBean>) :
    BaseQuickAdapter<AttCourseBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: AttCourseBean) {
        viewHolder?.let { holder ->
            var llbac = holder.getView<View>(R.id.llChooseCourse)
            var tvCourseName = holder.getView<TextView>(R.id.tvCourseName2)
            var tvTeacherName = holder.getView<TextView>(R.id.tvTeacherName2)
            var tvRoomName = holder.getView<TextView>(R.id.tvRoomAtt2)
            var tvAttTime = holder.getView<TextView>(R.id.tvTimeAtt2)
            holder.setText(R.id.tvCourseName2, item?.coursename)
                .setText(R.id.tvTeacherName2, item?.teachername)
                .setText(R.id.tvRoomAtt2, item?.classroomname)
                .setText(R.id.tvTimeAtt2, item?.coursetime)


            holder.getView<CheckBox>(R.id.cbChooseTimetable).apply {
                isChecked = item.checked
                isEnabled = false
            }
//            if (item.checked) {
//                llbac.setBackgroundResource(R.drawable.bac_blue_bac_5)
//                tvCourseName.setTextColor(mContext.resources.getColor(R.color.white))
//                tvTeacherName.setTextColor(mContext.resources.getColor(R.color.white))
//                tvRoomName.setTextColor(mContext.resources.getColor(R.color.white))
//                tvAttTime.setTextColor(mContext.resources.getColor(R.color.white))
//
//            } else {
//                llbac.setBackgroundResource(R.drawable.bac_blue_line_5)
//                tvCourseName.setTextColor(mContext.resources.getColor(R.color.commonBlue))
//                tvTeacherName.setTextColor(mContext.resources.getColor(R.color.commonBlue))
//                tvRoomName.setTextColor(mContext.resources.getColor(R.color.commonBlue))
//                tvAttTime.setTextColor(mContext.resources.getColor(R.color.commonBlue))
//            }
//
        }
    }
}