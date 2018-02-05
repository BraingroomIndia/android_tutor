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

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5

-keepattributes Signature
-keepattributes *Annotation*


-verbose
######### retrofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.

-dontwarn com.squareup.okhttp.**

-dontwarn okio.**

-keep class com.braingroom.tutor.model.** {*;}

-keep class com.google.firebase.**{*;}


-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#####

##-injars :Mobilisten.aar

#-keep class com.zoho.commons.ChatComponent.class
#-keep class com.zoho.*

#-keep public class com.zoho.**

#-keep public class com.zoho.** {
 # public protected *;
#}


-keep class com.facebook.applinks.** { *; }
-keepclassmembers class com.facebook.applinks.** { *; }
-keep class com.facebook.FacebookSdk { *; }
-keep class com.google.android.gms.ads.identifier.** { *; }
-printmapping mapping.txt

#-Realm
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**
-keepattributes InnerClasses
-keepnames public class * extends io.realm.RealmObject
-keep public class * extends io.realm.RealmObject { *; }