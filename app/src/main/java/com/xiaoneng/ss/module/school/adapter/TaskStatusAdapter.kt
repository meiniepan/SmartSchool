package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.TaskDetailBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class TaskStatusAdapter(layoutId: Int, listData: MutableList<TaskDetailBean>?) :
    BaseQuickAdapter<TaskDetailBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: TaskDetailBean?) {
        viewHolder?.let { holder ->
            var statusStr = ""
            var line2Str = "负责人："
            var line4Str = "参与人："

            when (item?.status) {//草稿箱1，进行中2，已关闭3，未完成4，已完成5
                "1" -> {
                    statusStr = "草稿箱"
                    holder.setTextColor(R.id.tvTaskStatus,mContext.resources.getColor(R.color.commonBlue))
                }
                "2" -> {
                    statusStr = "进行中"
                    holder.setTextColor(R.id.tvTaskStatus,mContext.resources.getColor(R.color.commonBlue))
                }
                "3" -> {
                    statusStr = "已关闭"
                    holder.setTextColor(R.id.tvTaskStatus,mContext.resources.getColor(R.color.commonHint))
                }
                "4" -> {
                    statusStr = "未完成"
                    holder.setTextColor(R.id.tvTaskStatus,mContext.resources.getColor(R.color.commonBlue))
                }
                "5" -> {
                    statusStr = "已完成"
                    holder.setTextColor(R.id.tvTaskStatus,mContext.resources.getColor(R.color.commonBlue))
                }

            }
            holder.setText(R.id.tvTaskStatus, statusStr)
                .setText(R.id.tvTitle4, item?.taskname)
                .setText(R.id.tvTaskLine2, line2Str)
                .setText(R.id.tvTaskLine3, item?.remark)
                .setText(R.id.tvTaskLine4, line4Str)

        }
    }
}