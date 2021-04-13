package com.xiaoneng.ss.module.school.adapter

import android.media.Image
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
            holder.addOnClickListener(R.id.cbDiskFile)

            var icon = holder.getView<ImageView>(R.id.ivDiskIcon)
            holder.setGone(R.id.ivFolderInfo,item?.isFolder==true)

            if (item?.isFolder==true){
                icon.setImageResource(R.drawable.ic_file)
                holder.setText(R.id.tvDiskFileName, item?.foldername)
                    .setText(R.id.tvDiskFileDate, DateUtil.formatShowTime(item?.updatetime?:""))
            }else{
                if (item?.path!!.endsWith(".doc") || item?.path!!.endsWith(".docx")) {
                    holder.setImageResource(R.id.ivDiskIcon, R.drawable.ic_type_word)
                } else if (item?.path!!.endsWith(".xls") || item?.path!!.endsWith(".xlsx")) {
                    holder.setImageResource(R.id.ivDiskIcon, R.drawable.ic_type_excel)
                } else if (item?.path!!.endsWith(".pdf")) {
                    holder.setImageResource(R.id.ivDiskIcon, R.drawable.ic_type_pdf)
                } else if (item?.path!!.endsWith(".zip") || item?.path!!.endsWith(".rar") || item?.path!!.endsWith(".tar") || item?.path!!.endsWith(
                        ".gz"
                    )
                ) {
                    holder.setImageResource(R.id.ivDiskIcon, R.drawable.ic_type_zip)
                } else {
                    holder.setImageResource(R.id.ivDiskIcon, R.drawable.ic_type_unknow)
                }
                holder.setText(R.id.tvDiskFileName, item?.filename)
                    .setText(R.id.tvDiskFileDate, DateUtil.formatShowTime(item?.updatetime?:""))
            }

        }
    }
}