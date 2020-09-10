package com.xiaoneng.ss.common.constclass

import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @author Burning
 * @description:
 * @date :2020/9/10 11:03 AM
 */
 class Solang() {
    @Target(AnnotationTarget.FIELD,AnnotationTarget.VALUE_PARAMETER)
    @StringDef(STUDENT, TEACHER)
    @Retention(RetentionPolicy.SOURCE)
    annotation class UserType


    companion object {
        const val STUDENT = "1"
        const val TEACHER = "2"
        const val ADMIN = "99"
    }
}