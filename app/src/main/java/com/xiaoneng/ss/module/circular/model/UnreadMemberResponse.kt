package com.xiaoneng.ss.module.circular.model

import android.os.Parcelable
import com.xiaoneng.ss.module.school.model.UnreadMemberBean
import com.xiaoneng.ss.module.school.model.UserBeanSimple
import kotlinx.android.parcel.Parcelize

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2021/04/28
 * Time: 17:06
 */
@Parcelize
data class UnreadMemberResponse(
    var data: ArrayList<UnreadMemberBean>? = null,
    var id: String? = null,
    var publishUserId: String? = null,
    var title: String ,
    var code: String//1:任务
): Parcelable