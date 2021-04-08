package com.xiaoneng.ss.module.school.adapter

import android.view.View
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
class AddImgAdapter(layoutId: Int, listData: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: String) {
        viewHolder?.let { holder ->
           var imageView: ImageView  = holder.getView(R.id.ivPropertyAddPic)
           var ivDelete: ImageView  = holder.getView(R.id.ivPropertyDelete)
            ivDelete.setOnClickListener {
                mData.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
            }
            if (holder.layoutPosition==mData.size-1){
                displayImage(
                    mContext, R.drawable.bac_add_2,
                    imageView
                )
                ivDelete.visibility = View.INVISIBLE
            }else{
                displayImage(
                    mContext, item,
                    imageView
                )
                ivDelete.visibility = View.VISIBLE
            }

        }
    }
}