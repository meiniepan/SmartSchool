package com.xiaoneng.ss.module.school.adapter

import android.widget.ProgressBar
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.common.utils.formatMemorySize
import com.xiaoneng.ss.module.school.model.DiskFileBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class CloudTransAdapter(layoutId: Int, listData: MutableList<DiskFileBean>?) :
    BaseQuickAdapter<DiskFileBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: DiskFileBean?) {
        viewHolder?.let { holder ->
            var pb = holder.getView<ProgressBar>(R.id.pbFile)
            var p = (item?.currentSize?:0)/(item?.totalSize?:1)*100
            var fileSize = item?.currentSize?.formatMemorySize()+"/"+item?.totalSize?.formatMemorySize()
            pb.setProgress(p.toInt())
            holder.setText(R.id.tvDiskFileName, DateUtil.formatShowTime(item?.id?:""))
        }
    }
}