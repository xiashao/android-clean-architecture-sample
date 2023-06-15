// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
//        classpath('com.android.tools.build:gradle:8.0.2')
    }

}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.register<Exec>("downloadConfig") {
    commandLine = mutableListOf("${project.rootDir}${File.separator}download_config.sh")
}
// ./gradlew clean --refresh-dependencies
configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "minutes")
}
