package com.xiaoneng.ss.common.permission

/**
 * Created with Android Studio.
 * Description: 密封类实现枚举类型
 * @author: Burning
 * @CreateDate: 2020/4/27 18:54
 */
sealed class PermissionResult {
    object Grant : PermissionResult()
    class Deny(val permissions: Array<String>) : PermissionResult()
    class Rationale(val permissions: Array<String>) : PermissionResult()
}