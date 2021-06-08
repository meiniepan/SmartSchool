package com.xiaoneng.ss.module.circular.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.formatMemorySize
import com.xiaoneng.ss.common.utils.getFileIcon
import com.xiaoneng.ss.common.utils.toLongSafe
import com.xiaoneng.ss.module.school.model.FileInfoBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class NoticeFileAdapter(layoutId: Int, listData: MutableList<FileInfoBean>?) :
    BaseQuickAdapter<FileInfoBean, BaseViewHolder>(layoutId, listData) {
    var canDel = false
    override fun convert(viewHolder: BaseViewHolder?, item: FileInfoBean?) {
        viewHolder?.let { holder ->
            var path = item?.url ?: ""
            var size = item?.ext?.size.toLongSafe().formatMemorySize()

            var ivDelete: ImageView = holder.getView(R.id.ivPropertyDelete)
            ivDelete.setOnClickListener {
                mData.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
            }
            if (canDel) {
                ivDelete.visibility = View.VISIBLE
            } else {
                ivDelete.visibility = View.INVISIBLE
            }

            holder.setText(R.id.tvNoticeFileName, item?.name)
                .setText(R.id.tvNoticeFileSize, size)
            holder.setBackgroundRes(R.id.ivNoticeFile, path.getFileIcon())
        }
    }

    fun canDel() {
        canDel = true
    }
}