package com.xiaoneng.ss.module.school.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.FileTransInfo
import com.xiaoneng.ss.common.utils.formatMemorySize
import com.xiaoneng.ss.module.school.interfaces.IFileTrans
import com.xiaoneng.ss.module.school.model.DiskFileBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class CloudTransAdapter(
    layoutId: Int,
    listData: MutableList<DiskFileBean>?,
    val listener: IFileTrans
) :
    BaseQuickAdapter<DiskFileBean, BaseViewHolder>(layoutId, listData) {
    override fun convert(viewHolder: BaseViewHolder?, item: DiskFileBean?) {
        viewHolder?.let { holder ->
            var pb = holder.getView<ProgressBar>(R.id.pbFile)
            var action = holder.getView<ImageView>(R.id.ivAction)
            var total: Long = item?.totalSize ?: 0
            var p = 0L
            if (total != 0L) {
                p = (item?.currentSize ?: 0) * 100 / total

            } else {
                p = 0L
            }

            if (item?.status==2){
                pb.visibility = View.GONE
            }else{
                pb.visibility = View.VISIBLE
            }
            var fileSize =
                item?.currentSize?.formatMemorySize() + "/" + item?.totalSize?.formatMemorySize()
            pb.setProgress(p.toInt())

            holder.setText(R.id.tvDiskFileName, item?.path?.split("/")?.last())
                .setText(R.id.tvDiskFileSize, fileSize)
            if (item?.status == 0) {
                action.setImageResource(R.drawable.ic_pause_d)
                action.setOnClickListener {
                    item?.task?.cancel()
                    item.status = 1
                    FileTransInfo.modifyFile(item)
                    notifyItemChanged(holder.adapterPosition)

                }
            } else if (item?.status == 1) {
                action.setImageResource(R.drawable.ic_start_d)
                action.setOnClickListener {
                    listener.upload(item)
                    item.status = 0
                    FileTransInfo.modifyFile(item)
                    notifyItemChanged(holder.adapterPosition)
                }
            } else {
                action.setImageResource(R.drawable.ic_dustbin)
                action.setOnClickListener {
                    FileTransInfo.delFile(item!!)
                    mData.remove(item!!)
                    notifyItemRemoved(holder.adapterPosition)
                }
            }
        }
    }
}