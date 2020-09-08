package com.xiaoneng.ss.module.mine.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiaoneng.ss.R
import com.xiaoneng.ss.module.mine.model.InviteCodeBean
import org.jetbrains.anko.toast


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:32
 */
class InviteCodeAdapter(layoutId: Int, listData: MutableList<InviteCodeBean>?) :
    BaseQuickAdapter<InviteCodeBean, BaseViewHolder>(layoutId, listData) {

    override fun convert(viewHolder: BaseViewHolder?, item: InviteCodeBean) {
        viewHolder?.let { holder ->

            holder.setText(R.id.tvNameInviteCode, item?.classname)
                .setText(R.id.tvCodeInviteCode, item?.code)

            holder.getView<TextView>(R.id.tvCopyInviteCode).apply {
                setOnClickListener {
                    val cm: ClipboardManager =
                        mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val mClipData = ClipData.newPlainText("Label", item.code)
                    cm.setPrimaryClip(mClipData)
                    mContext.toast("复制成功")
                }
            }

        }
    }
}