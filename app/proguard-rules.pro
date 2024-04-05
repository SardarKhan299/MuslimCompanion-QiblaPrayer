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

# Common Android ProGuard rules
-keep class androidx.** { *; }
-keep class com.google.android.material.** { *; }
-keep class androidx.recyclerview.** { *; }
-keep class androidx.navigation.** { *; }
-keep class org.jetbrains.kotlinx.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.fragment.** { *; }
-keep class androidx.activity.** { *; }
-keep class com.google.dagger.** { *; }
-keep class com.intuit.sdp.** { *; }
-keep class com.squareup.** { *; }
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class com.jakewharton.retrofit.** { *; }
-keep class com.google.code.gson.** { *; }
-keep class junit.** { *; }
-keep class androidx.test.** { *; }
-keep class org.jetbrains.kotlin.** { *; }
-keep class com.airbnb.android.lottie.** { *; }
-keep class androidx.biometric.** { *; }
-keep class androidx.security.** { *; }
-keep class com.google.maps.android.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class androidx.webkit.** { *; }
-keep class com.google.firebase.** { *; }
-keep class com.github.bumptech.glide.** { *; }
-keep class pl.droidsonroids.gif.** { *; }

# Specific ProGuard rules for individual libraries
-keep class androidx.room.** { *; }
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-dontwarn kotlin.reflect.jvm.internal.**
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform

# Glide specific rules
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Retrofit and OkHttp specific rules
-dontwarn retrofit2.Platform$Java8
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensionsKt
-dontwarn okio.**
-dontwarn javax.annotation.**

# Firebase specific rules
-keepattributes *Annotation*
-keepnames class com.google.firebase.** { *; }
-keepnames class com.google.android.gms.** { *; }

# Room database specific rules
-keepattributes Signature
-keepattributes *Annotation*
-keepclassmembers class * {
    @androidx.room.Dao *;
}
-keepclassmembers class * {
    @androidx.room.Database *;
}