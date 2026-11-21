import org.gradle.kotlin.dsl.implementation

plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies{
    implementation(project(":domain"))
    // csv
    implementation("com.jsoizo:kotlin-csv:2.1.0")
    //csv apache
    implementation("org.apache.commons:commons-csv:1.14.1")
}
