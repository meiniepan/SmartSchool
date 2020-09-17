package com.xiaoneng.ss.module.school.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.recyclerview.StatusRecyclerView
import com.xiaoneng.ss.module.school.model.AttendanceSchoolBean
import com.xiaoneng.ss.module.school.model.AttendanceStuBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class AttendanceSchoolAdapter(layoutId: Int, listData: MutableList<AttendanceSchoolBean>) :
    BaseQuickAdapter<AttendanceSchoolBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: AttendanceSchoolBean) {
        viewHolder?.let { holder ->
            lateinit var eAdapter: AttendanceSchoolItemAdapter
            var eData: ArrayList<AttendanceStuBean> = ArrayList()
            if (holder.adapterPosition == 0) {
                holder.setText(R.id.tvLeaveTypeSchool, "病假")
                    .setText(R.id.tvLeaveValueSchool, item?.sickleave + "人")
                eData.addAll(item.sickleavelist!!)
            } else if (holder.adapterPosition == 1) {
                holder.setText(R.id.tvLeaveTypeSchool, "事假")
                    .setText(R.id.tvLeaveValueSchool, item?.thingleave + "人")
                eData.addAll(item.thingleavelist!!)
            } else if (holder.adapterPosition == 2) {
                holder.setText(R.id.tvLeaveTypeSchool, "早间迟到")
                    .setText(R.id.tvLeaveValueSchool, item?.morninglate + "人")
                eData.addAll(item.morninglatelist!!)
            } else if (holder.adapterPosition == 3) {
                holder.setText(R.id.tvLeaveTypeSchool, "课堂迟到")
                    .setText(R.id.tvLeaveValueSchool, item?.courselate + "人")
                eData.addAll(item.courselatelist!!)
            } else if (holder.adapterPosition == 4) {
                holder.setText(R.id.tvLeaveTypeSchool, "旷课")
                    .setText(R.id.tvLeaveValueSchool, item?.truant + "人")
                eData.addAll(item.truantlist!!)
            }

            if (eData.size > 0) {
                var recyclerView = holder.getView<RecyclerView>(R.id.rvAttItemSchool)
                eAdapter = AttendanceSchoolItemAdapter(R.layout.item_attendance_school_item, eData)

                recyclerView.apply {
                    layoutManager = LinearLayoutManager(mContext)
                    setAdapter(eAdapter)
                }
                eAdapter.setOnItemClickListener { _, view, position ->

                }
            }
        }
    }

    private fun initAdapter(recyclerView: StatusRecyclerView) {


    }
}