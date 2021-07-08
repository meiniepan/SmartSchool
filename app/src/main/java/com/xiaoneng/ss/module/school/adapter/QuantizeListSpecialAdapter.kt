package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.QuantizeBody
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import com.xiaoneng.ss.module.school.model.QuantizeTypeBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/07/06
 * Time: 17:32
 */
class QuantizeListSpecialAdapter(layoutId: Int, listData: MutableList<QuantizeBody>) :
    BaseQuickAdapter<QuantizeBody, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder, item: QuantizeBody) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvTimeS, item.stime)
        }
    }

}