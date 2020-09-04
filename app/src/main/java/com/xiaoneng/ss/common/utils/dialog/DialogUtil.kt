package com.xiaoneng.ss.common.utils.dialog

import android.content.Context
import android.content.DialogInterface

/**
 * Created by Burning on 2017/1/6.
 */
object DialogUtil {
    /*下面两个button 的Dialog*/
    fun showDialogTwoButton(
        context: Context?,
        title: String?,
        message: String?,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener? = null,
        positiveMessage: String? = null,
        negativeMessage: String? = null
    ) {
        val builder =
            CustomDialog.Builder(context!!, 2)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setNegativeButton(negativeMessage, negativeListener)
        builder.setPositiveButton(positiveMessage, positiveListener)
        builder.create().show()
    }

    /*下面一个button 的Dialog*/
    fun showDialogOneButton(
        context: Context?,
        title: String?,
        message: String?,
        negativeListener: DialogInterface.OnClickListener? = null,
        negativeMessage: String? = null
    ) {
        val builder =
            CustomDialog.Builder(context!!, 2)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setNegativeButton(negativeMessage, negativeListener)
        builder.create().show()
    }


}