// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
}

buildscript {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        // classpath("com.github.ThomasConstantinBity:ICP-Kotlin-Kit:1.0.0-beta03")
    }
}