package com.xiaoneng.ss.module.school.adapter

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
class TimetableAdapter(layoutId: Int, listData: MutableList<TimetableBean>, val total: Int) :
    BaseQuickAdapter<TimetableBean, BaseViewHolder>(layoutId, listData) {
    private lateinit var mAdapter: LessonAdapter
    private lateinit var mRecycler: RecyclerView
    var mLessonData: ArrayList<LessonBean> = ArrayList()
    var mRealLessonData: ArrayList<LessonBean> = ArrayList()

    override fun convert(viewHolder: BaseViewHolder, item: TimetableBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvNameWeek, item?.week)
//                .setText(R.id.tvTime3,item?.noticetime)
//
//            holder.setText(R.id.tvAction, item?.title)

            mRecycler = holder.getView(R.id.rvLesson)
            mRecycler.isNestedScrollingEnabled = false
            holder.adapterPosition
            mRealLessonData.clear()
            mLessonData = item.list
            initAdapter(item)
        }
    }

    private fun initAdapter(item: TimetableBean) {
        if (total > 0 && mLessonData.size > 0) {
            var ii = 0
            for (i in 1 until total + 1) {
                if (ii >= mLessonData.size) {
                    break
                }
                if (mLessonData[ii].position == i.toString()) {
                    mRealLessonData.add(mLessonData[ii])
                    ii += 1
                } else {
                    mRealLessonData.add(LessonBean())
                }
            }
        }
        mAdapter = LessonAdapter(R.layout.item_lesson, mRealLessonData)
        if (DateUtil.isSameDay(item.time.toLong() * 1000)) {
            mAdapter.setColor(true)
        } else {
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