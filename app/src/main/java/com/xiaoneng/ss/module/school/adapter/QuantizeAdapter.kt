package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.QuantizeTypeBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/06/24
 * Time: 17:32
 */
class QuantizeAdapter(layoutId: Int, listData: MutableList<QuantizeTypeBean>) :
    BaseQuickAdapter<QuantizeTypeBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder, item: QuantizeTypeBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvQuantizeName, item.typename)
        }
    }

}