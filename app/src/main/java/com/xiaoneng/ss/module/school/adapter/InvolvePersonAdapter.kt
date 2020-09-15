package com.xiaoneng.ss.module.school.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.eventBus.ManageInvolveEvent
import com.xiaoneng.ss.model.StudentBean
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

    override fun convert(viewHolder: BaseViewHolder?, item: StudentBean) {
        viewHolder?.let { holder ->
            holder.setText(R.id.tvLabelInvolve, item.realname.last().toString())
                .setText(R.id.tvNameInvolve, item.realname)
            if (isShow) {
                holder.getView<View>(R.id.ivNo).visibility = View.VISIBLE
            } else {
                holder.getView<View>(R.id.ivNo).visibility = View.GONE
            }
        }
    }

    @Subscribe
    fun changeThemeEvent(event: ManageInvolveEvent) {
        isShow = event.flag
    }
}