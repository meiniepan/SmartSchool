package com.xiaoneng.ss.module.school.adapter

import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.recyclerview.StatusRecyclerView
import com.xiaoneng.ss.module.school.model.SchoolBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/12/17
 * Time: 17:32
 */
class SchoolAdapter(layoutId: Int, listData: MutableList<SchoolBean>?) :
    BaseQuickAdapter<SchoolBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: SchoolBean?) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvSchoolLabel, item?.name)
            initRecycler(holder, item)
        }
    }

    private fun initRecycler(holder: BaseViewHolder, item: SchoolBean?) {
        var recyclerView = holder.getView<StatusRecyclerView>(R.id.rvSchoolItem)
        var eAdapter = SchoolItemAdapter(R.layout.item_school_item, item?.items)
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            setAdapter(eAdapter)
        }
    }
}