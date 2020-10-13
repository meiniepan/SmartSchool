package com.xiaoneng.ss.custom.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView


/**
 * @author Burning
 * @description:
 * @date :2020/10/13 1:40 PM
 */
class FingerMoveView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var lastL: Int = 0
    private var lastT: Int = 0
    private var originalLeft: Int = 0
    private var isFirst: Boolean = true

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (isFirst) {
            originalLeft = left
            isFirst = false
        }
        var x = event.rawX
        var y = event.rawY

        when (event.getAction()) {
            MotionEvent.ACTION_DOWN -> {
                lastX = x
                lastY = y
                lastL = left
                lastT = top
            }
            MotionEvent.ACTION_MOVE -> {

                var disX = x - lastX
                var disY = y - lastY
                var left = lastL + disX
                var top = lastT + disY

                var parent = parent as View
                if (left < 0) {
                    left = 0f
                }
                if (left > parent.right-width) {
                    left = parent.right.toFloat()-width
                }
                if (top > parent.height-height) {
                    top = parent.height.toFloat()-height
                }
                if (top < 0) {
                    top = 0f
                }
                val width = left + width
                val height = top + height
                layout(left.toInt(), top.toInt(), width.toInt(), height.toInt())

            }

            MotionEvent.ACTION_UP -> {
                val top = top
                val height = top + height

                val anim = ValueAnimator.ofInt(left, originalLeft)
                anim.addUpdateListener {
                    var left = it.animatedValue as Int
                    layout(left, top, left + width, height)
                }
                anim.duration = 300
                anim.start()
            }
        }
        return true
    }


}