plugins {
    id("xiashao.android.library")
    id("xiashao.android.hilt.library")
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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(project(":Data:core-model"))
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.compiler)
    implementation(libs.retrofit.core)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging)
    api(libs.kotlinx.datetime)
    implementation(libs.androidx.dataStore.datastore.preferences)
}