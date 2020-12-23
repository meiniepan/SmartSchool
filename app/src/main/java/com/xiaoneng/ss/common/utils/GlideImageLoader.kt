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
fun displayImage(
    context: Context,
    path: Any?,
    imageView: ImageView,
    placeholder: Int = R.drawable.ic_img_placehold
) {
    Glide.with(context)
        .load(path)
        .placeholder(placeholder)
        .error(R.drawable.ic_img_error)
        .into(imageView)

}

