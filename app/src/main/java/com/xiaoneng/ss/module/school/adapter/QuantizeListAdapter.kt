package com.xiaoneng.ss.module.school.adapter

import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.R
import com.xiaoneng.ss.common.utils.dealTemplateShow
import com.xiaoneng.ss.module.school.model.QuantizeBody
import com.xiaoneng.ss.module.school.model.QuantizeTemplateBean
import com.xiaoneng.ss.module.school.model.QuantizeTypeBean


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/07/06
 * Time: 17:32
 */
class QuantizeListAdapter(layoutId: Int, listData: MutableList<QuantizeBody>) :
    BaseQuickAdapter<QuantizeBody, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder, item: QuantizeBody) {
        viewHolder?.let { holder ->
            var llRoot = holder.getView<LinearLayout>(R.id.llRoot)
            item.templatedata?.let {
                var views = ArrayList<QuantizeTemplateBean>()
                val resultType = object : TypeToken<ArrayList<QuantizeTemplateBean>>() {}.type
                val gson = Gson()
                try {
                    views = gson.fromJson<ArrayList<QuantizeTemplateBean>>(it, resultType)
                    dealTemplateShow(mContext, llRoot, views)
                } catch (e: Exception) {

                }

            }
        }
    }

}