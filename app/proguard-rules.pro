# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#kotlin
-keep public class com.xiaoneng.ss.**{*;}

-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep class **.R$* {*;}
-keepclassmembers enum * { *;}

#-----------------不需要混淆系统组件等-------------------------------------------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclasseswithmembers class * {      # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {      # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}


-keepattributes Signature        #不混淆泛型
-keepclassmembers class **.R$* { #不混淆资源类
    public static <fields>;
}
-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}

#-keep class com..**{*;}  #过滤掉自己编写的实体类
-keep class com.xiaoneng.ss.model.**{*;}  #过滤掉自己编写的实体类
-keep class com.xiaoneng.ss.module.circular.model.**{*;}  #过滤掉自己编写的实体类
-keep class com.xiaoneng.ss.account.model.**{*;}  #过滤掉自己编写的实体类
-keep class com.xiaoneng.ss.module.school.model.**{*;}  #过滤掉自己编写的实体类
-keep class com.xiaoneng.ss.module.mine.model.**{*;}  #过滤掉自己编写的实体类

#oss
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**

-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

#PictureSelector 2.0
-keep class com.luck.picture.lib.** { *; }

#Ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#Okio
-dontwarn org.codehaus.mojo.animal_sniffer.*

#eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#loadSir
-dontwarn com.kingja.loadsir.**
-keep class com.kingja.loadsir.** {*;}

#-----------------gson----------------------------
-keep class com.google.**{*;}
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }

#-----------------okhttp3----------------------------
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

#-----------------okhttp----------------------------
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *;}
-dontwarn okio.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#androidx
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}



-keep public class com.xiaoneng.ss.base.viewmodel.BaseViewModel{*;}
-keep public class com.xiaoneng.ss.common.utils.CommonUtil
-keep public class * extends com.xiaoneng.ss.base.repository.BaseRepository {*;}
-keep public class com.xiaoneng.ss.base.repository.BaseRepository
-keep public class * extends com.xiaoneng.ss.base.repository.ApiRepository{*;}
-keep public class com.xiaoneng.ss.network.ExtKt{*;}
-keep public class * extends com.xiaoneng.ss.base.view.BaseFragment{*;}
-keep public class * extends com.xiaoneng.ss.base.view.BaseActivity{*;}


#推送
-dontwarn com.umeng.**
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn com.meizu.**
-keepattributes *Annotation*
-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class com.meizu.** {*;}
-keep class org.apache.thrift.** {*;}
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}
-keep public class **.R$*{
   public static final int *;
}

#华为通道
 -ignorewarnings
 -keepattributes *Annotation*
 -keepattributes Exceptions
 -keepattributes InnerClasses
 -keepattributes Signature
 -keepattributes SourceFile,LineNumberTable
 -keep class com.hianalytics.android.**{*;}
 -keep class com.huawei.updatesdk.**{*;}
 -keep class com.huawei.hms.**{*;}

#小米通道
-keep class org.android.agoo.xiaomi.MiPushBroadcastReceiver {*;}
#可以防止一个误报的 warning 导致无法成功编译，如果编译使用的 Android 版本是 23。
-dontwarn com.xiaomi.push.**






