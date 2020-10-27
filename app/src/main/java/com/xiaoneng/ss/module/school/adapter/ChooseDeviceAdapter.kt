package com.xiaoneng.ss.module.school.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.school.model.DeviceBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class ChooseDeviceAdapter(layoutId: Int, listData: MutableList<DeviceBean>) :
    BaseQuickAdapter<DeviceBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder, item: DeviceBean) {
        viewHolder.let { holder ->
            holder.setText(R.id.tvDevice, item.name)
            var textView = holder.getView<TextView>(R.id.tvDevice)
            if (item.isCheck) {
                textView.setBackgroundResource(R.drawable.bac_blue_bac_5)
                textView.setTextColor(mContext.resources.getColor(R.color.white))
            } else {
                textView.setBackgroundResource(R.drawable.bac_blue_line_5)
                textView.setTextColor(mContext.resources.getColor(R.color.themeColor))
            }
        }
    }
}