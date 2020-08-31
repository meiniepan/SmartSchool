package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.TaskBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class TaskStatusAdapter(layoutId: Int, listData: MutableList<TaskBean>?) :
    BaseQuickAdapter<TaskBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: TaskBean?) {
        viewHolder?.let { holder ->
            var statusStr = ""
            var line2Str = "负责人："
            var line4Str = "参与人："

            when (item?.status) {
                "1" -> {
                    statusStr = "进行中"
                }
                "0" -> {
                    statusStr = "未发布"
                }
                "3" -> {
                    statusStr = "已关闭"
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