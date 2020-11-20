package com.xiaoneng.ss.module.school.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.displayImage


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/11/12
 * Time: 17:32
 */
class DisplayImgAdapter(layoutId: Int, listData: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: String) {
        viewHolder?.let { holder ->
            var imageView: ImageView = holder.getView(R.id.ivPropertyAddPic)
            displayImage(
                mContext, item,
                imageView
            )
        }
    }
}