package com.xiaoneng.ss.common.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;

import java.lang.reflect.Field;

/**
 * @author Burning
 * @description:
 * @date :2020/8/27 10:06 AM
 */
public class JavaUtils {
    public static void setStatusBarTransparent(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(context.getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
            } catch (Exception e) {
            }
        }
    }
}
