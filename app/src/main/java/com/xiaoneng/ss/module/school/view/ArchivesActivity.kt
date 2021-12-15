package com.xiaoneng.ss.module.school.view

import android.util.Log
import androidx.lifecycle.Observer
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.common.utils.displayImage
import com.xiaoneng.ss.common.utils.getSexString
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.model.ArchivesBean
import com.xiaoneng.ss.module.school.model.TimetableBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_archives.*

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
        mViewModel.getArchives()
    }

    override fun initDataObserver() {
        mViewModel.mArchivesData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<ArchivesBean>(it)?.let {
                    if (!UserInfo.getUserBean().portrait.isNullOrEmpty()) {
                        displayImage(
                            this,
                            UserInfo.getUserBean().domain + UserInfo.getUserBean().portrait,
                            ivAvatar2
                        )
                    }
                    if(!it.work_year.isNullOrEmpty()){
                        it.work_year = it.work_year+"年"
                    }
                    if(!it.teach_year.isNullOrEmpty()){
                        it.teach_year = it.teach_year+"年"
                    }
                    tvName2.text = UserInfo.getUserBean().realname

                    tvItem1.text = it.birthday
                    tvItemSex.text = getSexString(it.sex)
                    tvItemHighEdu.text = it.highest_education_str
                    tvItemPolitical.text = it.political_str
                    tvItemTeachSubject.text = it.teach_subject_str
                    tvItemRelation.text = it.relation_school_str
                    tvItemWorkYear.text = it.work_year
                    tvItemTeachYear.text = it.teach_year
                    tvItemJoinTime.text = it.join_time

                }
            }
        })
    }
}
