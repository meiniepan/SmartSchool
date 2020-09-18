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

    private var type: String = "0"

    override fun convert(viewHolder: BaseViewHolder?, item: TaskDetailBean?) {
        viewHolder?.let { holder ->
            var statusStr = ""
            var line2Str = "负责人：" + item?.operatorname
            var ss = ""
            item?.involve?.let {
                ss = if (it.size > 3) {
                    it[0].name + "、" +
                            it[1].name + "、" +
                            it[2].name + "等" + item.involve.size + "人..."
                } else {
                    if (it.size > 0) {
                        it.forEach {
                           ss = ss + it.name + "、"
                        }
                        ss.substring(0,ss.length-1)
                    } else {
                        ss
                    }

                }

            }
            var line4Str = "参与人：$ss"
            if (type == "1") {
                when (item?.completestatus) {//任务状态0待发布1进行中2完成3关闭

                    "0" -> {
                        statusStr = "未完成"
                        holder.setTextColor(
                            R.id.tvTaskStatus,
                            mContext.resources.getColor(R.color.commonBlue)
                        )
                    }
                    "1" -> {
                        statusStr = "已完成"
                        holder.setTextColor(
                            R.id.tvTaskStatus,
                            mContext.resources.getColor(R.color.commonBlue)
                        )
                    }

                }
            } else if (type == "2") {
                when (item?.status) {//任务状态0待发布1进行中2完成3关闭
                    "0" -> {
                        statusStr = "草稿箱"
                        holder.setTextColor(
                            R.id.tvTaskStatus,
                            mContext.resources.getColor(R.color.commonBlue)
                        )
                    }
                    "1" -> {
                        statusStr = "进行中"
                        holder.setTextColor(
                            R.id.tvTaskStatus,
                            mContext.resources.getColor(R.color.commonBlue)
                        )
                    }
                    "3" -> {
                        statusStr = "已关闭"
                        holder.setTextColor(
                            R.id.tvTaskStatus,
                            mContext.resources.getColor(R.color.commonHint)
                        )
                    }

                }

            }

            holder.setText(R.id.tvTaskStatus, statusStr)
                .setText(R.id.tvTitle4, item?.taskname)
                .setText(R.id.tvTaskLine2, line2Str)
                .setText(R.id.tvTaskLine3, item?.remark)
                .setText(R.id.tvTaskLine4, line4Str)

        }
    }

    fun setType(type: String) {
        this.type = type
    }
}