package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import com.xiaoneng.ss.module.school.model.QuantizeTypeBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/07/06
 * Time: 17:32
 */
class QuantizeListAdapter(layoutId: Int, listData: MutableList<QuantizeTemplateBean>) :
    BaseQuickAdapter<QuantizeTemplateBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder, item: QuantizeTemplateBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvQuantizeName, item.value)
        }
    }

}