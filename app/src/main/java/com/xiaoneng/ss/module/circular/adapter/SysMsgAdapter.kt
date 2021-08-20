package com.xiaoneng.ss.module.circular.adapter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.module.circular.model.NoticeBean
import com.xiaoneng.ss.module.school.view.*


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class SysMsgAdapter(layoutId: Int, listData: MutableList<NoticeBean>?) :
    BaseQuickAdapter<NoticeBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: NoticeBean?) {
        viewHolder?.let { holder ->
            var text0:TextView = holder.getView<TextView>(R.id.tvTitle3)
            var iv:ImageView = holder.getView(R.id.ivAction)
            when (item?.expand?.action) {
                "admin/spacebook/default" -> {
                    text0.text = mContext.resources.getString(R.string.siteTitle)
                    iv.background = mContext.resources.getDrawable(
                        R.drawable.ic_changdi)
                }
                "admin/schedules/default" -> {
                    text0.text = mContext.resources.getString(R.string.scheduleTitle)
                    iv.background = mContext.resources.getDrawable(
                        R.drawable.ic_richeng)
                }
                "moral/moral/default" -> {
                    text0.text = mContext.resources.getString(R.string.quantizeTitle)
                    iv.background = mContext.resources.getDrawable(
                        R.drawable.ic_quantize)
                }
                "admin/wages/default" -> {
                    text0.text = mContext.resources.getString(R.string.salaryTitle)
                    iv.background = mContext.resources.getDrawable(
                        R.drawable.ic_gongzitiao)
                }
                "admin/repair/default" -> {
                    text0.text = mContext.resources.getString(R.string.propertyTitle)
                    iv.background = mContext.resources.getDrawable(
                        R.drawable.ic_baoxiu)
                }
                "admin/tasks/default" -> {
                    text0.text = mContext.resources.getString(R.string.taskTitle)
                    iv.background = mContext.resources.getDrawable(
                        R.drawable.ic_renwu)
                }
                "disk/folder/default" -> {
                    text0.text = mContext.resources.getString(R.string.diskTitle)
                    iv.background = mContext.resources.getDrawable(
                        R.drawable.ic_yunpan)
                }
                "admin/attendances/default" -> {
                    text0.text = mContext.resources.getString(R.string.attendanceTitle)
                    iv.background = mContext.resources.getDrawable(
                        R.drawable.ic_kaoqin)
                }
                "admin/achievements/default" -> {
                    text0.text = mContext.resources.getString(R.string.achievementTitle)
                    iv.background = mContext.resources.getDrawable(
                        R.drawable.ic_chengji)
                }
                "admin/courses/default" -> {
                    text0.text = mContext.resources.getString(R.string.timetableTitle)
                    iv.background = mContext.resources.getDrawable(
                        R.drawable.ic_kebiao)
                }
            }
            holder.setText(R.id.tvAction, item?.title)
                .setText(R.id.tvTime3, DateUtil.formatShowTime(item?.noticetime!!))

            holder.getView<TextView>(R.id.tvAction).apply {
                if (item?.status == "1") {
                    setTextColor(mContext.resources.getColor(R.color.commonHint))
                } else {
                    setTextColor(mContext.resources.getColor(R.color.themeColor))
                }
            }
        }
    }
}