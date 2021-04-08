package com.xiaoneng.ss.module.activity

import com.xiaoneng.ss.R
import com.xiaoneng.ss.base.view.BaseActivity
import com.xiaoneng.ss.common.utils.Constant
import com.xiaoneng.ss.common.utils.displayImage
import com.yc.cn.ycgallerylib.zoom.listener.OnZoomClickListener
import com.yc.cn.ycgallerylib.zoom.listener.OnZoomLongClickListener
import kotlinx.android.synthetic.main.activity_image_scale.*


/**
 * @author Burning
 * @description:
 * @date :2021/4/8 3:02 PM
 */
class ImageScaleActivity : BaseActivity() {
var path:String? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_image_scale
    }

    override fun initView() {
        super.initView()
        path = intent.getStringExtra(Constant.DATA)
        imageView.setOnZoomClickListener(OnZoomClickListener { finish() })
        imageView.setOnZoomLongClickListener(OnZoomLongClickListener { false })
        imageView.setMaxScale(4)
        displayImage(this,path,imageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (imageView!=null){
            //重置所有状态，清空mask，停止所有手势，停止所有动画
            imageView.reset();
        }
    }
}