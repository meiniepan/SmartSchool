package com.xiaoneng.ss.module.school.adapter

import android.widget.CheckBox
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.getBooleanString
import com.xiaoneng.ss.model.StudentBean
import com.xiaoneng.ss.module.school.model.DepartmentPersonBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class InvolvePersonAdapter(layoutId: Int, listData: MutableList<DepartmentPersonBean>) :
    BaseQuickAdapter<DepartmentPersonBean, BaseViewHolder>(layoutId, listData) {


    override fun convert(viewHolder: BaseViewHolder?, item: DepartmentPersonBean) {
        viewHolder?.let { holder ->
            var cbAll = holder.getView<CheckBox>(R.id.cbDepAll)
            cbAll.isChecked = item.isAll
            cbAll.setOnCheckedChangeListener { buttonView, isChecked ->
                item.isAll = isChecked
                item.data.forEach {
                    it.choice = getBooleanString(isChecked)
                }
                initRecycler(holder,item)
            }
            holder.setText(R.id.tvNameDepart, item.departmentsname)
            initRecycler(holder, item)
        }
    }

    private fun initRecycler(holder: BaseViewHolder, item: DepartmentPersonBean) {
        var eData: MutableList<StudentBean> = ArrayList()
        eData.clear()
        eData.addAll(item.data)
        var eRecyclerView = holder.getView<RecyclerView>(R.id.rvInvolvePerson2)
        var eAdapter = InvolvePerson2Adapter(R.layout.item_involve2, eData)
        eRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 5)
            setAdapter(eAdapter)
        }

        eAdapter.setOnItemClickListener { _, view, position ->
            if (eData[position].choice == "0") {
                eData[position].choice = "1"
            } else {
                eData[position].choice = "0"
            }
            eAdapter.notifyDataSetChanged()
        }
    }

}