plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "2.1.0"
}

android {
    val versionMajor = 1
    val versionMinor = 30
    val versionPatch = 2
    val appName = "KhoroojYar"
    val appVersionCode = versionMajor * 10000 + versionMinor * 100 + versionPatch
    val appVersionName = "$versionMajor.$versionMinor.$versionPatch"

    defaultConfig {
        applicationId = "com.faridnia.khoroojyar"
        minSdk = 33
        targetSdk = 35
        compileSdk = 35
        namespace = "com.faridnia.khoroojyar"
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    applicationVariants.all {
        outputs.all {
            val outputImpl = this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
//                val buildType = variant.buildType.name
            val buildType = name.split("-")[0]
            outputImpl.outputFileName =
                "${appName}-${buildType}-v${appVersionName}-c${appVersionCode}.apk"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    // Allow references to generated code
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.material3)
    implementation(libs.ui)

    // Compose UI dependencies
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material3.material3)
    implementation(libs.navigation.compose)

    // Lifecycle ViewModel integration for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Optional: Debugging and Tooling
    debugImplementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)

    //Persian Date Picker
    implementation(libs.compose.persian.date.picker)

    //Data Store
    implementation(libs.androidx.datastore.preferences)

    // Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Room
    implementation(libs.androidx.room.runtime)
    // If this project uses any Kotlin source, use Kotlin Symbol Processing (KSP)
    ksp(libs.androidx.room.compiler)
    //Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.kotlinx.serialization)

    //Vico Charts
//    implementation(libs.vico.compose)
    implementation(libs.vico.compose.m3)
    implementation(libs.vico.views)

    //Work Manager with Coroutines
    implementation(libs.work)
    implementation(libs.androidx.hilt.work)
    kapt(libs.hilt.compiler)

    implementation (libs.compose.glide)
}