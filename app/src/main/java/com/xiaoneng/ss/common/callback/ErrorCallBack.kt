package com.xiaoneng.ss.common.callback

import com.kingja.loadsir.callback.Callback
import com.xiaoneng.ss.R

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/22
 * Time: 14:29
 */
class ErrorCallBack : Callback() {
    override fun onCreateView(): Int = R.layout.layout_error
}