package com.xiaoneng.ss.module.circular.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.displayImage
import com.xiaoneng.ss.module.school.model.FileInfoBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class NoticeImgAdapter(layoutId: Int, listData: MutableList<FileInfoBean>?) :
    BaseQuickAdapter<FileInfoBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: FileInfoBean?) {
        viewHolder?.let { holder ->
            var img = holder.getView<ImageView>(R.id.ivNoticeImg)
            displayImage(mContext, UserInfo.getUserBean().domain+item?.url, img)

        }
    }
}