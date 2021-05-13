package com.xiaoneng.ss.module.school.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.FolderBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/05/13
 * Time: 17:32
 */
class DiskPathAdapter(layoutId: Int, listData: MutableList<FolderBean>?) :
    BaseQuickAdapter<FolderBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: FolderBean?) {
        viewHolder?.let { holder ->

            var icon = holder.getView<ImageView>(R.id.ivDiskIcon)

            if (item?.isFolder==true){
                icon.setImageResource(R.drawable.ic_file)
                holder.setText(R.id.tvDiskFileName, item?.foldername)
            }

        }
    }
}