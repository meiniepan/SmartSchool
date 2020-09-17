package com.xiaoneng.ss.module.school.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.DateUtil
import com.xiaoneng.ss.module.school.model.CourseBean
import com.xiaoneng.ss.module.school.model.TimetableBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class TimetableAdapter(layoutId: Int, listData: MutableList<TimetableBean>) :
    BaseQuickAdapter<TimetableBean, BaseViewHolder>(layoutId, listData) {

    private var total: Int = 0


    override fun convert(viewHolder: BaseViewHolder, item: TimetableBean) {
        viewHolder?.let { holder ->

            initAdapter(holder, item)
        }
    }

    private fun initAdapter(holder: BaseViewHolder, item: TimetableBean) {
        lateinit var mAdapter: LessonAdapter
        var mRecycler: RecyclerView = holder.getView(R.id.rvLesson)
        var mLessonData: ArrayList<CourseBean> = ArrayList()
        var mRealLessonData: ArrayList<CourseBean> = ArrayList()
        mRecycler.isNestedScrollingEnabled = false
        mLessonData.addAll(item.list)
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
                    mRealLessonData.add(CourseBean())
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

    fun setTotalSize(total: Int) {
        this.total = total
    }
}