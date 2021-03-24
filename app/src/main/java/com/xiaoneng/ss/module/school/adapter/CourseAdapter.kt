package com.xiaoneng.ss.module.school.adapter

import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.module.school.model.CourseBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class CourseAdapter(layoutId: Int, listData: MutableList<CourseBean>) :
    BaseQuickAdapter<CourseBean, BaseViewHolder>(layoutId, listData) {
    private var isMaster: Boolean = false
    private var hasColor: Boolean = false

    override fun convert(viewHolder: BaseViewHolder, item: CourseBean) {
        viewHolder?.let { holder ->
            var ll = holder.getView<View>(R.id.llCourse)
            if (TextUtils.isEmpty(item.coursename)) {
                ll.visibility = View.INVISIBLE
            } else {
                ll.visibility = View.VISIBLE
                if (isMaster) {
                    holder.setText(R.id.tvNameCourse, item?.coursename)
                } else {
                    if (AppInfo.checkRule2("teacher/courses/timeTable")) {
                        holder.setText(R.id.tvNameCourse, item?.levelname+item?.classname)
                    } else {
                        holder.setText(R.id.tvNameCourse, item?.coursename)
                    }

                }

                holder.setText(R.id.tvNameTeacherCourse, item?.campus)

                holder.setText(R.id.tvNameRoomCourse, item?.classroomname)
//                if (hasColor) {
//                    holder.setBackgroundRes(R.id.llCourse, R.drawable.bac_blue_bac_5)
//                } else {
//                    holder.setBackgroundRes(R.id.llCourse, R.drawable.bac_gray_bac_5)
//                }
            }

        }
    }

    fun setColor(has: Boolean) {
        hasColor = has
    }

    fun setMaster(isMaster: Boolean) {
        this.isMaster = isMaster
    }

}