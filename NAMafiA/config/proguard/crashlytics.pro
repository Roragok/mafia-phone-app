# crashlytics 2

-keep class io.fabric.**
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keepattributes SourceFile,LineNumberTable,*Annotation*

-keep public class * extends java.lang.Exception

-printmapping mapping.txt