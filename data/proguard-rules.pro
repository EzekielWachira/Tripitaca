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
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes AnnotationDefault,RuntimeVisibleAnnotations
# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn  com.ezzy.data.domain.model.Geolocation
-dontwarn com.ezzy.data.domain.model.Listings
-dontwarn com.ezzy.data.domain.model.Property
-dontwarn com.ezzy.data.domain.model.Filter

#-dontwarn java.lang.invoke.StringConcatFactory

-keep class com.ezzy.data.domain.model.Geolocation { *; }
-keep class com.ezzy.data.domain.model.Listings { *; }
-keep class com.ezzy.data.domain.model.Property { *; }
-keep class com.ezzy.data.domain.model.Filter { *; }

# Gson uses generic type information stored in a class file when working with
# fields. Proguard removes such information by default, keep it.

-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
