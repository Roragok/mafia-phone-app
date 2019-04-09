# retrofit 2

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform

# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8

# Retain generic signatures used for parsing the return types reflectively.
-keepattributes Signature

-keep interface com.roragok.namafia.api.retrofit.NamafiaService { *; }
-keep public class com.roragok.namafia.api.entities.** { *; }
