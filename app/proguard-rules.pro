# COMMON
-verbose
-optimizations !method/propagation/*
-optimizations !method/marking/*
-optimizations !method/specialization/*
-optimizations !method/removal/parameter
-optimizations !class/merging/*
-optimizations !code/simplification/arithmetic

-keepattributes SourceFile,LineNumberTable,InnerClasses
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
        public static int d(...);
        public static int w(...);
        public static int v(...);
        public static int i(...);
        public static int e(...);
        public static java.lang.String getStackTraceString(java.lang.Throwable);
}

-assumenosideeffects class java.lang.Exception {
    public void printStackTrace();
}

# GSON
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keepclassmembers enum * { *; }

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

-keep,allowobfuscation @interface com.google.gson.annotations.*

-keep class com.google.gson.stream.** { *; }

# OKHTTP
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform
-dontwarn org.conscrypt.ConscryptHostnameVerifier

# OKIO
-dontwarn okio.**

# REFLECTION USAGE
-keep class com.andro.indieschool.cocktailapp.feature.details.data.remote.response.DrinkDetail { *; }