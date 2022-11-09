package com.xiaoneng.ss.module.circular.adapter

import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.formatMemorySize
import com.xiaoneng.ss.common.utils.getFileIcon
import com.xiaoneng.ss.common.utils.toLongSafe
import com.xiaoneng.ss.module.school.model.FileInfoBean
import kotlinx.android.synthetic.main.activity_my_test.*


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class ImageMarqueeAdapter(layoutId: Int, listData: MutableList<FileInfoBean>?) :
    BaseQuickAdapter<FileInfoBean, BaseViewHolder>(layoutId, listData), LifecycleObserver {
    private var timer: CountDownTimer? = null
    var childNum = 0
    var mWidth = 0
    var mPWidth = 0
    var xScrolled = 0
    lateinit var ll: View
    var paused = false

    override fun convert(viewHolder: BaseViewHolder?, item: FileInfoBean?) {
        viewHolder?.let { holder ->
            var path = item?.url ?: ""
            ll = holder.getView(R.id.item_ll)

            if (holder.adapterPosition == 0) {
                holder.setBackgroundRes(R.id.item_image, R.drawable.ic_baoxiu)
            } else if (holder.adapterPosition == mData.size - 1) {
                holder.setBackgroundRes(R.id.item_image, R.drawable.ic_renwu)
            } else {
                holder.setBackgroundRes(R.id.item_image, R.drawable.ic_tonggao)
            }
        }
    }


    fun realScroll() {
        Log.e("realScroll", "realScroll")
        Handler().postDelayed({
            scrollToEnd()
        }, 2000)
    }

    fun scrollToEnd() {
        timer?.start()
    }


    private fun initTimer() {
        Log.e("initTimer===", "initTimer")
        if (timer==null) {
            mWidth = ll.width + ll.marginLeft + ll.marginRight
            childNum = recyclerView.childCount
            mPWidth = recyclerView.width - recyclerView.paddingLeft - recyclerView.paddingRight
            Log.e("scrollToEnd: childNum", childNum.toString())
            Log.e("scrollToEnd: mPWidth", mPWidth.toString())
            Log.e("scrollToEnd: mWidth", mWidth.toString())
            if (mData.size * mWidth < mPWidth) {
                return
            }
            var xTotalScroll = mWidth * mData.size - mPWidth + 50L
            Log.e("xTotalScroll", xTotalScroll.toString())
            var interval = 50L
            var step = 20
            var time = xTotalScroll / step * interval
            Log.e("time", time.toString())
            timer = object : CountDownTimer(time, interval) {
                override fun onFinish() {
                    Log.e("onFinish", timer.toString())
                    Handler().postDelayed({
                        xScrolled = 0
                        recyclerView.scrollToPosition(0)
                    }, 1000)
                    realScroll()
                }

                override fun onTick(p0: Long) {
                    Log.e("onTick", p0.toString())
                    recyclerView.scrollBy(step, 0)
                    xScrolled += 20
                }
            }
            realScroll()
        }
    }

    fun stopWork() {
        Log.e("onDetached===", recyclerView.toString())
        timer?.cancel()
        timer = null
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        Log.e("onAttached===", recyclerView.toString())
        recyclerView.post {
            initTimer()
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        stopWork()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.e("onResume===", "onResume")
        if (paused) {
            recyclerView.post {
                initTimer()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        /*
         确保Adapter#onDetachedFromRecyclerView被调用
         */
        Log.e("onPause===", "onPause")
        paused = true
        stopWork()
    }
}