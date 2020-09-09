package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.LessonBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class ChooseCourseAdapter(layoutId: Int, listData: MutableList<LessonBean>) :
    BaseQuickAdapter<LessonBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: LessonBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvCourseName, item?.coursename)
                .setText(R.id.tvTeacherName,item?.teachername)
                .setText(R.id.tvRoomAtt,item?.classroomname)
                .setText(R.id.tvTimeAtt,item?.coursetime)
//
//            holder.setText(R.id.tvAction, item?.title)
        }
    }
}