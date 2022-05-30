package configs

import configs.extensions.implementation
import configs.extensions.debugImplementation
import configs.extensions.ksp
import org.gradle.api.artifacts.dsl.DependencyHandler

// Idea from https://proandroiddev.com/avoid-repetitive-dependency-declarations-with-gradle-kotlin-dsl-97c904704727


fun DependencyHandler.implementCommonDependencies() {
    implementation(SupportLibraries.CoreKtx)
    implementation(SupportLibraries.Appcompat)
    implementation(SupportLibraries.CoroutineCore)
    implementation(SupportLibraries.CoroutineAndroid)
    implementation(SupportLibraries.LifecycleRuntime)
    implementation(SupportLibraries.ActivityKtx)
}

fun DependencyHandler.implementComposeDependencies() {
    implementation(ComposeLibraries.Ui)
    implementation(ComposeLibraries.Material)
    implementation(ComposeLibraries.Preview)
    implementation(ComposeLibraries.Runtime)
    implementation(ComposeLibraries.Foundation)
    implementation(ComposeLibraries.MaterialIconCore)
    implementation(ComposeLibraries.MaterialIconExtended)
    debugImplementation(ComposeLibraries.Tooling)
    debugImplementation(ComposeLibraries.Manifest)
    //
    implementation(ComposeLibraries.Activity)
    implementation(ComposeLibraries.ViewModel)
    implementation(ComposeLibraries.Coil)

    // Accompanist
    implementation(AccompanistLib.Systemuicontroller)
    implementation(AccompanistLib.Insets)
    implementation(AccompanistLib.PlaceholderMaterial)
    implementation(AccompanistLib.NavigationMaterial)
    implementation(AccompanistLib.Permissions)
}

fun DependencyHandler.implementNavigationDependencies() {
    // Navigation
    implementation(NavigationLib.Navigation)
    implementation(NavigationLib.DestinationCore)
    ksp(NavigationLib.DestinationKsp)
    implementation(NavigationLib.DestinationAnimation)
}