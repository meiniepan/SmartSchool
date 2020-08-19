package com.xiaoneng.ss.module.mine.adapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.custom.interpolator.CustomScaleInterpolator
import com.xiaoneng.ss.module.school.model.SchoolResponse


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class MineAdapter(layoutId: Int, listData: MutableList<SchoolResponse>?) :
    BaseQuickAdapter<SchoolResponse, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: SchoolResponse?) {
        viewHolder?.let { holder ->

        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.0f, 1.0f)
        val animatorY =
            ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.0f, 1.0f)
        val set = AnimatorSet()
        set.duration = 1000
        set.interpolator = CustomScaleInterpolator(0.4f)
        set.playTogether(animatorX, animatorY)
        set.start()
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val animatorX =
            ObjectAnimator.ofFloat(holder.itemView, "scaleX", 1.0f, 0.0f)
        val animatorY =
            ObjectAnimator.ofFloat(holder.itemView, "scaleY", 1.0f, 0.0f)
        val set = AnimatorSet()
        set.duration = 1000
        set.interpolator = CustomScaleInterpolator(0.4f)
        set.playTogether(animatorX, animatorY)
        set.start()
    }
}