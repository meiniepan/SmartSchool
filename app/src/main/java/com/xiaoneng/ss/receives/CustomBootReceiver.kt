package com.xiaoneng.ss.receives

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.xiaoneng.ss.services.CustomJgService


/**
 * @author Burning
 * @description:
 * @date :2020/9/29 11:26 AM
 */
class CustomBootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            val i = Intent(context, CustomJgService::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(i)
        }
    }

}