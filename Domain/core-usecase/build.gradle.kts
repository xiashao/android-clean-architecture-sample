plugins {
    id("xiashao.android.library")
    id("xiashao.android.hilt.library")
}

android {
    namespace = "com.xiashao.domain.usecase"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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
