package configs

object Versions {
    const val Compose = "1.2.0-alpha08"


    const val CoreKtx = "1.7.0"
    const val AppCompat = "1.4.1"
    const val LifeCycleRuntime = "2.4.1"
    const val Activity = "1.4.0"
    const val Splash = "1.0.0-beta02"


    const val Coroutines = "1.6.1"
}

object SupportLibraries {
    const val CoreKtx = "androidx.core:core-ktx:${Versions.CoreKtx}"
    const val Appcompat = "androidx.appcompat:appcompat:${Versions.AppCompat}"
    const val CoroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Coroutines}"
    const val CoroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Coroutines}"
    const val LifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LifeCycleRuntime}"
    const val ActivityKtx = "androidx.activity:activity-ktx:${Versions.Activity}"
    const val Splashscreen = "androidx.core:core-splashscreen:${Versions.Splash}"

}

object ComposeLibraries {
    const val Ui = "androidx.compose.ui:ui:${Versions.Compose}"
    const val Material = "androidx.compose.material3:material3:1.0.0-alpha10"
    const val Preview = "androidx.compose.ui:ui-tooling-preview:${Versions.Compose}"
    const val Runtime = "androidx.compose.runtime:runtime:${Versions.Compose}"
    const val Foundation = "androidx.compose.foundation:foundation:${Versions.Compose}"
    const val MaterialIconCore = "androidx.compose.material:material-icons-core:${Versions.Compose}"
    const val MaterialIconExtended =
        "androidx.compose.material:material-icons-extended:${Versions.Compose}"
    const val Tooling = "androidx.compose.ui:ui-tooling:${Versions.Compose}"
    const val Manifest = "androidx.compose.ui:ui-test-manifest:${Versions.Compose}"

    const val Activity = "androidx.activity:activity-compose:1.4.0"
    const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1"
    const val Coil = "io.coil-kt:coil-compose:2.0.0-rc02"
}


object NavigationLib {
    const val Navigation = "androidx.navigation:navigation-compose:2.4.2"
    const val DestinationCore = "io.github.raamcosta.compose-destinations:core:1.4.4-beta"
    const val DestinationKsp = "io.github.raamcosta.compose-destinations:ksp:1.4.4-beta"
    const val DestinationAnimation =
        "io.github.raamcosta.compose-destinations:animations-core:1.4.4-beta"
}

object AccompanistLib {
    const val Systemuicontroller = "com.google.accompanist:accompanist-systemuicontroller:0.23.1"
    const val Insets = "com.google.accompanist:accompanist-insets:0.23.1"
    const val PlaceholderMaterial = "com.google.accompanist:accompanist-placeholder-material:0.23.1"
    const val NavigationMaterial = "com.google.accompanist:accompanist-navigation-material:0.23.1"
    const val Permissions = "com.google.accompanist:accompanist-permissions:0.23.1"
}

object Utilities {
    const val Timber = "com.jakewharton.timber:timber:5.0.1"
}

object DaggerHiltLib {
    const val Android = "com.google.dagger:hilt-android:2.41"
    const val Compiler = "com.google.dagger:hilt-android-compiler:2.41"
    const val Compose = "androidx.hilt:hilt-navigation-compose:1.0.0"
}
