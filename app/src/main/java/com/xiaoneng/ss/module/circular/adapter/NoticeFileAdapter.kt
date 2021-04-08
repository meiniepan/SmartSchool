package com.xiaoneng.ss.module.circular.adapter

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.formatMemorySize
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
            if (path.endsWith(".doc") || path.endsWith(".docx")) {
                holder.setBackgroundRes(R.id.ivNoticeFile, R.drawable.ic_type_word)
            } else if (path.endsWith(".xls") || path.endsWith(".xlsx")) {
                holder.setBackgroundRes(R.id.ivNoticeFile, R.drawable.ic_type_excel)
            } else if (path.endsWith(".pdf")) {
                holder.setBackgroundRes(R.id.ivNoticeFile, R.drawable.ic_type_pdf)
            } else if (path.endsWith(".zip") || path.endsWith(".rar") || path.endsWith(".tar") || path.endsWith(
                    ".gz"
                )
            ) {
                holder.setBackgroundRes(R.id.ivNoticeFile, R.drawable.ic_type_zip)
            } else {
                holder.setBackgroundRes(R.id.ivNoticeFile, R.drawable.ic_type_unknow)
            }

        }
    }

    fun canDel() {
        canDel = true
    }
}