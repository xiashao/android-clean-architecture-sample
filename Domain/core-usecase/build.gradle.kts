plugins {
    id("com.android.library")
    id ("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.xiashao.sample.domain"
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
//    api(project(":AppLibs:di"))
//    api(project(":AppLibs:domain"))
//    api(project(":TechLibs:model"))
//    implementation(project(":TechLibs:log"))
//
//    apiFoundation(FoundationModules.AppLibs.DI, project)
//    apiFoundation(FoundationModules.AppLibs.DOMAIN, project)
//    apiFoundation(FoundationModules.TechLibs.MODEL, project)
//    apiFoundation(FoundationModules.Services.Car.SDK, project)
//    implFoundation(FoundationModules.TechLibs.LOG, project)

    implementation(project(":Data:core-model"))
    implementation(project(":Data:core-data"))
}
