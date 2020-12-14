package com.xiaoneng.ss.module.school.`interface`

import com.xiaoneng.ss.module.school.model.PropertyDetailBean

/**
 * @author Burning
 * @description:
 * @date :2020/12/14 11:47 AM
 */
interface IPropertyRecord {
    fun doFinish(bean:PropertyDetailBean) {

    }

    fun doReceive(bean:PropertyDetailBean) {

    }


    fun doRemind(bean:PropertyDetailBean) {

    }

    fun doCancel(bean:PropertyDetailBean) {

    }

    fun doShift(bean:PropertyDetailBean) {

    }

    fun doDelay(bean:PropertyDetailBean) {
    }
}