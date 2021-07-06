package com.xiaoneng.ss.module.school.model

import android.os.Parcelable
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
data class QuantizeTemplateBean(
    var fieldId: String?=null,
    var name: String?=null,
    //'Input', // 控件类型,单行文本框
    //'Textarea', // 多行输入框
    //'InputNumber', // 数字输入框
    //'Radio', // 单选框
    //'Checkbox', // 多选框
    //'DatePicker', // 日期（单日）
    //'DatePickerMultiple', // 日期范围（值是数组格式）
    //'DateTimePicker', // 日期时间（单日）
    //'CascaderClass', // 年级班级
    //'ChoseStudents', // 选择学生（是用组织架构那种组件）
    //
    var label: String?=null,
    var multiple: Boolean?=null,
    var placeholder: String?=null,
    var rules: QuantizeRuleBean?=null,
    var value: String?=null,
    var stime: String?=null,
    var etime: String?=null,
    var selections: ArrayList<String>?=null,
    var classes: ArrayList<ClassBean>?=null,
) : Parcelable