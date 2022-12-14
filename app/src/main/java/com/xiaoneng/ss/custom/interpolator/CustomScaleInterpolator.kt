package com.xiaoneng.ss.custom.interpolator

import android.view.animation.Interpolator

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 20:16
 */

class CustomScaleInterpolator(elasticFactor: Float) :
    Interpolator {

    private val elasticFactor: Float

    override fun getInterpolation(input: Float): Float {
        return (Math.pow(
            2.0,
            -10 * input.toDouble()
        ) * Math.sin((input - elasticFactor / 4) * (2 * Math.PI) / elasticFactor) + 1).toFloat()
    }

    init {
        this.elasticFactor = elasticFactor
    }
}