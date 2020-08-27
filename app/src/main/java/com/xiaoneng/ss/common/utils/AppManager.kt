package com.xiaoneng.ss.common.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*
import kotlin.system.exitProcess

/**
 * Created with Android Studio.
 * Description: Activity管理类
 * @author: Burning
 * @date: 2020/02/25
 * Time: 19:05
 */

object AppManager {
    private val activityStack : Stack<Activity> = Stack()


    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityStack.remove(activity)
    }

     fun finishAllActivity() {
        for(activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        exitProcess(0)
    }
}