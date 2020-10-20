package com.xiaoneng.ss.receives

import android.content.Context
import android.util.Log
import com.tencent.android.tpush.*

/**
 * @author Burning
 * @description:
 * @date :2020/9/29 11:26 AM
 */
class CustomTPushReceiver : XGPushBaseReceiver() {


    override fun onTextMessage(p0: Context?, p1: XGPushTextMessage?) {

        Log.d("TPush", p1.toString())
    }

    override fun onDeleteAccountResult(p0: Context?, p1: Int, p2: String?) {

    }

    override fun onSetTagResult(p0: Context?, p1: Int, p2: String?) {

    }

    override fun onSetAttributeResult(p0: Context?, p1: Int, p2: String?) {

    }

    override fun onUnregisterResult(p0: Context?, p1: Int) {

    }

    override fun onDeleteTagResult(p0: Context?, p1: Int, p2: String?) {

    }

    override fun onRegisterResult(p0: Context?, p1: Int, p2: XGPushRegisterResult?) {

    }

    override fun onNotificationClickedResult(p0: Context?, p1: XGPushClickedResult?) {
        p1.toString()
        Log.d("TPush", "onTextMessage: ")
    }

    override fun onDeleteAttributeResult(p0: Context?, p1: Int, p2: String?) {

    }

    override fun onSetAccountResult(p0: Context?, p1: Int, p2: String?) {

    }

    override fun onNotificationShowedResult(p0: Context?, p1: XGPushShowedResult?) {

    }

}