package com.xiaoneng.ss.module.school.adapter

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.model.StudentBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class InvolvePerson2Adapter(layoutId: Int, listData: MutableList<StudentBean>) :
    BaseQuickAdapter<StudentBean, BaseViewHolder>(layoutId, listData) {

    var isManager: Boolean = false

    override fun convert(viewHolder: BaseViewHolder?, item: StudentBean) {
        viewHolder?.let { holder ->
            if (item.realname.isNotEmpty()) {
                holder.setText(R.id.tvLabelInvolve, item.realname.last().toString())
                    .setText(R.id.tvNameInvolve, item.realname)
            }
            if (isManager) {
                holder.getView<View>(R.id.ivNo).visibility = View.VISIBLE

            } else {
                holder.getView<View>(R.id.ivNo).visibility = View.GONE
            }

            if (item.choice == "1") {
                holder.getView<TextView>(R.id.tvLabelInvolve).apply {
                    setBackgroundResource(R.drawable.bac_blue_oval)
                    setTextColor(mContext.resources.getColor(R.color.white))
                }

            } else {
                holder.getView<TextView>(R.id.tvLabelInvolve).apply {
                    setBackgroundResource(R.drawable.bac_blue_line_oval)
                    setTextColor(mContext.resources.getColor(R.color.commonBlue))
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (isManager) {
            val animatorX =
                ObjectAnimator.ofFloat(holder.itemView, "rotation", -5f, 5f)
            animatorX.repeatCount = ValueAnimator.INFINITE
            animatorX.repeatMode = ValueAnimator.RESTART
            animatorX.duration = 100
            animatorX.start()
        }
//        val animatorY =
//            ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0f, 10f)
//        animatorY.repeatCount = ValueAnimator.INFINITE
//        val set = AnimatorSet()
//        set.duration = 30
//        set.interpolator = CustomScaleInterpolator(0.4f)
//        set.playSequentially(animatorX, animatorY)
//        set.start()
    }


    fun setManage(isManager: Boolean) {
        this.isManager = isManager
    }
}