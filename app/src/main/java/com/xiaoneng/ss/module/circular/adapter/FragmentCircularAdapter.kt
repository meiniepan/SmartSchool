package com.xiaoneng.ss.module.sys.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xiaoneng.ss.module.sys.view.CircularFragment


/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/27
 * Time: 17:32
 */
class FragmentCircularAdapter(
    fragmentManager: CircularFragment,
    fragmentlist: ArrayList<Fragment>
) :
    FragmentStateAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}