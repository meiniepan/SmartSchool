package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.model.ClassBean
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/06/24
 * Time: 17:06
 */
@Parcelize
data class QuantizeBody(
    var token: String?=UserInfo.getUserBean().token,//
    var stime: String?=null,//
    var etime: String?=null,//
    var shis: String?="00:00",//
    var ehis: String?="23:59",//
    var checktime: String?=null,//
    var classid: String?=null,//
    var typeid: String?=null,//
    var realname: String?=null,
    var uid: String?=null,
    var usertype: String?=null,
    var involve: String?=null,
    var actname: String?=null,//情况类型
    var rulename: String?=null,//影响项目
    var types: String?=null,//影响项目id
    var score: String?=null,//扣分
    var correctscore: String?=null,//综合加分
    var templatedata: String?=null,//
    var remark: String?=null,
    var status: String?=null,

    ) : Parcelable