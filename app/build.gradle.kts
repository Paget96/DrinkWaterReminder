plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.devtoolsKsp)
    id("androidx.navigation.safeargs.kotlin")
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.paget96.drinkwaterreminder"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.paget96.drinkwaterreminder"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "v1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {

    // Support lib dependencies
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.browser)

    // Lifecycle library
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    ksp(libs.androidx.lifecycle.compiler)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.lifecycle.process)

    // Navigation library
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.dynamic.features.fragment)

    // Room persistence library
    implementation(libs.androidx.room.ktx)
    ksp(libs.room.compiler)

    // Dagger Hilt library
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Datastore library
    implementation(libs.androidx.datastore.preferences)

    // Other dependencies
    implementation(libs.appIntro)
    implementation(libs.circleimageview)
    implementation(libs.mpAndroidChart)
    implementation(libs.round.corner.progress.bar)

    // Kotlin
    implementation(libs.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
}