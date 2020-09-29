package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.module.school.model.AchievementBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class AchievementStuAdapter(layoutId: Int, listData: MutableList<AchievementBean>?) :
    BaseQuickAdapter<AchievementBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: AchievementBean?) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvTimePerformance, DateUtil.formatShowTime(item?.testtime?:""))
                .setText(R.id.tvScore1Performance,item?.achievement)
            holder.setText(R.id.tvRank1Performance, item?.ranking)
        }
    }
}