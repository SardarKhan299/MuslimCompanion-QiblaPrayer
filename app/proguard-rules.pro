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
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

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

#-keep class com.dropbear.canya.** { *; }
-keep class com.onesignal.** { *; }
-keep class com.google.firebase.** { *; }
-keepclasseswithmembernames class * {
    native <methods>;
}
#-keep com.sinch.android.rtc.internal.natives.**

-dontwarn org.apache.http.annotation.**

#-keep class com.sinch.** { *; }
#-keep interface com.sinch.** { *; }
#-keep class org.webrtc.** { *; }

#-keep class com.google.android.gms.**{ *; }

-dontwarn com.google.android.gms.**
-dontwarn android.media.**


-dontwarn com.squareup.picasso.**
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-dontwarn java.nio.file.**
-dontwarn java.lang.**
-dontwarn retrofit2.**
-dontwarn it.moondroid.**

# Class names are needed in reflection
#-keepnames class com.amazonaws.**
# Request handlers defined in request.handlers
-keep class com.amazonaws.services.**.*Handler
# The following are referenced but aren't required to run
-dontwarn com.fasterxml.jackson.**
-dontwarn org.apache.commons.logging.**
# Android 6.0 release removes support for the Apache HTTP client
-dontwarn org.apache.http.**
# The SDK has several references of Apache HTTP client
-dontwarn com.amazonaws.http.**
-dontwarn com.amazonaws.metrics.**
#-dontwarn org.apache.lang.**
#-dontwarn com.crashlytics.android.answers.shim.**

##---------------Begin: proguard configuration for Gson ----------
# Gson uses generic type information stored in a class file when working with
#fields. Proguard removes such information by default, so configure it to keep
#all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
#-keep class com.google.gson.examples.android.model.** { *; }

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.* { <fields>; }

# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

-dontnote kotlin.**
-dontwarn kotlin.**
-keepclassmembernames class kotlinx.* {
    volatile <fields>;
}
-dontnote kotlinx.**
-keep class kotlinx.coroutines.*
# retrofit
-keepattributes Signature
-dontwarn retrofit2.**
-keep class retrofit2.* { *; }
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-dontwarn retrofit.RxSupport*
-dontwarn retrofit.appengine.UrlFetchClient
-dontnote retrofit.http.RestMethod
-keepclasseswithmembers class * {
    @retrofit2.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}
# okhttp
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.**
#gson
-keep class com.google.gson.stream.* { *; }
-keep class com.google.gson.* { *; }
-keep public class com.google.gson.* {
    public private protected *;
}
-keep class com.google.appengine.* { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class com.google.gson.* { *; }
-keep class com.google.inject.* { *; }
-keep class sun.misc.Unsafe.* { *; }
-keep class com.google.gson.stream.* { *; }
-keepclassmembers enum * { *; }
-keep,allowobfuscation @interface com.google.gson.annotations.*
-dontnote com.google.gson.annotations.Expose
-keepclassmembers class * {
    @com.google.gson.annotations.Expose <fields>;
}
-keepclasseswithmembers,allowobfuscation,includedescriptorclasses class * {
    @com.google.gson.annotations.Expose <fields>;
}
-dontnote com.google.gson.annotations.SerializedName
-keepclasseswithmembers,allowobfuscation,includedescriptorclasses class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepclassmembers enum * {
    @com.google.gson.annotations.SerializedName <fields>;
}
#OKHTTP3
-dontwarn okhttp3.**

# Keep all data classes not obfuscated...
-keep class com.qibla.qiblacompass.prayertime.finddirection.data.remote.dto.* { *; }

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation