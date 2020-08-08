/**
 * Created by Donkey
 * on 2:25 PM
 */
package donkey.dependencies

object Versions {
    const val compileSdk = 29
    const val buildTools = "29.0.3"
    const val minSdk = 21
    const val targetSdk = 29
    const val versionCode = 1
    const val versionName = "1.0"
    const val kotlin = "1.3.72"
    const val core_ktx = "1.3.0"
    const val appcompat = "1.1.0"
    const val constraintLayout = "1.1.3"
    const val junit = "4.12"
    const val test_junit = "1.1.1"
    const val test_espresso = "3.2.0"
    const val  navigation = "2.0.0"
    const val  navigation_ui = "2.1.0"
    const val coroutines_android = "1.3.5"
    const val retrofit = "2.7.1"
    const val retrofit_converter_gson = "2.6.2"
    const val okhttp_logging_interceptor = "4.0.0"

    const val viewpager = "1.0.0"
    const val material = "1.2.0-alpha03"
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val junit = "junit:junit:${Versions.junit}"
    const val test_junit = "androidx.test.ext:junit:${Versions.test_junit}"
    const val test_espresso ="androidx.test.espresso:espresso-core:${Versions.test_espresso}"
    const val navigation_fragment ="androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.navigation_ui}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_android}"

    // network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_converter_gson}"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor}"

    // viewpager
    const val viewpager = "androidx.viewpager2:viewpager2:${Versions.viewpager}"
    const val material = "com.google.android.material:material:${Versions.material}"

}