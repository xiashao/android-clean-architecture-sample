plugins {
    id("com.android.library")
    id ("org.jetbrains.kotlin.android")
    id("ivi.android.hilt.library")
}

android {
    namespace = "com.xiashao.sample.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(":Data:core-model"))
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.compiler)
//    implementation(project(":AppLibs:di"))
//    implementation(project(":TechLibs:network"))
//    implFoundation(FoundationModules.AppLibs.DI, project)
//    implFoundation(FoundationModules.TechLibs.NETWORK, project)
//
//    implementation(Libs.ROOM_RUNTIME)
//    implementation(Libs.ROOM_KTX)
//    kapt(Libs.ROOM_COMPILER)
    api(libs.kotlinx.datetime)
//
//    implementation(Libs.DATASTORE_PREFERENCES)
//    implementation(Libs.GSON)
//
//    implementation(Libs.LOGGING_INTERCEPTOR)
//    implementation(Libs.RETROFIT)
//    implementation(Libs.RETROFIT_GSON_CONVERTER)
}