plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.devtoolsKsp) apply false
    alias(libs.plugins.hiltAndroid) apply false
}
buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}
true