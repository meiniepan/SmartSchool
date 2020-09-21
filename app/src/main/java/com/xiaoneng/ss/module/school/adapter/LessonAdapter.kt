package com.xiaoneng.ss.module.school.adapter

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.eventBus.ChangeMasterTimetableEvent
import com.xiaoneng.ss.module.school.model.CourseBean
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class LessonAdapter(layoutId: Int, listData: MutableList<CourseBean>) :
    BaseQuickAdapter<CourseBean, BaseViewHolder>(layoutId, listData) {
    private var isMaster: Boolean = false
    private var hasColor: Boolean = false
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        EventBus.getDefault().register(this)
    }
    override fun convert(viewHolder: BaseViewHolder, item: CourseBean) {
        viewHolder?.let { holder ->
            var ll = holder.getView<View>(R.id.llCourse)
            if (TextUtils.isEmpty(item.coursename)) {
                ll.visibility = View.INVISIBLE
            } else {
                ll.visibility = View.VISIBLE
                if (isMaster) {
                    holder.setText(R.id.tvNameCourse, item?.coursename)
                } else {
                    if (UserInfo.getUserBean().usertype =="2"||UserInfo.getUserBean().usertype =="99") {
                        holder.setText(R.id.tvNameCourse, item?.classname)
                    } else {
                        holder.setText(R.id.tvNameCourse, item?.coursename)
                    }

                }

                holder.setText(R.id.tvNameTeacherCourse, item?.campus)

                holder.setText(R.id.tvNameRoomCourse, item?.classroomname)
                if (hasColor) {
                    holder.setBackgroundRes(R.id.llCourse, R.drawable.bac_blue_bac_5)
                } else {
                    holder.setBackgroundRes(R.id.llCourse, R.drawable.bac_gray_bac_5)
                }
            }

        }
    }
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        EventBus.getDefault().unregister(this)
    }
    fun setColor(has: Boolean) {
        hasColor = has
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun changeThemeEvent(event: ChangeMasterTimetableEvent) {
        isMaster = event.flag
    }
}