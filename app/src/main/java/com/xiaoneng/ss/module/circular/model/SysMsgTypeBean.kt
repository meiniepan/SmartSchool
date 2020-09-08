package com.xiaoneng.ss.module.circular.model

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:06
 */
data class SysMsgTypeBean(
    //通知类型 0：任务 1：考勤 2：课表 3：成绩 4：版本更新 动作为跳转相应详情页
    var type: String? = "",
    //通知标题
    var title: String? = "",
    //任务ID等信息
    var info: String? = ""

    )
