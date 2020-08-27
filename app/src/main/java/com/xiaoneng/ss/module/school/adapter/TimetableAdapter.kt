package com.xiaoneng.ss.module.school.adapter

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.module.school.model.LessonBean
import com.xiaoneng.ss.module.school.model.TimetableBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class TimetableAdapter(layoutId: Int, listData: MutableList<TimetableBean>) :
    BaseQuickAdapter<TimetableBean, BaseViewHolder>(layoutId, listData) {

    private lateinit var mAdapter: LessonAdapter
    private lateinit var mRecycler: RecyclerView
    var mLessonData: ArrayList<LessonBean> = ArrayList()

    override fun convert(viewHolder: BaseViewHolder, item: TimetableBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvNameWeek, item?.week)

//                .setText(R.id.tvTime3,item?.noticetime)
//
//            holder.setText(R.id.tvAction, item?.title)

            mRecycler = holder.getView(R.id.rvLesson)
            mLessonData = item.list
            initAdapter(item)
        }
    }

    private fun initAdapter(item: TimetableBean) {
        mAdapter = LessonAdapter(R.layout.item_lesson, mLessonData)
        if (DateUtil.isSameDay(item.time.toLong()*1000)) {
            mAdapter.setColor(true)
        }else{
            mAdapter.setColor(false)
        }
        mRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }
}