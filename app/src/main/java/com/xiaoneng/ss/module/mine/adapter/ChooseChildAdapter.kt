package com.xiaoneng.ss.module.mine.adapter

import android.widget.CheckBox
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.model.StudentBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class ChooseChildAdapter(layoutId: Int, listData: MutableList<StudentBean>?) :
    BaseQuickAdapter<StudentBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: StudentBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvNameChild, item.realname)
            holder.getView<CheckBox>(R.id.cbChild).apply {
                isChecked = item.choice == "1"
                isEnabled = false
            }
        }
    }
}