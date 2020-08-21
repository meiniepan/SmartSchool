package com.xiaoneng.ss.common.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.xiaoneng.ss.R

/**
 * Created with Android Studio.
 * Description:
 * @author: Burning
 * @date: 2020/02/26
 * Time: 11:12
 */
fun displayImage(context: Context, path: Any?, imageView: ImageView) {
    Glide.with(context)
        .load(path)
        .error(R.drawable.ic_eye)
        .placeholder(R.drawable.ic_eye)
        .into(imageView)
}