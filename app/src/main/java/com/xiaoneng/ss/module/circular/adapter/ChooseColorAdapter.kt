package com.xiaoneng.ss.module.circular.adapter

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.circular.model.ColorBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class ChooseColorAdapter(layoutId: Int, listData: MutableList<ColorBean>) :
    BaseQuickAdapter<ColorBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder, item: ColorBean) {
        viewHolder.let { holder ->
            var shape = ShapeDrawable(OvalShape())
            shape.paint.color = Color.parseColor(item.color)
            var view = holder.getView<View>(R.id.ivColor)
            view.background = shape
            if (item.isCheck) {
                holder.setGone(R.id.ivCheckColor,true)
            } else {
                holder.setGone(R.id.ivCheckColor,false)
            }
        }
    }
}