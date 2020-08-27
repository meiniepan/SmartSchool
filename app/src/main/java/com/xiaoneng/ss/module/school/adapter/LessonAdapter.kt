package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.LessonBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class LessonAdapter(layoutId: Int, listData: MutableList<LessonBean>) :
    BaseQuickAdapter<LessonBean, BaseViewHolder>(layoutId, listData) {

    private var hasColor: Boolean = false

    override fun convert(viewHolder: BaseViewHolder, item: LessonBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvNameCourse, item?.coursename)
                .setText(R.id.tvNameTeacherCourse,item?.teachername)

            holder.setText(R.id.tvNameRoomCourse, item?.classroomname)
            if (hasColor){
                holder.setBackgroundRes(R.id.llCourse,R.drawable.bac_blue_bac_5)
            }else{
                holder.setBackgroundRes(R.id.llCourse,R.drawable.bac_gray_bac_5)
            }
        }
    }

    fun setColor(has:Boolean) {
        hasColor = has
    }

}