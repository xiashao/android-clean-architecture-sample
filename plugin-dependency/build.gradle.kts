plugins {
    id("java-gradle-plugin")
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    `kotlin-dsl`
}


repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("com.android.tools.build:gradle:8.0.2")
    implementation("commons-io:commons-io:2.6")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
    implementation("org.jlleitschuh.gradle:ktlint-gradle:11.0.0")
    implementation("com.google.protobuf:protobuf-gradle-plugin:0.9.1")
    implementation(gradleApi())
    implementation(localGroovy())

}

gradlePlugin {
    plugins {
        register("androidLibsVersionDependency") {
            id = "xiashao.libs.version"
            implementationClass = "com.xiashao.plugin.AndroidLibsVersionPlugin"
        }
        register("androidApplication") {
            id = "xiashao.android.application"
            implementationClass = "com.xiashao.plugin.AndroidApplicationPlugin"
        }

        register("androidLibrary") {
            id = "xiashao.android.library"
            implementationClass = "com.xiashao.plugin.AndroidLibraryPlugin"
        }

        register("androidHiltLibrary") {
            id = "xiashao.android.hilt.library"
            implementationClass = "com.xiashao.plugin.AndroidHiltLibraryPlugin"
        }

        register("protoBuf") {
            id = "xiashao.android.protobuf"
            implementationClass ="com.xiashao.plugin.ProtoBufPlugin"
        }

        register("layoutTransform") {
            id = "xiashao.android.layout"
            implementationClass ="com.xiashao.plugin.databinding.LayoutPlugin"
        }

        register("androidDimensConvert") {
            id = "xiashao.android.dimensConvert"
            implementationClass ="com.xiashao.plugin.dimensconvert.AndroidDimensConvertPlugin"
        }
    }
}

