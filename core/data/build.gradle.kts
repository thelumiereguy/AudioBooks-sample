import configs.implementCommonDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "dev.thelumiereguy.data"
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
}

dependencies {
    val roomVersion = "2.4.2"

    implementCommonDependencies()
    implementation(configs.DaggerHiltLib.Android)
    kapt(configs.DaggerHiltLib.Compiler)

    implementation(project(":helpers"))

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("com.google.android.material:material:1.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")

    implementation("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
}
