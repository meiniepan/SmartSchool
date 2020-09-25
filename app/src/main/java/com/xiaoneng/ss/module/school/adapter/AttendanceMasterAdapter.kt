package com.xiaoneng.ss.module.school.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.recyclerview.StatusRecyclerView
import com.xiaoneng.ss.module.circular.adapter.AttTagsAdapter
import com.xiaoneng.ss.module.school.model.AttendanceBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class AttendanceMasterAdapter(layoutId: Int, listData: MutableList<AttendanceBean>) :
    BaseQuickAdapter<AttendanceBean, BaseViewHolder>(layoutId, listData) {
    lateinit var eAdapter: AttTagsAdapter
    lateinit var eData: ArrayList<String>
    override fun convert(viewHolder: BaseViewHolder?, item: AttendanceBean) {
        viewHolder?.let { holder ->
            holder.addOnClickListener(R.id.tvLeaveType)
            eData = ArrayList()
            holder.setText(R.id.tvStudentCode, item?.cno)
                .setText(R.id.tvStudentName, item?.realname)
//

            eData.addAll(item.tags!!)
            if (eData.size > 0) {
                initAdapter(holder.getView(R.id.rvAttTags))
            }
        }
    }

    private fun initAdapter(recyclerView: StatusRecyclerView) {
        eAdapter = AttTagsAdapter(R.layout.item_att_tags, eData)

        recyclerView.apply {
            layoutManager =
                object : LinearLayoutManager(context, HORIZONTAL, false) {

                }
            setAdapter(eAdapter)
        }
        eAdapter.setOnItemClickListener { _, view, position ->

        }
    }
}