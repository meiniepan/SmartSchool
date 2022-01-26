package com.xiaoneng.ss.module.school.view

import android.Manifest
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity
import com.xiaoneng.ss.common.permission.PermissionResult
import com.xiaoneng.ss.common.permission.Permissions
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.FragmentVpAdapter
import com.xiaoneng.ss.common.utils.mStartActivity
import com.xiaoneng.ss.common.utils.netResponseFormat
import com.xiaoneng.ss.module.school.adapter.PropertyTypeAdapter
import com.xiaoneng.ss.module.school.model.PropertyTypeBean
import com.xiaoneng.ss.module.school.viewmodel.SchoolViewModel
import com.xiaoneng.ss.network.response.BaseResp
import kotlinx.android.synthetic.main.activity_property.*
import pub.devrel.easypermissions.AppSettingsDialog

/**
 * @author Burning
 * @description:报修报送
 * @date :2020/10/23 3:17 PM
 */
class PropertyActivity : BaseLifeCycleActivity<SchoolViewModel>() {
    lateinit var mAdapter: PropertyTypeAdapter
    var mData: ArrayList<PropertyTypeBean> = ArrayList()
    private lateinit var fragmentAdapter: FragmentVpAdapter
    private var fragmentList = ArrayList<Fragment>()
    var rotationB = false
    private val mPermissions = arrayOf(
        Manifest.permission.RECORD_AUDIO
    )

    override fun getLayoutId(): Int {
        return R.layout.activity_property
    }

    override fun initView() {
        super.initView()
        initPermission(mPermissionsAudio)
        initViewPager()
        initTab()
        initUI()

        tvRepair.setOnClickListener {
            checkFirsTab()
        }
        tvReport.setOnClickListener {
            checkSecondTab()
        }
        ivAddFile.setOnClickListener {
            showAdd()
        }
        llMain.setOnClickListener {
            showAdd()
        }
        initAdapter()
        tvType0.setOnClickListener {
            if (mData.size > 0) {
                initVoicePermission(0)
                showAdd()
            }
        }
        tvType1.setOnClickListener {
            if (mData.size > 1) {
                initVoicePermission(1)
                showAdd()
            }
        }
    }

    private fun initUI() {
        //判断是否有维修权限

    }

    private fun initTab() {
        tvRepair.setOnClickListener {
            checkFirsTab()
        }
        tvReport.setOnClickListener {
            checkSecondTab()
        }
    }

    private fun showAdd(it: View = ivAddFile) {
        val anim: ObjectAnimator
        if (rotationB) {
            anim = ObjectAnimator.ofFloat(it, "rotation", 45.0F, 0F)
            anim.doOnEnd {
                tvType0.visibility = View.GONE
                tvType1.visibility = View.GONE
                llMain.visibility = View.GONE
            }
        } else {
            anim = ObjectAnimator.ofFloat(it, "rotation", 0.0F, 45.0F)
            anim.doOnEnd {
                tvType0.visibility = View.VISIBLE
                tvType1.visibility = View.VISIBLE
                llMain.visibility = View.VISIBLE
            }
        }
        rotationB = !rotationB
        anim.duration = 300
        anim.start()
    }


    private fun checkFirsTab() {
        tvRepair.setBackgroundResource(R.drawable.bac_blue_bac)
        tvRepair.setTextColor(resources.getColor(R.color.white))
        tvReport.setBackgroundResource(R.drawable.bac_blue_line_21)
        tvReport.setTextColor(resources.getColor(R.color.themeColor))
        vpProperty.currentItem = 0
    }

    private fun checkSecondTab() {
        tvReport.setBackgroundResource(R.drawable.bac_blue_bac)
        tvReport.setTextColor(resources.getColor(R.color.white))
        tvRepair.setBackgroundResource(R.drawable.bac_blue_line_21)
        tvRepair.setTextColor(resources.getColor(R.color.themeColor))
        vpProperty.currentItem = 1
    }

    private fun initViewPager() {
        var fragment1 = PropertyRecordFragment.getInstance()
        fragment1.arguments = Bundle().apply {
            putString(Constant.TYPE, "1")
            putParcelableArrayList(Constant.DATA, mData)
        }
        var fragment2 = PropertyRecordFragment.getInstance()
        fragment2.arguments = Bundle().apply {
            putString(Constant.TYPE, "0")
            putParcelableArrayList(Constant.DATA, mData)
        }
        fragmentList.add(fragment1)
        fragmentList.add(fragment2)
        fragmentAdapter = FragmentVpAdapter(
            supportFragmentManager,
            fragmentList
        )
        vpProperty.adapter = fragmentAdapter
        vpProperty.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    checkFirsTab()
                } else if (position == 1) {
                    checkSecondTab()
                }
            }
        })
    }

    override fun initData() {
        super.initData()
        getData()
    }

    override fun getData() {
        super.getData()
        showLoading()
        mViewModel.getPropertyType()
    }

    private fun initAdapter() {

    }

    private fun initVoicePermission(position: Int) {
        Permissions(this).request(*mPermissions).observe(
            this, Observer {
                when (it) {
                    is PermissionResult.Grant -> {
                        mStartActivity<AddPropertyActivity>(this) {
                            putExtra(Constant.DATA, mData[position])
                        }
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Rationale -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有录音权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                    }
                    // 进入设置界面申请权限
                    is PermissionResult.Deny -> {
                        AppSettingsDialog.Builder(this)
                            .setTitle("申请权限")
                            .setRationale("没有录音权限应用将无法正常运行，点击确定进入权限设置界面来进行更改")
                            .build()
                            .show()
                    }
                }
            }
        )

    }

    override fun initDataObserver() {
        mViewModel.mRepairTypeData.observe(this, Observer {
            it?.let {
                netResponseFormat<BaseResp<PropertyTypeBean>>(it)?.let { bean ->
                    bean.data?.let {
                        mData.addAll(it)
                        if (mData.size > 1) {
                            tvType0.text = mData[0].name
                            tvType1.text = mData[1].name
                        } else if (mData.size > 0) {
                            tvType0.text = mData[0].name
                        }
                    }
                }
            }
        })
    }
}