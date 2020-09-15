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
    const val swipeRefreshLayout = "1.1.0-rc01"
    const val baseRecyclerViewAdapterHelper = "2.9.50"
    const val recyclerView = "1.1.0"


    const val viewpager = "1.0.0"
    const val material = "1.2.0-alpha03"

    const val livedata_ktx = "2.2.0"
    const val viewmodel_ktx = "2.2.0"
    const val lifecycle_extension = "2.2.0"
    const val koin = "2.0.1"

    const val verticalTabLayout = "1.2.5"
    const val flowLayout = "1.1.2"

    const val glide = "4.11.0"
    const val glide_compiler = "4.11.0"
    const val customactivityoncrash = "2.3.0"

    const val banner = "1.4.10"
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
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    //recyclerView
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.baseRecyclerViewAdapterHelper}"

    // network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_converter_gson}"
    const val okhttp_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp_logging_interceptor}"

    // viewpager
    const val viewpager = "androidx.viewpager2:viewpager2:${Versions.viewpager}"
    const val material = "com.google.android.material:material:${Versions.material}"

    // liveData
    const val livedata_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.livedata_ktx}"
    // viewModel
    const val viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewmodel_ktx}"
    const val lifecycle_extension = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle_extension}"

    // koin
    const val koin_android = "org.koin:koin-android:${Versions.koin}"
    const val koin_androidx_scope = "org.koin:koin-androidx-scope:${Versions.koin}"
    const val koin_androidx_viewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    const val verticalTabLayout = "q.rorbin:VerticalTabLayout:${Versions.verticalTabLayout}"
    const val flowLayout = "com.hyman:flowlayout-lib:${Versions.flowLayout}"

    //Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_compiler}"

    //崩溃捕捉框架
    const val customactivityoncrash = "cat.ereza:customactivityoncrash:${Versions.customactivityoncrash}"

    const val banner = "com.youth.banner:banner:${Versions.banner}"
}