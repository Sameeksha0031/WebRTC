// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {
    dependencies{
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")
        classpath("com.android.tools.build:gradle:7.0.4")
    }
}