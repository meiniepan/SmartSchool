package com.xiaoneng.ss.module.school.view

import android.app.Activity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.model.StudentBean
import com.xiaoneng.ss.module.school.adapter.InvolvePersonAdapter
import com.xiaoneng.ss.module.school.model.StudentsResponse
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import kotlinx.android.synthetic.main.activity_involve_person.*

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/08/27
 * Time: 17:01
 */
class InvolvePersonActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    private lateinit var mAdapterInvolve: InvolvePersonAdapter
    var mDataInvolve = ArrayList<StudentBean>()
    lateinit var chosenDay: String
    var id = ""


    override fun getLayoutId(): Int = R.layout.activity_involve_person


    override fun initView() {
        super.initView()
        id = intent.getStringExtra(Constant.TITLE)
        initAdapterInvolve()
        tvConfirm.setOnClickListener {
            var data = ArrayList<StudentBean>()
            mDataInvolve.forEach {
                if (it.choice == "1"){
                    data.add(it)
                }
            }
            setResult(Activity.RESULT_OK, intent.putExtra(Constant.DATA, data))
            finish()
        }

    }


    override fun initData() {
        super.initData()
        mViewModel.getStudentsByClass(id)

    }


    private fun initAdapterInvolve() {
        mAdapterInvolve = InvolvePersonAdapter(R.layout.item_involve, mDataInvolve)
        rvInvolvePerson.apply {
            layoutManager = GridLayoutManager(context, 5)
            setAdapter(mAdapterInvolve)
        }
        mAdapterInvolve.setOnItemClickListener { _, view, position ->
            if (mDataInvolve[position].choice == "0") {
                mDataInvolve[position].choice = "1"
            } else {
                mDataInvolve[position].choice = "0"
            }
            mAdapterInvolve.notifyDataSetChanged()
        }
    }


    override fun initDataObserver() {


        mViewModel.mBaseData.observe(this, Observer { response ->
            response?.let {
                netResponseFormat<StudentsResponse>(it)?.let {
                    mDataInvolve.clear()
                    it.data.forEach {
                        it.parentId = id
                    }
                    mDataInvolve.addAll(it.data)
                    rvInvolvePerson.notifyDataSetChanged()
                }
            }
        })

    }

}