package com.xiaoneng.ss.module.school.view

import android.app.Dialog
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.AppInfo
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.*
import com.xiaoneng.ss.model.ClassBean
import com.xiaoneng.ss.module.school.adapter.DialogListAdapter
import com.xiaoneng.ss.module.school.adapter.TimetableAdapter
import com.xiaoneng.ss.module.school.adapter.TimetableLabelAdapter
import com.xiaoneng.ss.module.school.adapter.TitleTimetableAdapter
import com.xiaoneng.ss.module.school.model.TimetableBean
import com.xiaoneng.ss.module.school.model.TimetableLabelBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_timetable_master.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class ArchivesActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    var mData = ArrayList<TimetableBean>()

    override fun getLayoutId(): Int = R.layout.activity_archives


    override fun initView() {
        super.initView()

    }

    override fun initData() {
        super.initData()
    }

    override fun initDataObserver() {
        mViewModel.mTimetableData.observe(this, Observer { response ->
            response?.let {

            }
        })
    }
}
