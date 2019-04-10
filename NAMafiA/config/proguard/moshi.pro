-dontwarn org.jetbrains.annotations.**
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.**
-keep class org.threeten.bp.**
-keep interface kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader
-keepattributes *Annotation*
-keepattributes Signature
-keepclassmembers class ** {
    @com.squareup.moshi.FromJson *;
    @com.squareup.moshi.ToJson *;
    @com.squareup.moshi.Json *;
}

-keep class com.roragok.namafia.api.entities.** { *; }
-keep public enum com.roragok.namafia.**
