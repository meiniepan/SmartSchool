package com.xiaoneng.ss.module.mine.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.model.StudentBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class ChooseChildAdapter(layoutId: Int, listData: MutableList<StudentBean>?) :
    BaseQuickAdapter<StudentBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: StudentBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvPhoneParent, item.phone)

        }
    }
}