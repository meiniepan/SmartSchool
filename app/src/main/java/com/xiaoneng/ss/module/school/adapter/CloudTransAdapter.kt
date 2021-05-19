package com.xiaoneng.ss.module.school.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jiang.awesomedownloader.downloader.AwesomeDownloader
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.FileDownloadInfo
import com.xiaoneng.ss.common.state.FileTransInfo
import com.xiaoneng.ss.common.utils.eventBus.FileDownloadEvent
import com.xiaoneng.ss.common.utils.formatMemorySize
import com.xiaoneng.ss.module.school.interfaces.IFileTrans
import com.xiaoneng.ss.module.school.model.DiskFileBean
import java.io.File


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
            var pb = holder.getView<ProgressBar>(R.id.pbFile)
            var action = holder.getView<ImageView>(R.id.ivAction)
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
            pb.setProgress(p.toInt())
            var fileName: String? = ""
            if (item?.filename.isNullOrEmpty()) {
                fileName = item?.path?.split("/")?.last()
            } else {
                fileName = item?.filename
            }
            holder.setText(R.id.tvDiskFileName, fileName)
                .setText(R.id.tvDiskFileSize, fileSize)
            if (item?.status == 0) {
                action.setImageResource(R.drawable.ic_pause_d)
                action.setOnClickListener {
                        item.status = 1
                    if (type == 0) {
                        item?.task?.cancel()
                        FileTransInfo.modifyFile(item)
                    } else {
                        AwesomeDownloader.stopAll()
                        FileDownloadInfo.modifyFile(item)
                    }

                    notifyItemChanged(holder.adapterPosition)

                }
            } else if (item?.status == 1) {
                action.setImageResource(R.drawable.ic_start_d)
                action.setOnClickListener {
                        item.status = 0
                    if (type == 0) {
                        listener.upload(item)
                        FileTransInfo.modifyFile(item)
                    } else {
                        AwesomeDownloader.resumeAndStart()
                        AwesomeDownloader.setOnProgressChange { progress ->
                            //do something...
                            var bean = DiskFileBean(
                                 progress = progress
                            )
                            FileDownloadInfo.modifyFile(bean)
                            FileDownloadEvent(bean).post()
                        }.setOnStop { downloadBytes, totalBytes ->
                            //do something...
                        }.setOnFinished { filePath, fileName ->
                            var bean = DiskFileBean(
                                 status = 2
                            )
                            FileDownloadInfo.modifyFile(bean)
                            FileDownloadEvent(bean).post()
                        }.setOnError { exception ->
                            //do something...
                        }
                        FileDownloadInfo.modifyFile(item)
                    }

                    notifyItemChanged(holder.adapterPosition)
                }
            } else {
                action.setImageResource(R.drawable.ic_dustbin)
                action.setOnClickListener {
                    if (type == 0) {
                        FileTransInfo.delFile(item!!)
                    } else {
                        FileDownloadInfo.delFile(item!!)
                    }
                    mData.remove(item!!)
                    notifyItemRemoved(holder.adapterPosition)
                }
            }
        }
    }

}
