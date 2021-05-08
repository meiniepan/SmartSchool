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
    const val BASE_URL = "http://api.huiwencloud.com:81"
    const val BASE_URL_DEV = "http://t1.huiwencloud.com:81"
    const val TO_DO = "20200814"
    const val TYPE_STUDENT = "1"
    const val TYPE_TEACHER = "2"
    const val TYPE_ADMIN = "99"

    const val LOGIN_TYPE_STU = "self"
    const val LOGIN_TYPE_PAR = "parents"
    const val DATA = "data"
    const val DATA2 = "data2"
    const val DATA3 = "data3"
    const val TIME = "time"
    const val FLAG = "flag"
    const val BIND_PARENT_SMS = 4
    const val INIT_X5 = "init_x5"
    const val TASK_STATUS = "task_status"//0草稿箱，1进行中，2已关闭，3未完成，4已完成
    const val LOGIN_KEY = "login"
    const val DEVICE_TOKEN = "device_token"
    const val DEV_URL = "dev_url"
    const val AGREE_PROTOCOL = "agree_protocol"
    const val IS_MASTER = "is_master"
    const val USER_INFO = "user_info"
    const val APP_INFO = "app_info"
    const val FILE_TRANS_INFO = "file_trans_info"
    const val TITLE = "title"
    const val TYPE = "type"
    const val TITLE_2 = "title_2"
    const val LEAVE_TYPE = "leave_type"
    const val ID = "id"
    const val ID2 = "id2"


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
    const val TEMP_OUT_TIME = "0100"//临时参数失败



    // 二维码扫描
    const val REQUEST_CODE_SCAN = 1
    const val REQUEST_CODE_COURSE = 2
    const val REQUEST_CODE_PRINCIPAL = 3
    const val REQUEST_CODE_FILE = 4

    const val CODED_CONTENT = "codedContent"
    const val INTENT_ZXING_CONFIG = "zxingConfig"



}