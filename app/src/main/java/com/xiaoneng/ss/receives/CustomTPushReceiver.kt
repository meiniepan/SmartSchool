package com.xiaoneng.ss.receives

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


/**
 * @author Burning
 * @description:
 * @date :2020/9/29 11:26 AM
 */
class CustomTPushReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.w("=====", "Tpush")
    }
}