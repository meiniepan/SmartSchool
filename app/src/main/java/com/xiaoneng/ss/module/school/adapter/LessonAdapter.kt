package com.xiaoneng.ss.module.school.adapter

import android.text.TextUtils
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.SPreference
import com.xiaoneng.ss.module.school.model.LessonBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class LessonAdapter(layoutId: Int, listData: MutableList<LessonBean>) :
    BaseQuickAdapter<LessonBean, BaseViewHolder>(layoutId, listData) {
    private var isMaster: Boolean by SPreference(Constant.IS_MASTER, false)
    private var hasColor: Boolean = false

    override fun convert(viewHolder: BaseViewHolder, item: LessonBean) {
        viewHolder?.let { holder ->
            var ll = holder.getView<View>(R.id.llCourse)
            if (TextUtils.isEmpty(item.coursename)) {
                ll.visibility = View.INVISIBLE
            } else {
                ll.visibility = View.VISIBLE
                if (isMaster) {
                    holder.setText(R.id.tvNameCourse, item?.coursename)
                } else {
                    if (UserInfo.getUserBean().usertype =="2"||UserInfo.getUserBean().usertype =="99") {
                        holder.setText(R.id.tvNameCourse, item?.classname)
                    } else {
                        holder.setText(R.id.tvNameCourse, item?.coursename)
                    }

                }

                holder.setText(R.id.tvNameTeacherCourse, item?.campus)

                holder.setText(R.id.tvNameRoomCourse, item?.classroomname)
                if (hasColor) {
                    holder.setBackgroundRes(R.id.llCourse, R.drawable.bac_blue_bac_5)
                } else {
                    holder.setBackgroundRes(R.id.llCourse, R.drawable.bac_gray_bac_5)
                }
            }

        }
    }

    fun setColor(has: Boolean) {
        hasColor = has
    }

}