package com.xiaoneng.ss.module.sys.adapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.custom.interpolator.CustomScaleInterpolator
import com.xiaoneng.ss.module.circular.model.CircularResponse


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class SystemAdapter(layoutId: Int, listData: MutableList<CircularResponse>?) :
    BaseQuickAdapter<CircularResponse, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: CircularResponse?) {
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