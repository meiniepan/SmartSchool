package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.FileDownloadInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.endIsImage
import com.xiaoneng.ss.common.utils.endIsVideo
import com.xiaoneng.ss.common.utils.getFileIcon
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
    var isPrivate = true
    override fun convert(viewHolder: BaseViewHolder?, item: FolderBean?) {
        viewHolder?.let { holder ->
            holder.addOnClickListener(R.id.ivFolderInfo)
            holder.addOnClickListener(R.id.cbDiskFile)

            var cb = holder.getView<CheckBox>(R.id.cbDiskFile)
            cb.isChecked = item?.isChecked ?: false
            cb.visibility = View.VISIBLE
            var icon = holder.getView<ImageView>(R.id.ivDiskIcon)
            var creator = holder.getView<TextView>(R.id.tvCreator)

            var isAdmin = UserInfo.getUserBean().uid == item?.cuser_id && !isPrivate
            var isNotAdmin = UserInfo.getUserBean().uid != item?.cuser_id && !isPrivate
            holder.setGone(R.id.ivAdmin, isAdmin)
            if (item?.isFolder == true && isNotAdmin) {
                cb.visibility = View.INVISIBLE
            }
            var b = (item?.isFolder == true && (isPrivate || isAdmin))
            holder.setGone(R.id.ivFolderInfo, b)
            if (isPrivate) {
                creator.visibility = View.GONE
            } else {
                creator.visibility = View.VISIBLE
                creator.text = item?.cuser_realname
            }
            if (item?.isFolder == true) {
                icon.setImageResource(R.drawable.ic_file)
                holder.setText(R.id.tvDiskFileName, item?.foldername)
                    .setText(R.id.tvDiskFileDate, DateUtil.formatShowTime(item?.updatetime ?: ""))
            } else {
                var name = holder.getView<TextView>(R.id.tvDiskFileName)
                if (FileDownloadInfo.hasFile(item?.objectid)) {
                    name.setTextColor(mContext.resources.getColor(R.color.titleBlack))
                }else{
                    name.setTextColor(mContext.resources.getColor(R.color.commonHint))
                }
                holder.setImageResource(R.id.ivDiskIcon, item?.objectid!!.getFileIcon())
                holder.setText(R.id.tvDiskFileName, item?.filename)
                    .setText(R.id.tvDiskFileDate, DateUtil.formatShowTime(item?.updatetime ?: ""))
            }

        }
    }
}