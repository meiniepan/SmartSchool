package com.xiaoneng.ss.module.school.adapter

import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.TextView
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
            var sendlabel = item?.sendlabel
            if (sendlabel == "all") {
                sendlabel = "所有人"
            } else if (sendlabel == "teacher") {
                sendlabel = "所有老师"
            } else if (sendlabel == "classmaster") {
                sendlabel = "所有班主任"
            } else if (sendlabel == "students") {
                sendlabel = "所有学生"
            }
            if (!isEmpty(sendlabel)) {
                ss = sendlabel + "、"
            }
            item?.involve?.let {
                ss = if (it.size > 3) {
                    ss + it[0].realname + "、" +
                            it[1].realname + "、" +
                            it[2].realname + "等" + it.size + "人..."
                } else {
                    if (it.size > 0) {
                        it.forEach {
                            ss = ss + it.realname + "、"
                        }
                        ss.substring(0, ss.length - 1)
                    } else {
                        if (ss.length > 0) {
                            ss = ss.substring(0, ss.length - 1)
                        }
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
                            mContext.resources.getColor(R.color.themeColor)
                        )
                    }
                    "1" -> {
                        statusStr = "已完成"
                        holder.setTextColor(
                            R.id.tvTaskStatus,
                            mContext.resources.getColor(R.color.themeColor)
                        )
                    }

                }
            } else if (type == "2") {
                when (item?.taskstatus) {//任务状态0待发布1进行中2完成3关闭
                    "0" -> {
                        statusStr = "草稿箱"
                        holder.setTextColor(
                            R.id.tvTaskStatus,
                            mContext.resources.getColor(R.color.themeColor)
                        )
                    }
                    "1" -> {
                        statusStr = "进行中"
                        holder.setTextColor(
                            R.id.tvTaskStatus,
                            mContext.resources.getColor(R.color.themeColor)
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
                .setText(R.id.tvTaskLine4, line4Str)

            var tvRemark = holder.getView<TextView>(R.id.tvTaskLine3)
            if (item?.remark.isNullOrEmpty()) {
                tvRemark.visibility = View.GONE
            } else {
                tvRemark.visibility = View.VISIBLE
                tvRemark.text = item?.remark
            }

        }
    }

    fun setType(type: String) {
        this.type = type
    }
}