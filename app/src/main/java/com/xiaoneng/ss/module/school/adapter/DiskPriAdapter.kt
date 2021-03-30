package com.xiaoneng.ss.module.school.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.module.school.model.AchievementBean
import com.xiaoneng.ss.module.school.model.DiskFileBean
import com.xiaoneng.ss.module.school.model.FolderBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class DiskPriAdapter(layoutId: Int, listData: MutableList<FolderBean>?) :
    BaseQuickAdapter<FolderBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: FolderBean?) {
        viewHolder?.let { holder ->
            holder.addOnClickListener(R.id.ivFolderInfo)
            holder.setText(R.id.tvDiskFileName, item?.foldername)
                .setText(R.id.tvDiskFileDate, DateUtil.formatShowTime(item?.updatetime?:""))
        }
    }
}