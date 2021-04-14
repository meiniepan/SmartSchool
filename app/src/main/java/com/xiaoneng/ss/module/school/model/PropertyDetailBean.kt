package com.xiaoneng.ss.module.school.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class PropertyDetailBean(
    var token: String?,
    var id: String?,
    var remark: String?,
    var repairerid: String?,
    var repairerinfo: UserBeanSimple?,
    var reportid: String?,
    var reportinfo: UserBeanSimple?,
    var fileinfo: ArrayList<FileInfoBean>?,
    var typename: String?,
    var typeid: String?,
    var deviceid: String?,
    var device: String?,
    var status: String?,//0撤销 1未接单 2接单 3完成
    var isdelay: String?,
    var delayreasons: String?,
    var delayer: String?,
    var repairlog: String?,
    var reporttime: String?,
    var handletime: String?,
    var completetime: String?,
    var companyid: String?,
    var schoolid: String?,
    var schoolname: String?,
    var muser_id: String?,
    var cuser_id: String?,
    var createtime: String?,
    var addr: String? = null,//详细地址
    var updatetime: String?
)