import configs.implementCommonDependencies

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "dev.thelumiereguy.feature_book_listing"
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
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        freeCompilerArgs = configs.Configs.FreeCompilerArgs
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

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.6.0")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.4.1")

    implementation("androidx.fragment:fragment-ktx:1.4.1")

    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl:4.3.2")
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:4.3.2")

    implementation("com.github.bumptech.glide:glide:4.12.0")

    testImplementation(kotlin("test"))

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2")

    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("io.mockk:mockk:1.12.2")
}
