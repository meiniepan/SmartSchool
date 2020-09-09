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
class AttLessonAdapter(layoutId: Int, listData: MutableList<LessonBean>) :
    BaseQuickAdapter<LessonBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: LessonBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvNameCourse2, item?.coursename)

//
//            holder.setText(R.id.tvAction, item?.title)
        }
    }
}