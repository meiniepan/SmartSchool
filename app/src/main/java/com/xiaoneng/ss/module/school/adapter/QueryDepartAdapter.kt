package com.xiaoneng.ss.module.school.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.UserBeanSimple


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class QueryDepartAdapter(layoutId: Int, listData: MutableList<UserBeanSimple>) :
    BaseQuickAdapter<UserBeanSimple, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: UserBeanSimple) {
        viewHolder?.let { holder ->
            var nameStr = item.realname
            holder.setText(R.id.tvName1Query, nameStr)
            if (item.usertype == "1") {
                holder.setText(R.id.tvName2Query, item.cno)
            } else {
                holder.setText(R.id.tvName2Query, item.dep_name)
            }


        }
    }
}