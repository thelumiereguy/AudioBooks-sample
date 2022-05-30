buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.41")
        classpath(kotlin("serialization", version = "1.6.20"))
        val nav_version = "2.3.0"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}

plugins {
    id("com.google.devtools.ksp") version "1.6.20-1.0.5" apply false
    id("org.jetbrains.kotlin.jvm") version "1.6.20" apply false
    id("com.android.library") version "7.1.3" apply false
    id("org.jetbrains.kotlin.android") version "1.6.20" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}