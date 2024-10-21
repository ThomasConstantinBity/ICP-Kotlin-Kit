-keep class com.bity.icp_kotlin_kit.data.** { *; }
-keepclassmembers class com.bity.icp_kotlin_kit.data**$* { *; }
-keepclassmembernames class com.bity.icp_kotlin_kit.data**$* { *; }

# Retrofit
-keep class retrofit2.** { *; }

# Keep Kotlin metadata (usedb by reflection)
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes KotlinMetadata

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Prevent any code shrinking or optimizations
-keepnames class com.bity.icp_kotlin_kit.candid.**
-keepnames class com.bity.icp_kotlin_kit.domain.generated_file.**
-dontoptimize
# Keep all fields in the candid package
-keepclassmembers class com.bity.icp_kotlin_kit.domain.generated_file.** {
    <fields>;
}
# Keep public methods in the candid package.
-keepclassmembers class com.bity.icp_kotlin_kit.candid.** {
    public *;
}

# Keep all classes in com.bity.icp_kotlin_kit.domain.generated_file because they are used via reflection.
-keep class com.bity.icp_kotlin_kit.domain.generated_file.** {
    public <init>(...);  # Keep constructors.
    public *;            # Keep all public methods and fields.
}

# Ensure constructor parameters are not obfuscated, keeping them in their original form
# (especially important for Kotlin default parameters).
-keepclassmembers class com.bity.icp_kotlin_kit.domain.generated_file.** {
    public <init>(...);  # Keep constructors, especially with multiple parameters.
    public *;            # Keep public methods/fields to avoid any obfuscation issues.
}


# Keep synthetic classes and members (especially for Kotlin classes with default parameters).
-keepclasseswithmembers class kotlin.** {
    synthetic <methods>;
}

# Preserve default argument constructors generated by Kotlin.
-keepclassmembers class ** {
    @kotlin.jvm.JvmOverloads <init>(...);
}

# Prevent obfuscation of Kotlin synthetic default methods.
-keep class kotlin.jvm.internal.** { *; }