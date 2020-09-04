package com.xiaoneng.ss.module.mine.view

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.state.UserInfo
import com.xiaoneng.ss.model.ParentBean
import com.xiaoneng.ss.model.StudentBean
import com.xiaoneng.ss.module.circular.viewmodel.CircularViewModel
import com.xiaoneng.ss.module.mine.adapter.ChooseChildAdapter
import kotlinx.android.synthetic.main.activity_choose_child.*
import org.jetbrains.anko.toast

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:01
 */
class ChooseChildActivity : BaseLifeCycleActivity<CircularViewModel>() {
    lateinit var mAdapter: ChooseChildAdapter
    var mData = ArrayList<StudentBean>()

    override fun getLayoutId(): Int = R.layout.activity_choose_child


    override fun initView() {
        super.initView()
        initAdapter()
        tvConfirm.setOnClickListener {

        }

    }

    private fun initAdapter() {
        mAdapter = ChooseChildAdapter(R.layout.item_choose_child, mData)
        rvChooseChild.apply {
            layoutManager = LinearLayoutManager(context)
            setAdapter(mAdapter)
        }
        mAdapter.setOnItemClickListener { _, view, position ->

        }
    }


    override fun onResume() {
        super.onResume()
        mData.clear()
        mData.addAll(UserInfo.getUserBean().students)
        if (mData.size > 0) {
            rvChooseChild.notifyDataSetChanged()
        } else {
            rvChooseChild.showEmptyView()
        }
    }


    override fun initDataObserver() {
        mViewModel.mNoticeData.observe(this, Observer { response ->
            response?.let {
                showSuccess()
                val gson: Gson = GsonBuilder().enableComplexMapKeySerialization().create()
                val jsonString = gson.toJson(it)
                val resultType = object : TypeToken<ArrayList<ParentBean>>() {}.type
                gson.fromJson<ArrayList<ParentBean>>(jsonString,resultType)?.let {
                    toast("绑定成功")
                    UserInfo.modifyParents(it)
                    finish()
                }
            }
        })

    }


}