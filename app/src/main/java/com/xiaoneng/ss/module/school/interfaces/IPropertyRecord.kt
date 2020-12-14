package com.xiaoneng.ss.module.school.interfaces

import com.xiaoneng.ss.module.school.model.PropertyDetailBean
import com.xiaoneng.ss.module.school.model.RepairBody

/**
 * @author Burning
 * @description:
 * @date :2020/12/14 11:47 AM
 */
interface IPropertyRecord {
    fun doFinish(bean:RepairBody) {

    }

    fun doReceive(bean:RepairBody) {

    }


    fun doRemind(bean:PropertyDetailBean) {

    }

    fun doCancel(bean:RepairBody) {

    }

    fun doShift(bean:RepairBody) {

    }

    fun doDelay(bean:RepairBody) {
    }
}