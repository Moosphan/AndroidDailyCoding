/**
 * Version config of libraries.
 */
object Versions {
    const val kotlinVersion = "1.4.32"
    const val coreKtxVersion = "1.6.0"
    const val appcompatVersion = "1.3.0"
    const val materialVersion = "1.4.0"
    const val constraintlayoutVersion = "2.0.4"
    const val legacySupportVersion = "1.0.0"
    const val hiltVersion = "2.36"
    const val hiltViewModelVersion = "1.0.0-alpha03"
    const val coroutinesVersion = "1.5.0"
    const val viewModelVersion = "2.3.1"
}

object Dependencies {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKtxVersion}"
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"
    val material = "com.google.android.material:material:${Versions.materialVersion}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayoutVersion}"
    val legacySupport = "androidx.legacy:legacy-support-v4:${Versions.legacySupportVersion}"
    val hilt = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    val hiltViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltViewModelVersion}"
    val hiltProcessor = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
    val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelVersion}"
}