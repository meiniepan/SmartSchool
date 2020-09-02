package com.xiaoneng.ss.common.utils

import android.animation.ValueAnimator
import android.content.Context
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import com.xiaoneng.ss.base.view.BaseApplication

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @CreateDate: 2020/4/5 20:26
 */

/**
 * dp与px转换
 *
 * @param context
 * @param dp
 * @return
 */
fun dp2px(context: Context = BaseApplication.instance.context, dp: Float): Float {
    val density = context.resources.displayMetrics.density
    return (dp * density + 0.5).toFloat()
}

fun dp2px(dp: Float): Float {
    val density = BaseApplication.instance.context.resources.displayMetrics.density
    return (dp * density + 0.5).toFloat()
}

fun sp2px(context: Context, sp: Float): Float {
    val scaleDensity = context.resources.displayMetrics.scaledDensity
    return (sp * scaleDensity + 0.5).toFloat()
}


fun startIntegralTextAnim(textView: TextView, value: Int, textType: String) {
    val animator = ValueAnimator.ofInt(0, value)
    //播放时长
    animator.duration = 1500
    animator.interpolator = DecelerateInterpolator()
    animator.addUpdateListener { animation ->
        //获取改变后的值
        val currentValue = animation.animatedValue as Int
        textView.text = "$textType $currentValue"
    }
    animator.start()
}
