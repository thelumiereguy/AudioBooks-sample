import configs.extensions.implementation
import configs.implementCommonDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "dev.thelumiereguy.feature_audio_book_player"
    compileSdk = configs.Configs.CompileSdk

    defaultConfig {
        minSdk = configs.Configs.MinSdk
        targetSdk = configs.Configs.TargetSdk

        testInstrumentationRunner = configs.Configs.AndroidJunitRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = configs.Configs.FreeCompilerArgs
    }
    buildFeatures {
        viewBinding = true
    }
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    implementCommonDependencies()

    implementation(configs.DaggerHiltLib.Android)
    kapt(configs.DaggerHiltLib.Compiler)

    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:ab-tests"))
    implementation(project(":helpers"))

    implementation(configs.SupportLibraries.CoreKtx)
    implementation(configs.SupportLibraries.Appcompat)
    implementation(configs.UILibraries.Material)

    implementation(configs.SupportLibraries.LifecycleLivedata)
    implementation(configs.SupportLibraries.LifecycleViewModel)
    implementation(configs.SupportLibraries.LifecycleViewModelSavedState)

    implementation(configs.SupportLibraries.FragmentKtx)

    implementation(configs.Utilities.Glide)

    testImplementation(kotlin("test"))

    testImplementation(configs.Coroutines.CoroutineTest)

    testImplementation(configs.TestUtils.JupiterJunit)

    testImplementation(configs.TestUtils.MockK)
}
