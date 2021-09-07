package com.xiaoneng.ss.module.school.adapter

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.InvolveLabelBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class InvolveLabelAdapter(layoutId: Int, listData: MutableList<InvolveLabelBean>) :
    BaseQuickAdapter<InvolveLabelBean, BaseViewHolder>(layoutId, listData) {


    override fun convert(viewHolder: BaseViewHolder?, item: InvolveLabelBean) {
        viewHolder?.let { holder ->
            var cbAll = holder.getView<CheckBox>(R.id.cbLabel)
            cbAll.isChecked = item.checked
            cbAll.setOnCheckedChangeListener { buttonView, isChecked ->
                item.checked = isChecked
            }
            holder.setText(R.id.tvLabel, item.name)
        }
    }

}