import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"

    const val material ="androidx.compose.material:material:${Versions.compose}"
    const val icon = "androidx.compose.material:material-icons-extended:${Versions.compose}"
    const val material3 = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
    const val foundation = "androidx.compose.foundation:foundation:${Versions.composeMaterial3}"

    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val hiltAgp = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    const val hiltwork = "androidx.hilt:hilt-work:${Versions.hiltWork}"
    const val hilttest = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"

    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"

    const val navigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val hiltNavigation ="androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigation}"
    const val nacom = "androidx.navigation:navigation-common-ktx:${Versions.navigation1}"
    const val narun = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation1}"
    const val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"

    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.reflect}"
    const val reflection = "org.reflections:reflections:${Versions.reflection}"

    const val kcc = "io.ktor:ktor-client-core:${Versions.ktor}"
    const val kio = "io.ktor:ktor-client-cio:${Versions.ktor}"
    const val kcl = "io.ktor:ktor-client-logging:${Versions.ktor}"
    const val kcw = "io.ktor:ktor-client-websockets:${Versions.ktor}"
    const val kcj = "io.ktor:ktor-client-json-jvm:${Versions.ktor}"
    const val kcsj = "io.ktor:ktor-client-serialization-jvm:${Versions.ktor}"
    const val kcs = "io.ktor:ktor-client-serialization:${Versions.ktor}"


    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val coro = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coro}"
    const val serial ="org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serial}"


}
//fun DependencyHandler.room(){
//    implementation(Dependencies.roomKtx)
//    implementation(Dependencies.roomRuntime)
//    kapt(Dependencies.roomCompiler)
//}

//fun DependencyHandler.retrofit(){
//    implementation(Dependencies.retrofit)
//    implementation(Dependencies.moshiConverter)
//}
//
//fun DependencyHandler.compose(){
//    implementation(Dependencies.composeUi)
//    implementation(Dependencies.composeRuntime)
//    implementation(Dependencies.composeUiTooling)
//    implementation(Dependencies.composeUiGraphics)
//    implementation(Dependencies.composeUiToolingPreview)
//}
//
//fun DependencyHandler.hilt(){
//    implementation(Dependencies.hiltAndroid)
//    kapt(Dependencies.hiltCompiler)
//}