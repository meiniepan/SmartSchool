package com.xiaoneng.ss.common.utils

import java.util.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/24
 * Time: 17:36
 */
object Constant {
    const val BASE_URL = " http://api.huiwencloud.com:81"
    const val TO_DO = "20200814"
    const val TYPE_STUDENT = "1"
    const val TYPE_TEACHER = "2"
    const val TYPE_ADMIN = "99"

    //    const val BASE_URL = "https://www.sojson.com"
    const val LOGIN_TYPE_STU = "self"
    const val LOGIN_TYPE_PAR = "parents"
    const val DATA = "data"
    const val TIME = "time"
    const val FLAG = "flag"
    const val BIND_PARENT_SMS = 4
    const val USERNAME_KEY = "username"
    const val TASK_STATUS = "task_status"//0草稿箱，1进行中，2已关闭，3未完成，4已完成
    const val LOGIN_KEY = "login"
    const val IS_MASTER = "is_master"
    const val USER_INFO = "user_info"
    const val TITLE = "title"
    const val TYPE = "type"
    const val TITLE_2 = "title_2"
    const val LEAVE_TYPE = "leave_type"
    const val ID = "id"

    val THIS_YEAR = Calendar.getInstance().get(Calendar.YEAR)

    const val SAVE_USER_LOGIN_KEY = "user/login"
    const val SAVE_USER_REGISTER_KEY = "user/register"
    const val SET_COOKIE_KEY = "set-cookie"
    const val COOKIE_NAME = "Cookie"

    const val NIGHT_MODE = "night_mode"

    const val HOME = 0
    const val SCHOOL = 1
    const val MINE = 2

    const val SUCCESS:String = "0000"
    const val NOT_LOGIN = "0004"



    // 二维码扫描
    const val REQUEST_CODE_SCAN = 1
    const val REQUEST_CODE_COURSE = 2
    const val REQUEST_CODE_PRINCIPAL = 3

    const val CODED_CONTENT = "codedContent"
    const val INTENT_ZXING_CONFIG = "zxingConfig"



}