import configs.implementCommonDependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = configs.Configs.CompileSdk
    namespace = configs.Configs.ApplicationId
    defaultConfig {
        applicationId = configs.Configs.ApplicationId
        minSdk = configs.Configs.MinSdk
        targetSdk = configs.Configs.TargetSdk
        versionCode = configs.Configs.VersionCode
        versionName = configs.Configs.VersionName
        testInstrumentationRunner = configs.Configs.AndroidJunitRunner
    }

    buildTypes {
        debug {
            isTestCoverageEnabled = false
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            isCrunchPngs = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = configs.Configs.FreeCompilerArgs
    }
}

dependencies {
    implementation(project(":feature-audio-book-listing"))
    implementation(project(":feature-audio-book-player"))
    implementCommonDependencies()
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(configs.SupportLibraries.Splashscreen)

    implementation(configs.DaggerHiltLib.Android)
    kapt(configs.DaggerHiltLib.Compiler)

//    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")
}
