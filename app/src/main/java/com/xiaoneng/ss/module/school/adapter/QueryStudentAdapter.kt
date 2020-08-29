package com.xiaoneng.ss.module.school.adapter

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
class QueryStudentAdapter(layoutId: Int, listData: MutableList<StudentBean>) :
    BaseQuickAdapter<StudentBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: StudentBean) {
        viewHolder?.let { holder ->
            var nameStr = item.realname + "-" + item.classname
            holder.setText(R.id.tvName1Query, nameStr)
                .setText(R.id.tvName2Query, item.cno)

        }
    }
}