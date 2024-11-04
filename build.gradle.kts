buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath (libs.google.services.v4310)
    }
}
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}
