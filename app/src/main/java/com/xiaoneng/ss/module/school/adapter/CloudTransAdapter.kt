package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.ProgressBar
import com.arialyy.aria.core.Aria
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.FileDownloadInfo
import com.xiaoneng.ss.common.state.FileTransInfo
import com.xiaoneng.ss.common.utils.formatMemorySize
import com.xiaoneng.ss.common.utils.getFileIcon
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
    var type: Int = 0
    override fun convert(viewHolder: BaseViewHolder?, item: DiskFileBean?) {
        viewHolder?.let { holder ->
            holder.addOnClickListener(R.id.cbDiskFile)
            holder.setImageResource(R.id.ivDiskIcon, item?.objectid!!.getFileIcon())
            var pb = holder.getView<ProgressBar>(R.id.pbFile)
            var action = holder.getView<ImageView>(R.id.ivAction)
            var cb = holder.getView<CheckBox>(R.id.cbDiskFile)
            cb.isChecked = item?.isChecked ?: false
            var total: Long = item?.totalSize ?: 0
            var p = 0L
            if (item?.progress != 0L) {
                p = item?.progress ?: 0
            } else {
                if (total != 0L) {
                    p = (item?.currentSize ?: 0) * 100 / total

                } else {
                    p = 0L
                }
            }
            if (item?.status == 2) {
                pb.visibility = View.GONE
            } else {
                pb.visibility = View.VISIBLE
            }
            var fileSize =
                item?.currentSize?.formatMemorySize() + "/" + item?.totalSize?.formatMemorySize()
            var fileSizeTotal = item?.totalSize?.formatMemorySize()
            pb.setProgress(p.toInt())
            var fileName: String? = ""
            if (item?.filename.isNullOrEmpty()) {
                fileName = item?.path?.split("/")?.last()
            } else {
                fileName = item?.filename
            }
            holder.setText(R.id.tvDiskFileName, fileName)

            if (item?.status == 0) {
                holder.setText(R.id.tvDiskFileSize, fileSize)
                action.visibility = View.VISIBLE
                action.setImageResource(R.drawable.ic_pause_d)
                action.setOnClickListener {
                    item.status = 1
                    if (type == 0) {
                        item?.task?.cancel()
                        FileTransInfo.modifyFile(item)
                    } else {
                        Aria.download(this)
                            .load(item.downTaskId)     //读取任务id
                            .stop();       // 停止任务
                        //.resume();    // 恢复任务
                        FileDownloadInfo.modifyFile(item)
                    }

                    notifyItemChanged(holder.adapterPosition)

                }
            } else if (item?.status == 1) {
                holder.setText(R.id.tvDiskFileSize, fileSize)
                action.visibility = View.VISIBLE
                action.setImageResource(R.drawable.ic_start_d)
                action.setOnClickListener {
                    item.status = 0
                    if (type == 0) {
                        listener.upload(item)
                        FileTransInfo.modifyFile(item)
                    } else {
                        Aria.download(this)
                            .load(item.downTaskId)     //读取任务id
                            .resume();    // 恢复任务
                        FileDownloadInfo.modifyFile(item)
                    }

                    notifyItemChanged(holder.adapterPosition)
                }
            } else {
                holder.setText(R.id.tvDiskFileSize, fileSizeTotal)
                action.setImageResource(R.drawable.ic_dustbin)
                action.visibility = View.GONE
            }
        }
    }

}
