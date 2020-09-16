package com.xiaoneng.ss.module.school.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.DepartmentBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class DepartmentAdapter(layoutId: Int, listData: MutableList<DepartmentBean>) :
    BaseQuickAdapter<DepartmentBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: DepartmentBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvDepart, item.name)
                .setText(R.id.tvNum, item.num)
            if (item.num == "0") {
                holder.getView<View>(R.id.tvNum).visibility = View.GONE
            } else {
                holder.getView<View>(R.id.tvNum).visibility = View.VISIBLE
            }

        }
    }
}