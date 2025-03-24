import org.gradle.api.artifacts.dsl.DependencyHandler
//fun DependencyHandler.room() {
//    implementation(Dependencies.roomKtx)
//    implementation(Dependencies.roomRuntime)
//    kapt(Dependencies.roomCompiler)
//}
fun DependencyHandler.implementation(dependency:String){
    add("implementation",dependency)
}
fun DependencyHandler.kapt(dependency:String){
    add("kapt",dependency)
}

