import configs.implementCommonDependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    signingConfigs {
        create("release") {
            storeFile =
                file("/Users/piyushpratheep/AndroidStudioProjects/AudioBookssample/audio_book_sample.jks")
            storePassword = "audio_book_sample"
            keyAlias = "audio_book_sample"
            keyPassword = "audio_book_sample"
        }
    }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":feature-audio-book-listing"))
    implementation(project(":feature-audio-book-player"))
    implementCommonDependencies()
    implementation("com.google.android.material:material:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(configs.SupportLibraries.Splashscreen)
    implementation(configs.Utilities.Timber)

    implementation(configs.DaggerHiltLib.Android)
    kapt(configs.DaggerHiltLib.Compiler)

    implementation("androidx.startup:startup-runtime:1.1.1")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.9.1")
    implementation("com.github.thelumiereguy:CrashWatcher-Android:2.0.2")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    val nav_version = "2.4.2"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
}
