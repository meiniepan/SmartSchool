//package com.xiaoneng.ss.custom.widgets
//
//import android.content.Context
//import android.os.Build
//import android.util.AttributeSet
//import android.util.Log
//import android.widget.FrameLayout
//import androidx.annotation.RequiresApi
//import com.google.android.material.bottomnavigation.BottomNavigationView
//import skin.support.app.SkinCompatDelegate
//import skin.support.widget.SkinCompatSupportable
//import skin.support.widget.SkinCompatBackgroundHelper
//
//import skin.support.widget.SkinCompatTextHelper
//
//
//
//
//class CustomNavi @JvmOverloads constructor(
//    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
//) : BottomNavigationView(context, attrs, defStyleAttr),SkinCompatSupportable {
//
//    private var mTextHelper: SkinCompatTextHelper? = null
//    private var mBackgroundTintHelper: SkinCompatBackgroundHelper? = null
//
//    init{
//        mBackgroundTintHelper =  SkinCompatBackgroundHelper(this);
//        mBackgroundTintHelper?.loadFromAttributes(attrs, defStyleAttr);
////        mTextHelper = SkinCompatTextHelper.create(this);
////        mTextHelper?.loadFromAttributes(attrs, defStyleAttr);
//
//    }
//
//
//    override fun setBackgroundResource(resid: Int) {
//        super.setBackgroundResource(resid)
//        Log.e("cus====", "setBackgroundResource: ", )
//        if (mBackgroundTintHelper != null) {
//            mBackgroundTintHelper?.onSetBackgroundResource(resid);
//        }
//
//    }
//    override fun applySkin() {
//        Log.e("cus====", "applySkin: ", )
//        if (mBackgroundTintHelper != null) {
//            mBackgroundTintHelper?.applySkin();
//        }
////        if (mTextHelper != null) {
////            mTextHelper?.applySkin();
////        }
//
//    }
//
//}