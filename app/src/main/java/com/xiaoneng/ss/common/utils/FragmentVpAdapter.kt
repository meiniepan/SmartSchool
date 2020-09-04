package com.xiaoneng.ss.common.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class FragmentVpAdapter(
    fragmentManager: FragmentManager,
    val fragmentlist: ArrayList<Fragment>
) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return fragmentlist[position]
    }

    override fun getCount(): Int {
        return fragmentlist.size
    }


}