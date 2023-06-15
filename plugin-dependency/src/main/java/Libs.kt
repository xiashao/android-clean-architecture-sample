object Libs {
    //================================= Android SDK Libs Start ===================================//
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val ANNOTATION = "androidx.annotation:annotation:${Versions.ANNOTATION}"
    const val ANDROIDX_SECURITY_CRYPTO =
        "com.google.crypto.tink:tink-android:${Versions.ANDROIDX_SECURITY_CRYPTO}"
    const val ANDROID_EXOPLAYER =
        "com.google.android.exoplayer:exoplayer-core:${Versions.ANDROID_EXOPLAYER}"
    const val EXOPLAYER_HLS =
        "com.google.android.exoplayer:exoplayer-hls:${Versions.ANDROID_EXOPLAYER}"
    const val EXOPLAYER_RTSP =
        "com.google.android.exoplayer:exoplayer-rtsp:${Versions.ANDROID_EXOPLAYER}"
    const val ANDROID_EXOPLAYER_UI =
        "com.google.android.exoplayer:exoplayer-ui:${Versions.ANDROID_EXOPLAYER}"
    const val GOOGLE_CRYPTO_TINK =
        "com.google.crypto.tink:tink-android:${Versions.GOOGLE_CRYPTO_TINK}"
    const val ANDROID_FLEXBOX = "com.google.android:flexbox:${Versions.ANDROID_FLEXBOX}"
    const val ANDROIDX_SWIPE_REFRESH_LAYOUT =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.ANDROIDX_SWIPE_REFRESH_LAYOUT}"
    const val ANDROIDX_MULTIDEX = "androidx.multidex:multidex:${Versions.ANDROIDX_MULTIDEX}"
    const val ANDROID_LOTTIE = "com.airbnb.android:lottie:${Versions.ANDROID_LOTTIE}"
    const val ANDROIDX_WINDOW = "androidx.window:window:${Versions.ANDROIDX_WINDOW}"
    const val ANDROIDX_LIFECYCLE_VIEW_MODEL =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROIDX_LIFECYCLE}"
    const val ANDROIDX_LIFECYCLE_RUNTIME =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE}"
    const val ANDROIDX_FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.ANDROIDX_FRAGMENT}"
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KT}"
    const val CORELIBRARY_DESUGARING =
        "com.android.tools:desugar_jdk_libs:${Versions.CORE_LIBRARY_DESUGARING}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val NAVIGATION_FRAGMENT_KTX =
        "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    const val STDLIB_JDK = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
    const val RECYCLE_VIEW = "androidx.recyclerview:recyclerview:${Versions.RECYCLE_VIEW}"
    const val LIFECYCLE_RUNTIME_KTX =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_VIEW_MODEL_KTX =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val APPCOMPAT = "androidx.appcompat:appcompat:${Versions.APPCOMPAT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val CONSTRAINT_LAYOUT =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM_VERSION}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM_VERSION}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM_VERSION}"
    const val DATASTORE_PREFERENCES =
        "androidx.datastore:datastore-preferences:${Versions.DATASTORE_PREFERENCES}"
    const val DATASTORE_CORE =
        "androidx.datastore:datastore-core:${Versions.DATASTORE_CORE}"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val KOTLINX_DATETIME =
        "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.KOTLINX_DATETIME}"
    const val ANDROID_MEDIA =
        "androidx.media:media:${Versions.MEDIA}"
    //================================= Android SDK Libs End  ====================================//

    //==============Unit Test Start =========================//
    const val JUNIT4 = "junit:junit:${Versions.JUNIT}"
    const val ANDROIDX_TEST_CORE = "androidx.test:core:${Versions.ANDROIDX_TEST}"
    const val ANDROIDX_JUNIT = "androidx.test.ext:junit-ktx:${Versions.ANDROIDX_JUNIT}"
    const val ANDROID_JUNIT_EXT = "androidx.test.ext:junit:${Versions.ANDROIDX_JUNIT}"
    const val MOCKITO = "org.mockito:mockito-inline:${Versions.MOCKITO_INLINE}"
    const val MOCKITO_KOTLIN = "org.mockito.kotlin:mockito-kotlin:${Versions.MOCKITO_KOTLIN}"
    const val ROBOLECTRIC = "org.robolectric:robolectric:${Versions.ROBOLECTRIC}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
    const val TRUTH = "com.google.truth:truth:${Versions.TRUTH}"
    const val COROUTINES_TEST =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
    //==============Unit Test end   =========================//

    // ==================================== Third Party Libs Start ============================== //
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
    const val LOGGER = "com.orhanobut:logger:${Versions.LOGGER}"

    //http://bumptech.github.io/glide/doc/getting-started.html
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
    const val GLIDE_KAPT = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
    const val GLIDE_OKHTTP =
        "com.github.bumptech.glide:okhttp3-integration:${Versions.GLIDE_OKHTTP}"
    const val ANDROID_SVG = "com.caverock:androidsvg-aar:${Versions.ANDROID_SVG}"

    //https://github.com/square/retrofit
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"

    //https://square.github.io/okhttp/#releases
    const val OKHTTP_BOM = "com.squareup.okhttp3:okhttp-bom:${Versions.OKHTTP}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_LOGGING}"
    const val OKHTTP_TEST = "com.squareup.okhttp3:mockwebserver:${Versions.OKHTTP}"
    const val VIEWPAGER2 = "androidx.viewpager2:viewpager2:${Versions.VIEWPAGER2}"
    const val SHIMMER = "com.facebook.shimmer:shimmer:${Versions.SHIMMER}"

    //https://developers.google.com/protocol-buffers/
    const val PROTO_BUF_LITE = "com.google.protobuf:protobuf-javalite:${Versions.PROTO_LITE}"
    // ==================================== Third Party Libs End ============================== //

}
