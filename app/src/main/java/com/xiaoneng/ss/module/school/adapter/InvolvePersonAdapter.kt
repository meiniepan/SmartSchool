package com.xiaoneng.ss.module.school.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.eventBus.ManageInvolveEvent
import com.xiaoneng.ss.model.StudentBean
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class InvolvePersonAdapter(layoutId: Int, listData: MutableList<StudentBean>) :
    BaseQuickAdapter<StudentBean, BaseViewHolder>(layoutId, listData) {

    private var isShow: Boolean = false
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        EventBus.getDefault().register(this)
    }
    override fun convert(viewHolder: BaseViewHolder?, item: StudentBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvLabelInvolve, item.realname.last().toString())
                .setText(R.id.tvNameInvolve, item.realname)
            if (isShow) {
                holder.getView<View>(R.id.ivNo).visibility = View.VISIBLE
                holder.getView<View>(R.id.tvLabelInvolve).setOnClickListener {
                    mData.removeAt(holder.adapterPosition)
                    notifyItemRemoved(holder.adapterPosition)
                }
            } else {
                holder.getView<View>(R.id.ivNo).visibility = View.GONE
                holder.getView<View>(R.id.tvLabelInvolve).isClickable = false
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        EventBus.getDefault().unregister(this)
    }
    @Subscribe
    fun changeThemeEvent(event: ManageInvolveEvent) {
        isShow = event.flag
    }
}