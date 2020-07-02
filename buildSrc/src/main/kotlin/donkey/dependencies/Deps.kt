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
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val junit = "junit:junit:${Versions.junit}"
    const val test_junit = "androidx.test.ext:junit:${Versions.test_junit}"
    const val test_espresso ="androidx.test.espresso:espresso-core:${Versions.test_espresso}"


}